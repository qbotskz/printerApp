package com.akimatBot.config;

import com.akimatBot.command.Command;

import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.standart.User;
import com.akimatBot.exceptions.CommandNotFoundException;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.*;
import com.akimatBot.services.CommandService;
import com.akimatBot.services.KeyboardMarkUpService;
import com.akimatBot.services.LanguageService;
import com.akimatBot.utils.BotUtil;
import com.akimatBot.utils.DateUtil;
import com.akimatBot.utils.SetDeleteMessages;
import com.akimatBot.utils.UpdateUtil;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class Conversation {

    private long chatId;
    //    private DaoFactory factory         = DaoFactory.getInstance();
//    private MessageDao messageDao;
    private FoodRepository foodRepository = TelegramBotRepositoryProvider.getFoodRepository();
    private List<Food> foods;
    private MessageRepository messageRepository = TelegramBotRepositoryProvider.getMessageRepository();
    private KeyboardMarkUpRepository keyboardMarkUpRepository = TelegramBotRepositoryProvider.getKeyboardMarkUpRepository();
    private static long currentChatId;
    private Command command;
    private BotUtil botUtil;
    private CommandService commandService = new CommandService();
    private KeyboardMarkUpService keyboardMarkUpService = new KeyboardMarkUpService();
//    private AppealTaskRequestToRenewalDao appealTaskRequestToRenewalDao = new AppealTaskRequestToRenewalDao();
//    private AppealTaskDao appealTaskDao = new AppealTaskDao();

    public String getStrikedText(Integer price){
        //int price = Integer.parseInt(stringPrice);
        String s = "";
        int a = 0;
        while (price > 0) {

            a = price % 10;
            price = price / 10;
            if(a == 0){
                s+="0̶";
            }
            else if(a==1){
                s+="1̶";
            }
            else if(a==2){
                s+="2̶";
            }
            else if(a==3){
                s+="3̶";
            }
            else if(a==4){
                s+="4̶";
            }
            else if(a==5){
                s+="5̶";
            }
            else if(a==6){
                s+="6̶";
            }
            else if(a==7){
                s+="7̶";
            }
            else if(a==8){
                s+="8̶";
            }
            else if(a==9){
                s+="9̶";
            }
        }
       return  new StringBuilder(s).reverse().toString();

    }

    public void handleUpdate(Update update, DefaultAbsSender bot) throws Exception {
        if (update.hasInlineQuery()) {
            AnswerInlineQuery ans = new AnswerInlineQuery();
            String query = update.getInlineQuery().getQuery();
            String commandId = "";
            String price;
            User user = TelegramBotRepositoryProvider.getUserRepository().findByChatId(chatId);
            
//            if (query.equals("set")) {
//                foods = foodRepository.findFoodsByFoodSubCategory(TelegramBotRepositoryProvider.getFoodSubCategoryRepo().findFoodSubCategoryEntityById(17));
//                commandId = "46,";
//            } else {
//                foods = foodRepository.findAllBySpecialOfferSumNotNull();
//                commandId = "47,";
//            }

//            if(query.equals("spec")) {
//                foods = foodRepository.findAllBySpecialOfferSumNotNull();
//                commandId = "47,";
//            }
//            else{
//                List<FoodSubCategoryEntity> foodSubCategoryEntities = TelegramBotRepositoryProvider.getFoodSubCategoryRepo().findAllByInlineTrue();
//                if(!foodSubCategoryEntities.isEmpty())
//                {
//                    for (FoodSubCategoryEntity f:foodSubCategoryEntities){
//                        if(query.equals(f.getName(1))){
//                            foods = foodRepository.findFoodsByFoodCategory(f);
//                            commandId = "46,";
//                            break;
//                        }
//                    }
//                }
//            }


            ArrayList<InlineQueryResult> inlineQueryResults = new ArrayList<>();
            int id = 0;
            for (Food food : foods) {
//                InlineQueryResultArticle inlineQueryResultArticle = new InlineQueryResultArticle();
//                inlineQueryResultArticle.setThumbUrl(food.getPhoto_url());
//                inlineQueryResultArticle.setId(String.valueOf(food.getId()));
//                if(commandId.equals("46,")) {
//                    if(food.getSpecialOfferSum()!=null){
//                        inlineQueryResultArticle.setTitle(food.getNameRu() +" " +getStrikedText(food.getFoodPrice(user.getCity())) +" " +(food.getFoodPrice(user.getCity())-food.getSpecialOfferSum())+" ₸");
//                    }
//                    else{
//                        inlineQueryResultArticle.setTitle(food.getNameRu() + " " + food.getFoodPrice(user.getCity())+"₸");
//                    }
//                }
//                else{
//
//                    inlineQueryResultArticle.setTitle(food.getNameRu() +" " +getStrikedText(food.getFoodPrice(user.getCity())) +" " +(food.getFoodPrice(user.getCity())-food.getSpecialOfferSum())+" ₸");
//                }
//
//                inlineQueryResultArticle.setDescription(food.getDescriptionRu());
//                InputTextMessageContent inputTextMessageContent = new InputTextMessageContent();
//                inputTextMessageContent.setMessageText(commandId + food.getId() + ",-");
//                inputTextMessageContent.setDisableWebPagePreview(false);
//                inputTextMessageContent.setParseMode("html");
//                inlineQueryResultArticle.setInputMessageContent(inputTextMessageContent);
//                inlineQueryResults.add(inlineQueryResultArticle);
            }


//            ans.setInlineQueryId(update.getInlineQuery().getId()).setResults(inlineQueryResults);
            ans.setCacheTime(1);
//            ans.setSwitchPmParameter("0");
//            ans.setSwitchPmText("HELLLLOOOO");
            bot.execute(ans);



            return;
        }
        printUpdate(update);
        chatId = UpdateUtil.getChatId(update);
        currentChatId = chatId;
        //messageDao      = factory.getMessageDao();
        checkLanguage(chatId);
        botUtil = new BotUtil(bot);
        try {
            if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().split(",").length == 3) {
//                if (update.getMessage().getText().split(",")[0].equals("46")) {
//                    command = new id046_showMenu_combos();
//                }
//                else if (update.getMessage().getText().split(",")[0].equals("47")) {
//                    command = new id047_showMenu_special_offer_inline();
//                }
//            }
//            if (update.hasCallbackQuery() && update.getCallbackQuery().getData().split(",").length == 3) {
//                if (update.getCallbackQuery().getData().split(",")[0].equals("10")) {
//                    command = new id021_show_reviews();
//                }
////                else if(update.getCallbackQuery().getData().split(",")[0].equals("25") ){
////                    command = new id025_showMenu_next();
////                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("23")) {
//                    command = new id023_test_courier();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("28")) {
//                    command = new id028_SelectStatus();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("24")) {
//                    command = new id024_test_operator();
//                }else if (update.getCallbackQuery().getData().split(",")[0].equals("38")) {
//                    command = new id038_showMenu_second();
//                } else if (update.getCallbackQuery().getData().split(",")[0].equals("39")) {
//                    command = new id039_showMenu_third();
//                } else if (update.getCallbackQuery().getData().split(",")[0].equals("40")) {
//                    command = new id040_showMenu_fourth();
//                } else if (update.getCallbackQuery().getData().split(",")[0].equals("41")) {
//                    command = new id041_showMenu_fifth();
//                } else if (update.getCallbackQuery().getData().split(",")[0].equals("42")) {
//                    command = new id042_showMenu_sixth();
//                } else if (update.getCallbackQuery().getData().split(",")[0].equals("44")) {
//                    command = new id044_showMenu_special_offer_second();
//                } else if (update.getCallbackQuery().getData().split(",")[0].equals("45")) {
//                    command = new id045_showMenu_special_offer_third();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("72")) {
//                    command = new id072_select_status_chef();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("73")) {
//                    command = new id073_confirm_status_chef();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("82")) {
//                    command = new id082_cart_second();
//                }
//
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("83")) {
//                    command = new id083_cart_third();
//                }
//            } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().split(",").length == 2) {
//                if (update.getCallbackQuery().getData().split(",")[0].equals("29")) {
//                    command = new id029_ConfirmByCourier();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("49")) {
//                    command = new id049_confirm_courier();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("73")) {
//                    command = new id073_confirm_status_chef();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("37")) {
//                    command = new id037_showMenu_first();
//                }
//                else if (update.getCallbackQuery().getData().split(",")[0].equals("95")) {
//                    command = new id095_select_manager();
//                }

            } else {
                command = commandService.getCommand(update);

                if (command != null) {
                    if (command.getMessageId() == 9) {
                        botUtil.sendMessageWithKeyboard("Меню", keyboardMarkUpService.select(10, chatId), chatId);
                    }
//                    SetDeleteMessages.deleteKeyboard(chatId, bot);
//                    SetDeleteMessages.deleteMessage(chatId, bot);
                }
            }


        } catch (CommandNotFoundException e) {
            if (chatId < 0) return;
            if (command == null) {


//                SetDeleteMessages.deleteKeyboard(chatId, bot);
//                SetDeleteMessages.deleteMessage(chatId, bot);
//                Message message = messageDao.getMessage(Const.COMMAND_NOT_FOUND);
//                bot.execute(new SendMessage().setChatId(chatId).setText(message.getName()));
                int updMessId;
                if (update.hasCallbackQuery()) {
                    updMessId = update.getCallbackQuery().getMessage().getMessageId();
                } else {
                    updMessId = update.getMessage().getMessageId();
                }
                try {
                    bot.execute(new DeleteMessage(String.valueOf(chatId), updMessId));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                bot.execute(new SendMessage().builder()
                        .chatId(chatId)
                        .text(messageRepository.findByIdAndLangId(2, getLanguage().getId()).getName())
                        .replyMarkup(keyboardMarkUpService.select(2, chatId)).build());

            }
        }

        if (command != null) {
            if (command.isInitNormal(update, bot)) {
                clear();
                return;
            }
            //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//            // ToDo
////            Date date = new Date(System.currentTimeMillis());
////            List<AppealTaskRequestToRenewal> appealTaskRequestToRenewals = appealTaskRequestToRenewalRepository.findAllByOldDateDeadlineBefore(date);
////            appealTaskRequestToRenewals.forEach(appealTaskRequestToRenewal -> {
////                appealTaskRequestToRenewal.set
////            });
////            appealTaskRequestToRenewalDao.updateStatus();
////            appealTaskDao.updateStatusAutomatically();
////
            boolean commandFinished = command.execute();

            if (commandFinished) {
                clear();
            }
        }
    }

    public static long getCurrentChatId() {
        return currentChatId;
    }

    private void checkLanguage(long chatId) {
        if (LanguageService.getLanguage(chatId) == null) LanguageService.setLanguage(chatId, Language.ru);
    }

    private void printUpdate(Update update) {
        String dataMessage = "";
        if (update.hasMessage())
            dataMessage = DateUtil.getDbMmYyyyHhMmSs(new Date((long) update.getMessage().getDate() * 1000));
        log.debug("New update get {} -> send response {}", dataMessage, DateUtil.getDbMmYyyyHhMmSs(new Date()));
        log.debug(UpdateUtil.toString(update));
    }

    private void clear() {
        command.clear();
        command = null;
    }

    private Language getLanguage() {
        if (chatId == 0) return Language.ru;
        return LanguageService.getLanguage(chatId);
    }
}
