//package com.akimatBot.command.impl.admin;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Courier;
//import com.akimatBot.entity.custom.RestaurantBranch;
//import com.akimatBot.entity.enums.WaitingType;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.CourierRepository;
//import com.akimatBot.repository.repos.UserRepository;
//import com.akimatBot.utils.ButtonsLeaf;
//import com.akimatBot.utils.Const;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class id026_editStuff extends Command {
//    private final UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
//    private final CourierRepository courierRepository = TelegramBotRepositoryProvider.getCourierRepository();
//    private Role role;
//    private Courier courier;
//    private List<User> stuffToEdit;
//    private User user;
//    private List<String> finalList;
//    private List<String> finalList2;
//    private ButtonsLeaf buttonsLeaf;
//    private WaitingType editWaitingType;
//    private List<RestaurantBranch> branches;
//    private RestaurantBranch restaurantBranch;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType) {
//            case START:
//                deleteMessage(updateMessageId);
//                if (isButton(122)) {
//                    // couriers
//                    role = new Role(3);
//                }
//                else if(isButton(212)){
//                    // chefs
//                    role = new Role(6);
//                }
//                else if(isButton(237)){
//                    role = new Role(5);
//                }
//                else {
//                    // operators
//                    role = new Role(4);
//                }
//                stuffToEdit = userRepository.findAllByRolesContains(role);
//
//                showStuff();
//                waitingType = WaitingType.CHOOSE_EMPLOYEE;
//                return COMEBACK;
//            case CHOOSE_EMPLOYEE:
//                deleteMessage(updateMessageId);
//
//                if (hasCallbackQuery()) {
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        editMessageWithKeyboard(getText(90), updateMessageId, buttonsLeaf.getListButtonInline());
//                    }
//                    deleteMessage(updateMessageId);
//                    user = stuffToEdit.stream()
//                            .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                            .findAny()
//                            .orElse(null);
//                    showEmployee();
//                    waitingType = WaitingType.SHOW_EMPLOYEE;
//                }
//                else {
//                    wrongData();
//                    showStuff();
//                }
//                return COMEBACK;
//            case SHOW_EMPLOYEE:
//                deleteMessage(updateMessageId);
//
//                if (hasCallbackQuery()) {
//                    if (isButton(82)) {
//                        getName();
//                        editWaitingType = WaitingType.EDIT_NAME;
//                    } else if (isButton(86)) {
//                        getPhoto();
//                        editWaitingType = WaitingType.EDIT_PHOTO;
//                    } else if (isButton(85)) {
//                        sendMessageWithKeyboard(getText(96), 16);
//
//                        waitingType = WaitingType.DELETE;
//                        return COMEBACK;
//                    }
//                    else if(isButton(224)){
//                        branches = restaurantBranchRepo.findAll();
//                        showBranches();
//                        editWaitingType = WaitingType.EDIT_BRANCH;
//                    }
//                    else if(isButton(127)){
//                        courier.setPhotoAllowed(true);
//                        courierRepository.save(courier);
//                        showEmployee();
//                        return COMEBACK;
//                    }
//                    else if(isButton(128)){
//                        courier.setPhotoAllowed(false);
//                        courierRepository.save(courier);
//
//                        showEmployee();
//                        return COMEBACK;
//                    }
//                    else if(isButton(129)){
//                        getPhone();
//                        editWaitingType = WaitingType.CHANGE_PHONE;
//
//                    }
//                    else if (isButton(67)) {
//                        showStuff();
//                        waitingType = WaitingType.CHOOSE_EMPLOYEE;
//                        return COMEBACK;
//                    }
//                    waitingType = WaitingType.EDIT_EMPLOYEE;
//                }else if(isButton(126)){
//                    return EXIT;
//                }
//                else {
//                    wrongData();
//                    showEmployee();
//                }
//                return COMEBACK;
//            case EDIT_EMPLOYEE:
//                deleteMessage(updateMessageId);
//
//                if (update.hasMessage()) {
//                    switch (editWaitingType) {
//                        case EDIT_NAME:
//                            if (update.getMessage().hasText()) {
//                                user.setFullName(update.getMessage().getText());
//                            } else {
//                                wrongData();
//                                getName();
//                            }
//                            break;
//                        case EDIT_PHOTO:
//                            if (update.getMessage().hasPhoto()) {
//                                courier.setPhotoUrl(updateMessagePhoto);
//                                courierRepository.save(courier);
//                            } else {
//                                wrongData();
//                                getPhoto();
//                                return COMEBACK;
//                            }
//                            break;
//                        case CHANGE_PHONE:
//                            if(update.getMessage().hasText()){
//                                user.setPhone(update.getMessage().getText());
//
//                            }
//                            else{
//                                wrongData();
//                                getPhone();
//                            }
//
//
//
//                    }
//                    userRepository.save(user);
//                    showEmployee();
//                    waitingType = WaitingType.SHOW_EMPLOYEE;
//                    return COMEBACK;
//
//                }
//                else if(hasCallbackQuery()){
//                    switch (editWaitingType){
//                        case EDIT_BRANCH:
//                            deleteMessage(updateMessageId);
//
//                            if (hasCallbackQuery()) {
//                                if (buttonsLeaf.isNext(updateMessageText)) {
//                                    editMessageWithKeyboard(getText(209), updateMessageId, buttonsLeaf.getListButtonInline());
//                                }
//                                if(isButton(67)){
//                                    showEmployee();
//                                    waitingType = WaitingType.SHOW_EMPLOYEE;
//                                    return COMEBACK;
//                                }
//                                deleteMessage(updateMessageId);
//                                restaurantBranch = branches.stream()
//                                        .filter(fc -> updateMessageText.equals(String.valueOf(fc.getId())))
//                                        .findAny()
//                                        .orElse(null);
//                                user.setRestaurantBranch(restaurantBranch);
//                                user = userRepository.save(user);
//                            } else {
//                                wrongData();
//                                showBranches();
//                            }
//                    }
//                }
//            case DELETE:
//                deleteMessage(updateMessageId);
//                if (hasCallbackQuery()){
//                    if(isButton(56)){
//                        if(isCourier(user.getChatId())){
//                            try{
//                                courierRepository.delete(courier);
//                            }
//                            catch (Exception e){
//                                System.out.println("Deleting error commandId - 26");
//                            }
//                        }
//
////                        userRepository.delete(user);
//                        user.deleteRoleById(role.getId());
//                        user = userRepository.save(user);
//                        stuffToEdit = userRepository.findAllByRolesContains(role);
//
//                        showStuff();
//                        waitingType = WaitingType.CHOOSE_EMPLOYEE;
//                    }
//                    else{
//                        showEmployee();
//                        waitingType = WaitingType.SHOW_EMPLOYEE;
//                    }
//
//                }
//                else{
//                    wrongData();
//                    sendMessageWithKeyboard(getText(96), 16);
//
//                }
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//    private int getBranchName() throws TelegramApiException{
//        return sendMessage(208);
//    }
//    private int showBranches() throws TelegramApiException{
//        List<String> finalList = new ArrayList<>();
//        List<String> finalList2 = new ArrayList<>();
//        branches.forEach((e) -> {
//            finalList.add(e.getId() + " | " + e.getBranchName());
//            finalList2.add(String.valueOf(e.getId()));
//        });
////        finalList.add(back);
////        finalList2.add(String.valueOf(foods.size() + 1));
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10, 35, chatId);
////        editMessageWithKeyboard(getText(39), updateMessageId, buttonsLeaf.getListButtonInline());
//        return sendMessageWithKeyboard(getText(209), buttonsLeaf.getListButtonInlineWithBack());
//    }
//    private void removeRole(List<Role> roles, long idToDelete){
//        roles.removeIf(s -> s.getId() == idToDelete);
//    }
//    private int showStuff() throws TelegramApiException {
//        finalList = new ArrayList<>();
//        finalList2 = new ArrayList<>();
//        if(stuffToEdit.isEmpty()){
//            sendMessage("Тут пусто");
//            return -1;
//        }
//        stuffToEdit.forEach((e) -> {
//            finalList.add(e.getFullName());
//            finalList2.add(String.valueOf(e.getId()));
//        });
//
//        buttonsLeaf = new ButtonsLeaf(finalList, finalList2, 10);
//        return toDeleteKeyboard(sendMessageWithKeyboard(getText(90), buttonsLeaf.getListButtonInline()));
//    }
//
//    private int showEmployee() throws TelegramApiException {
//        int keyboardId = 63;
//        Optional<RestaurantBranch> t = Optional.ofNullable(user.getRestaurantBranch());
//        RestaurantBranch restaurantBranch = t.orElse(new RestaurantBranch("UNDEFINED"));
//        if (isCourier(user.getChatId())) {
//            if(role.getId()==3){
//                keyboardId = 46;
//                courier = courierRepository.findCourierByUser(user);
//                if(courier == null){
//                    courier = new Courier(user, true);
//                    courierRepository.save(courier);
//                }
//                if (courier.isPhotoAllowed()) {
//                    keyboardId = 47;
//                }
//
//                return sendMessageWithPhotoAndKeyboard(String.format(getText(91), user.getFullName(), user.getPhone()),keyboardId, courier.getPhotoUrl(), chatId );
//            }
//            else if(role.getId()==6){
//                return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(214), user.getFullName(), user.getPhone(), restaurantBranch.getBranchName()), 91));
//            }
//            else{
//                return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(91), user.getFullName(), user.getPhone()), keyboardId));
//            }
//        }
//        else{
//            if(role.getId()==6){
//                //todo
//                return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(214), user.getFullName(), user.getPhone(), restaurantBranch.getBranchName()), 91));
//            }
//            return toDeleteKeyboard(sendMessageWithKeyboard(String.format(getText(91), user.getFullName(), user.getPhone()), keyboardId));
//        }
//    }
//    private int getPhone() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_PHONE));
//    }
//    public int getPhoto() throws TelegramApiException {
//        return sendMessage(getText(Const.GET_PHOTO));
//    }
//    private int wrongData() throws TelegramApiException {
//        return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId);
//    }
//
//    private int getName() throws TelegramApiException {
//        return sendMessage(getText(92));
//    }
//}
