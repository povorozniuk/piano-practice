package com.povorozniuk.backend.telegram;

import com.povorozniuk.backend.model.telegram.bot.piano.Command;
import com.povorozniuk.backend.service.PianoDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PianoPracticeBotListener extends TelegramLongPollingBot {

    private final PianoDatabaseService pianoDatabaseService;

    @Value("${responses.start-hello}")
    private String responseStartHello;
    @Value("${responses.start-commands}")
    private String responseStartCommands;
    @Value("${telegram.bot.user_name}")
    private String botUserName;
    @Value("${telegram.bot.bot_token}")
    private String botToken;

    public PianoPracticeBotListener(PianoDatabaseService pianoDatabaseService) {
        this.pianoDatabaseService = pianoDatabaseService;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.getMyChatMember() != null && update.getMyChatMember().getNewChatMember() != null && StringUtils.equals(update.getMyChatMember().getNewChatMember().getStatus(), "kicked")){
            User leavingUser = update.getMyChatMember().getFrom();
            log.info("User id={} firstName={} lastName={} username={} has stopped the bot", leavingUser.getId(), leavingUser.getFirstName(), leavingUser.getLastName(), leavingUser.getUserName());
        }

        if (update.getMessage() == null) return;

        Message message = update.getMessage();

        if (StringUtils.equals(update.getMessage().getText(), "/start")){
            User joiningUser = update.getMessage().getFrom();
            log.info("User id={} firstName={} lastName={} username={} has started the bot", joiningUser.getId(), joiningUser.getFirstName(), joiningUser.getLastName(), joiningUser.getUserName());
            sendMessage(SendMessage.builder()
                    .chatId(String.valueOf(message.getChatId()))
                    .text(String.format(responseStartHello, joiningUser.getFirstName())).build());
            sendMessage(SendMessage.builder()
                    .chatId(String.valueOf(message.getChatId()))
                    .text(responseStartCommands).build());
        }
        pianoDatabaseService.saveMessage(message);
        List<MessageEntity> messageEntityList = message.getEntities();
        if (messageEntityList == null){
            return;
        }
        Optional<MessageEntity> messageEntityOptional = messageEntityList.stream().findAny();
        if (messageEntityOptional.isPresent()){
            MessageEntity messageEntity = messageEntityOptional.get();
            String botCommand = messageEntity.getText();
            Command command = Command.getFromString(botCommand);
            if (command != null){
                String commandStr = "/" + command.name();
                String subCommand = null;
                if (message.getText().length() > commandStr.length()) {
                    subCommand = StringUtils.strip(message.getText().replace(commandStr, ""));
                }
                String returnMessage = pianoDatabaseService.getPracticeTime(command, subCommand);

                if (StringUtils.equals(returnMessage, PianoDatabaseService.PIANO_NOT_INVENTED_YET_TEXT)){
                    SendMessage pianoNotInventedMessage = SendMessage.builder()
                            .replyToMessageId(message.getMessageId())
                            .chatId(String.valueOf(message.getChatId()))
                            .text(returnMessage).build();
                    sendPhoto("https://i.ibb.co/2WMgpV8/piano-invented.png", "When was the piano invented?", null, message.getChatId());
                    sendMessage(pianoNotInventedMessage);
                }else{
                    SendMessage sendMessage = SendMessage.builder()
                            .replyToMessageId(message.getMessageId())
                            .chatId(String.valueOf(message.getChatId()))
                            .parseMode(ParseMode.MARKDOWN)
                            .text("```\n" + returnMessage + "```").build();
                    sendMessage(sendMessage);
                }
            }
        }
    }

    private void sendPhoto(final String imageUrl, final String filename, final Integer replyToMessageId, final Long chatId){
        try{
            URL url = new URL(imageUrl);
            try(InputStream is = url.openStream()){
                SendPhoto sendPhoto = SendPhoto.builder()
                        .photo(new InputFile(is, filename))
                        .replyToMessageId(replyToMessageId)
                        .chatId(String.valueOf(chatId))
                        .build();
                execute(sendPhoto);
            }
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(BotApiMethod<?> message){
        try {
            sendApiMethod(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
