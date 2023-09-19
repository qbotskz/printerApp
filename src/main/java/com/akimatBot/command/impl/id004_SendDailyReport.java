package com.akimatBot.command.impl;

import com.akimatBot.command.Command;
import com.akimatBot.utils.reports.OrderReportDaily;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id004_SendDailyReport extends Command {

    @Override
    public boolean execute() throws TelegramApiException {
        try {

            OrderReportDaily orderReportDaily = new OrderReportDaily();
            orderReportDaily.sendCompReport();
            sendMessage("Отправлено!");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("Произошла не приведенная ошибка!");
        }
        return EXIT;
    }

}
