package com.povorozniuk.backend.model.telegram.bot.piano;

public enum Command {

    today,
    yesterday,
    week,
    month,
    year;

    public static Command getFromString(String value){
        for (Command command : Command.values()){
            if (value.equalsIgnoreCase("/" + command)){
                return command;
            }
        }
        return null;
    }
}
