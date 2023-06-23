package com.akimatBot.command.impl.courier;

import com.akimatBot.command.Command;
import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.utils.ButtonsLeaf;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class id060_exit_from_order extends Command {
    private FoodOrder foodOrder;

    @Override
    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
        parseOrder();
        selectRejectionReason();
        return false;
    }
    private void selectRejectionReason() throws TelegramApiException {
        List<String> list = new ArrayList<>(Arrays.asList(getButtonText(181),getButtonText(182), getButtonText(215)));
        List<String> ids = new ArrayList<>(Arrays.asList("181", "182", "215"));

        ButtonsLeaf stat = new ButtonsLeaf(list,list);
        String[] str = update.getCallbackQuery().getMessage().getText().split("\n");
        StringBuilder stringBuilder = new StringBuilder();
        int i =0;
        for(String s:str){
            if(i==0){
                stringBuilder.append(String.format(getText(147), foodOrder.getId())).append("\n");
                i++;
                continue;
            }
            if(i==str.length-1){
                break;
            }
            stringBuilder.append(s).append("\n");
            i++;

        }
        editMessageWithKeyboard(update.getCallbackQuery().getMessage().getText(), updateMessageId, stat.getListButtonInline());

    }
    public void parseOrder(){
        String[] myString = getCallbackQuery().getMessage().getText().split("\n");
        Pattern pattern = Pattern.compile("(?<=№|#).[0-9]+");

        Matcher matcher = pattern.matcher(myString[0]);
        int orderNum = -1;
        if (matcher.find())
        {
            orderNum = Integer.parseInt(matcher.group(0).replaceAll("\\s+",""));
            System.out.println(orderNum);
        }
        foodOrder = orderRepository.findOrderById(orderNum);
    }
}
