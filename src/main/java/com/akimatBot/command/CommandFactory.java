package com.akimatBot.command;

import com.akimatBot.command.impl.*;
import com.akimatBot.command.impl.id001_ShowInfo;

import com.akimatBot.exceptions.NotRealizedMethodException;

import java.util.Optional;

public class CommandFactory {

    public  static Command getCommand(long id) { return Optional.ofNullable(getCommandWithoutReflection((int) id)).orElseThrow(() -> new NotRealizedMethodException("Not realized for type: " + id)); }

    private static Command getCommandWithoutReflection(int id) {
        switch (id) {
            case 1:
                return new id001_ShowInfo();

            case 3:
                return new id003_Market();
            case 4:
                return new id004_SendDailyReport();
            case 5:
                return new id093_registerPhone();


        }
        return null;
    }
}
