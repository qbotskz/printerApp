package com.akimatBot.command.impl.admin;

import com.akimatBot.command.Command;
import com.akimatBot.entity.custom.RestaurantBranch;
import com.akimatBot.entity.enums.WaitingType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;

public class id089_addRestaurantBranch extends Command {
    @Override
    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
        switch (waitingType){
            case START:
                getBranchName();
                waitingType = WaitingType.GET_NAME;
                return COMEBACK;
            case GET_NAME:
                if(update.hasMessage()&&update.getMessage().hasText()){
                    RestaurantBranch restaurantBranch = new RestaurantBranch();
                    restaurantBranch.setBranchName(update.getMessage().getText());
                    restaurantBranchRepo.save(restaurantBranch);
                    sendMessage(142);
                    return EXIT;
                }
        }
        return false;
    }
    private int getBranchName() throws TelegramApiException{
        return sendMessage(208);
    }
}
