package com.akimatBot.web.controllers.client;


import com.akimatBot.RestoranApplication;
import com.akimatBot.entity.custom.CartItem;
import com.akimatBot.entity.standart.Employee;
import com.akimatBot.repository.repos.FoodCategoryRepo;
import com.akimatBot.repository.repos.MessageRepository;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.services.CartItemService;
import com.akimatBot.services.KeyboardMarkUpService;
import com.akimatBot.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class CartController {



    @Autowired
    MessageRepository messageRepo;

    @Autowired
    FoodCategoryRepo subCategoryRepo;


    @Autowired
    CartItemService cartItemService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PropertiesRepo propertiesRepo;

    @PostMapping("/removeFromCart")
    public Map<Object, Object> removeFromCart(@RequestParam("foodId") int bookId,
                                  @RequestParam("chatId") long chatId){
         cartItemService.decreaseCartItemQuantity(bookId, chatId);
         return getCart(chatId);
    }

//    @PostMapping("/createOrder")
//    public Map<Object, Object> createOrder(@RequestParam("chatId") long chatId,
//                                           @RequestParam("useCashback") boolean useCashback,
//                                           @RequestParam("deliverNeed") boolean deliverNeed,
//                                           @RequestParam("address") String address,
//                                           @RequestParam("fullName") String fullName,
//                                           @RequestParam("phoneNumber") String phoneNumber){
//
////        User user = userService.findByChatId(chatId);
////        user.setPhone(phoneNumber);
////        user.setFullName(fullName);
////        userService.save(user);
////
////        Order order = new Order();
////        order.setUser(user);
////        order.setUseCashback(useCashback);
////        order.setDeliverNeed(deliverNeed);
////        order.setDeliveryPrice(deliverNeed?Integer.parseInt(propertiesRepo.findFirstById(3).getValue()):0);
////
////        order.setAddress(address);
////        order = orderService.fillOrderWithCardItemsAndSave(order, chatId, order.getUseCashback());
////        sendPayPage(order);
////        Map<Object, Object> asd = order.getJson();
//        return asd;
//
//    }


    @PostMapping("/addToCart")
    public Map<Object, Object> addToCart(@RequestParam("foodId") long bookId, @RequestParam("chatId") long chatId){

        try {
            cartItemService.addToCart(bookId, chatId);
            return getCart(chatId);
        }catch (Exception e){
            e.printStackTrace();
            Map<Object, Object> ans = new TreeMap<>();
            ans.put("answer" , "error");
            return ans;
        }
    }

    @PostMapping("/clearCart")
    public boolean  clearCart(@RequestParam("chatId") long chatId){
        try {
            cartItemService.clearUserCart(chatId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    @GetMapping("/getCart")
    public  Map<Object, Object> getCart(@RequestParam("chatId") long chatId){
        //todo overwrite

        List<CartItem> cartItems = cartItemService.findAllCartItemsOfUser(chatId);
        Employee user = employeeService.getByChatId(chatId);
        Map<Object, Object> data = new TreeMap<>();



        List<Map<Object, Object>> books = new ArrayList<>();
        for (CartItem cartItem : cartItems){
            Map<Object, Object> ans = new TreeMap<>();
            ans.put("id" , cartItem.getId());
            ans.put("quantity" , cartItem.getQuantity());
            ans.put("item" , cartItem.getFood().getJson(user.getLanguage()));
            books.add(ans);
        }


        Map<Object, Object> prop = new TreeMap<>();
//        prop.put("cashback", user.getCashback());
        prop.put("deliveryPrice", 1500);


        Map<Object, Object> userInfo = new TreeMap<>();
        userInfo.put("fullName", user.getFullName());
        userInfo.put("phoneNumber", user.getPhone());

        data.put("cartItems", books);
        data.put("properties", prop);
        data.put("userInfo", userInfo);
        return data ;
    }




//    private int sendMessage(String text, String chatId){
//        try {
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(chatId);
//            sendMessage.setText(text);
//            sendMessage.setParseMode("html");
//            return RestoranApplication.bot.execute(sendMessage).getMessageId();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return 0;
//    }
    private long sendMessageWithKeyboard(String text, long kid, long chatId){
        KeyboardMarkUpService keyboardMarkUpService = new KeyboardMarkUpService();
        return sendMessageWithKeyboard(text, keyboardMarkUpService.select(kid, chatId), chatId);
    }
    private int sendMessageWithKeyboard(String text, ReplyKeyboard replyKeyboard, long chatId){
//        try {
//
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(chatId);
//            sendMessage.setText(text);
//            sendMessage.setParseMode("html");
//            sendMessage.setReplyMarkup(replyKeyboard);
//            return RestoranApplication.bot.execute(sendMessage).getMessageId();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return 0;
    }
    public  void     deleteMessage(String chatId, int messageId) {
//        try {
//            RestoranApplication.bot.execute(new DeleteMessage(chatId, messageId));
//        } catch (TelegramApiException ignored) {}
    }

    private String getText(long messId, int langId){
        return messageRepo.findByIdAndLangId(messId, langId).getName();
    }




}
