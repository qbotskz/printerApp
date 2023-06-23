//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Cashback;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//public class id091_deleteCashback extends Command {
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        if (!isAdmin()) {
//            sendMessage(Const.NO_ACCESS);
//            return EXIT;
//        }
//
//        List<Cashback> cashbacks = cashbackRepository.findAll();
//        for(Cashback c:cashbacks){
//            cashbackRepository.delete(c);
//        }
//        sendMessage(215);
//
//        return EXIT;
//    }
//
//}
