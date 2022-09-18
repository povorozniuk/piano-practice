package com.povorozniuk.backend.service;

import com.povorozniuk.backend.entity.PracticeDay;
import com.povorozniuk.backend.entity.PracticeMonth;
import com.povorozniuk.backend.model.telegram.bot.piano.Command;
import com.povorozniuk.backend.repository.PracticeDayRepository;
import com.povorozniuk.backend.repository.PracticeMonthRepository;
import com.povorozniuk.backend.util.AsciTableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Repository
public class PianoDatabaseService {
    private final LinkedList<Message> messages = new LinkedList<>();

    private final PracticeDayRepository practiceDayRepository;

    private final PracticeMonthRepository practiceMonthRepository;
    public static final String PIANO_NOT_INVENTED_YET_TEXT =  "Come on now! The piano wasn't even invented until 1700. Let alone a digital one! \uD83D\uDE04";
    private static final String DATE_STARTED_TRACKING_TEXT = "I didn't start tracking piano practice time until July 2020; therefore, the earliest data is from 2020-07";
    private static final LocalDate DATE_PIANO_INVENTED = LocalDate.of(1700, 1,1);
    private static final LocalDate DATE_STARTED_TRACKING = LocalDate.of(2020,7,1);

    public PianoDatabaseService(PracticeDayRepository practiceDayRepository, PracticeMonthRepository practiceMonthRepository) {
        this.practiceDayRepository = practiceDayRepository;
        this.practiceMonthRepository = practiceMonthRepository;
    }

    public void saveMessage(Message message){
        if (messages.size() == 50){
            messages.removeLast();
        }
        messages.addFirst(message);
    }

    public List<Message> getMessages(){
        return messages;
    }

    public String getPracticeTime(final Command command,final String subCommand){
        Optional<PracticeDay> day;
        switch (command){
            case yesterday:
                day = practiceDayRepository.findPracticeDayByDay(LocalDate.now().minusDays(1));
                return day.isEmpty() ? "No data available for yesterday" : AsciTableUtil.generateAsciTable(day.get());
            case today:
                day = practiceDayRepository.findPracticeDayByDay(LocalDate.now());
                return day.isEmpty() ? "No data available for today" : AsciTableUtil.generateAsciTable(day.get());
            case week:
                int dayOfWeek = LocalDate.now().getDayOfWeek().getValue() - 1;
                List<PracticeDay> week = practiceDayRepository.findAllByDayIsBetween(LocalDate.now().minusDays(dayOfWeek), LocalDate.now());
                return week.isEmpty() ? "No data available for the week" : AsciTableUtil.generateAsciTable(week);
            case month:
                if (StringUtils.isBlank(subCommand)){
                    List<PracticeDay> month = practiceDayRepository.findAllByDayIsBetween(LocalDate.now().withDayOfMonth(1), LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
                    return month.isEmpty() ? "No data available for the requested month" : AsciTableUtil.generateAsciTable(month);
                }else{
                    LocalDate requestedMonth;
                    try{
                        requestedMonth = parseMonth(subCommand);
                        if (requestedMonth.isBefore(DATE_PIANO_INVENTED)) return PIANO_NOT_INVENTED_YET_TEXT;
                        if (requestedMonth.isBefore(DATE_STARTED_TRACKING)) return DATE_STARTED_TRACKING_TEXT;
                    }catch (DateTimeParseException e){
                        return "Invalid parameter '" + subCommand + "'. Please specify month in the following format YYYY-MM";
                    }
                    List<PracticeDay> monthlyData = practiceDayRepository.findAllByDayIsBetween(requestedMonth, requestedMonth.with(TemporalAdjusters.lastDayOfMonth()));
                    return monthlyData.isEmpty() ? "No data available for the requested month" : AsciTableUtil.generateAsciTable(monthlyData);
                }
            case year:
                if (StringUtils.isBlank(subCommand)){
                    List<PracticeMonth> year = practiceMonthRepository.findAllByMonthBetween(LocalDate.now().withMonth(1).withDayOfMonth(1), LocalDate.now().withDayOfMonth(1));
                    return year.isEmpty() ? "No data available for the requested year" : AsciTableUtil.generateAsciTable(year);
                }else{
                    LocalDate requestedYear;
                    try{
                        requestedYear = parseYear(subCommand);
                        if (requestedYear.isBefore(DATE_PIANO_INVENTED)) return PIANO_NOT_INVENTED_YET_TEXT;
                        if (requestedYear.isBefore(DATE_STARTED_TRACKING)) return DATE_STARTED_TRACKING_TEXT;
                    }catch (DateTimeParseException e){
                        return "Invalid parameter '" + subCommand + "'. Please specify year in the following format YYYY";
                    }
                    List<PracticeMonth> annualData;
                    if (requestedYear.equals(LocalDate.now().withMonth(1).withDayOfMonth(1))){
                        annualData = practiceMonthRepository.findAllByMonthBetween(requestedYear, LocalDate.now().withDayOfMonth(1));
                    }else{
                        annualData = practiceMonthRepository.findAllByMonthBetween(requestedYear, requestedYear.withMonth(12).withDayOfMonth(1));
                    }
                    return annualData.isEmpty() ? "No data available for the requested year" : AsciTableUtil.generateAsciTable(annualData);
                }
            default:
                throw new IllegalArgumentException("Unsupported command "+ command);
        }
    }

    private LocalDate parseMonth(String subCommand){
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return YearMonth.parse(subCommand, monthFormatter).atDay(1);
    }

    private LocalDate parseYear(String subCommand) {
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
        return Year.parse(subCommand, yearFormatter).atDay(1);
    }
}
