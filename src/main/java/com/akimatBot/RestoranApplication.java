package com.akimatBot;

import com.akimatBot.config.Bot;
import com.akimatBot.utils.reports.FTPConnectionService;
import com.akimatBot.web.websocets.timerTasks.CheckCancelOrderItem;
import com.akimatBot.web.websocets.timerTasks.CheckPrintKitchen;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.telegram.telegrambots.ApiContextInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Timer;

@SpringBootApplication
//@EnableJpaRepositories
//@Component
@Slf4j
public class RestoranApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestoranApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }



}
