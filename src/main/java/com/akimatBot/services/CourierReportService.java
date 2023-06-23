//package com.akimatBot.services;
//
//import com.akimatBot.config.Bot;
//import com.akimatBot.entity.custom.*;
//import com.akimatBot.entity.enums.Language;
//import com.akimatBot.entity.standart.User;
//import com.akimatBot.repository.TelegramBotRepositoryProvider;
//import com.akimatBot.repository.repos.*;
//import com.akimatBot.utils.BotUtil;
//import com.akimatBot.utils.Const;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFColor;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.telegram.telegrambots.bots.DefaultAbsSender;
//import org.telegram.telegrambots.meta.api.methods.GetFile;
//import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.awt.Color;
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class CourierReportService {
//    protected MessageRepository messageRepository                           = TelegramBotRepositoryProvider.getMessageRepository();
//    protected KeyboardMarkUpRepository keyboardMarkUpRepository             = TelegramBotRepositoryProvider.getKeyboardMarkUpRepository();
////    protected AdminRepository adminRepository                               = TelegramBotRepositoryProvider.getAdminRepository();
//    protected UserRepository userRepository                                 = TelegramBotRepositoryProvider.getUserRepository();
//    protected ButtonRepository buttonRepository                             = TelegramBotRepositoryProvider.getButtonRepository();
//
//
//
//
//    protected PropertiesRepo propertiesRepo                                 = TelegramBotRepositoryProvider.getPropertiesRepo();
//
//    protected OrderRepository orderRepository = TelegramBotRepositoryProvider.getOrderRepository();
//    protected CourierRepository courierRepository = TelegramBotRepositoryProvider.getCourierRepository();
//
//
//    private XSSFWorkbook            workbook                = new XSSFWorkbook();
//    private XSSFCellStyle           style                   = workbook.createCellStyle();
//    private Language currentLanguage         = Language.ru;
//    private Sheet                   sheets;
//    private Sheet                   sheet;
//    private int sum = 0;
//
//    private int msgId;
//    private BotUtil botUtil;
//    private String location;
//    public void sendCourierReport(long chatId, DefaultAbsSender bot, List<Order> orders) {
//        try {
//            botUtil = new BotUtil(bot);
//            msgId = botUtil.sendMessage("Отчет подготавливается...", chatId);
//
//            sendCompReport(chatId, bot, orders);
//        }
//        catch (Exception e) {
//            log.error("Can't create/send report", e);
//            try {
//                bot.execute(new SendMessage(chatId,"Ошибка при создании отчета"));
//            } catch (TelegramApiException ex) {
//                log.error("Can't send message", ex);
//            }
//        }
//    }
//    public void sendChefReport(long chatId, DefaultAbsSender bot, List<Order> orders) {
//        try {
//            msgId = botUtil.sendMessage("Отчет подготавливается...", chatId);
//
//            sendChefReport2(chatId, bot, orders);
//        }
//        catch (Exception e) {
//            log.error("Can't create/send report", e);
//            try {
//                bot.execute(new SendMessage(chatId,"Ошибка при создании отчета"));
//            } catch (TelegramApiException ex) {
//                log.error("Can't send message", ex);
//            }
//        }
//    }
//    public void sendSuggestionComplaintReport(long chatId, DefaultAbsSender bot,List<SuggestionComplaint> suggestionComplaints) {
//        try {
//            botUtil = new BotUtil(bot);
//            msgId = botUtil.sendMessage("Отчет подготавливается...", chatId);
//
//            makeSuggestionComplaintReport(chatId, bot, suggestionComplaints);
//        }
//        catch (Exception e) {
//            log.error("Can't create/send report", e);
//            try {
//                bot.execute(new SendMessage(chatId,"Ошибка при создании отчета"));
//            } catch (TelegramApiException ex) {
//                log.error("Can't send message", ex);
//            }
//        }
//    }
//    public void sendCourierKpiReport(long chatId, DefaultAbsSender bot,List<Order> orders, Date start, Date end) {
//        try {
//            botUtil = new BotUtil(bot);
//            List<Courier> couriers = courierRepository.findAll();
//            msgId = botUtil.sendMessage("Отчет подготавливается...", chatId);
//
//            sendKpiReport(chatId, bot,couriers, orders, start, end);
//        }
//        catch (Exception e) {
//            log.error("Can't create/send report", e);
//            try {
//                bot.execute(new SendMessage(chatId,"Ошибка при создании отчета"));
//            } catch (TelegramApiException ex) {
//                log.error("Can't send message", ex);
//            }
//        }
//    }
//    public void sendAdminReport(long chatId, DefaultAbsSender bot, List<Order> orders) {
//        try {
//            botUtil = new BotUtil(bot);
//            msgId = botUtil.sendMessage("Отчет подготавливается...", chatId);
//
//            sendCompReport(chatId, bot, orders);
//        }
//        catch (Exception e) {
//            log.error("Can't create/send report", e);
//            try {
//                bot.execute(new SendMessage(chatId,"Ошибка при создании отчета"));
//            } catch (TelegramApiException ex) {
//                log.error("Can't send message", ex);
//            }
//        }
//    }
//    protected <T> Map<Food, Long> countByClassicalLoop(List<Food> inputList) {
//        Map<Food, Long> resultMap = new LinkedHashMap<>();
//        for (Food element : inputList) {
//            if (resultMap.containsKey(element)) {
////                if(element.getId() == resultMap.)
//                resultMap.put(element, resultMap.get(element) + 1L);
//            } else {
//                resultMap.put(element, 1L);
//            }
//        }
//        return resultMap;
//    }
//    private String isFinishedToString(boolean isFinsihed){
//        if(isFinsihed) return "Завершен";
//        return "В процессе";
//    }
//    private String getFoodList(Order order){
//        String text = "";
//        if(order.getFoods()==null||order.getFoods().isEmpty()){
//            return text;
//        }
//
//        Map<Food, Long> foodMap = countByClassicalLoop(order.getFoods());
//        Iterator<Map.Entry<Food, Long>> itr = foodMap.entrySet().iterator();
//        int count = 1;
//
//        while (itr.hasNext()) {
//            Map.Entry<Food, Long> entry = itr.next();
//            if(entry.getKey().getSpecialOfferSum()!=null){
//                text += count + ". " + entry.getKey().getNameRu() + " " + (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) + "₸ x " + entry.getValue() + "шт = " + (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue() + "₸\n";
//                sum += (entry.getKey().getFoodPrice(order.getUser().getCity()) - entry.getKey().getSpecialOfferSum()) * entry.getValue();
//
//            }
//            else{
//                text += count + ". " + entry.getKey().getNameRu() + " " + entry.getKey().getFoodPrice(order.getUser().getCity()) + "₸ x " + entry.getValue() + "шт = " + entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue() + "₸\n";
//                sum += entry.getKey().getFoodPrice(order.getUser().getCity()) * entry.getValue();
//
//            }
//            count++;
//        }
//        text+="Сумма: "+sum;
//        return text;
//    }
//    private int calculateSum(List<Order> orderList){
//        int sum = 0;
//        for(Order o:orderList){
//            sum+=o.getSum();
//        }
//        return sum;
//
//    }
//    private void parseLocation(String s){
//        Pattern pattern = Pattern.compile("<a( +.+)*>((.*))</a>");
//        Matcher matcher;
//        try {
//             matcher = pattern.matcher(s);
//        }
//        catch (NullPointerException e){
//            s = "undefined";
//            location=s;
//            return;
//        }
//        location = s;
//        if (matcher.find())
//        {
//            location = matcher.group(2);
//        }
//
//    }
//    private void            sendCompReport(long chatId, DefaultAbsSender bot, List<Order> orders) throws TelegramApiException, IOException {
//        sheets                      = workbook.createSheet("Отчет");
//        sheet                       = workbook.getSheetAt(0);
//
//        if (orders == null || orders.size() == 0) {
//            bot.execute(new SendMessage(chatId, "Записи отсутствуют"));
//            return;
//        }
//
//        BorderStyle thin            = BorderStyle.THIN;
//        short black                 = IndexedColors.BLACK.getIndex();
//        short red                   = IndexedColors.RED.getIndex();
//        XSSFCellStyle styleTitle    = setStyle(workbook, thin, black, style);
//        int rowIndex                = 0;
//        createTitle(styleTitle, rowIndex, Arrays.asList("№;Источник;Данные клиента;Заказ;Сумма;Адрес;Способ оплаты;Статус;Причина отмены;Дата создания;Дата доставки;Данные курьера;Оценка".split(Const.SPLIT)));
//        List<List<String>> order = orders.stream().map(x -> {
//            List<String> list = new ArrayList<>();
////            if (x.getIdStatus() <=2){
////                appealTaskArchive = appealTaskArchiveDao.getOneByTaskId(x.getId()); // x.
////            }else {
////                appealTaskArchive = new AppealTaskArchive();
////            }
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//            list.add(String.valueOf(x.getId()));
//            list.add(x.getOrderType().getName());
//            list.add(x.getUser().getFullName()+"\n"+x.getUser().getPhone());
//            list.add(getFoodList(x));
//            list.add(String.valueOf(sum));
//
//            parseLocation(x.getLocation());
//            list.add(location);
//            list.add(x.getPaymentMethod().getName(1));
//
//
//            if(x.getRefuseReason()==null){
//                list.add(isFinishedToString(x.isFinished()));
//                list.add("-");
//            }
//            else{
//                list.add("Отменен");
//                list.add(x.getRefuseReason());
//            }
//
//
//            list.add(formatter.format(x.getOrderedDate()));
//
//            if(x.getDeliveredDate()!=null) list.add(formatter.format(x.getDeliveredDate()));
//            else{
//                list.add("-");
//            }
//
//
//
//            if(x.getCourier()!=null){
//                list.add(x.getCourier().getFullName()+"\n"+x.getCourier().getPhone());
//            }
//            else{
//                list.add("-");
//            }
//            if(x.getReviewGrade()!=null){
//                if(x.getReviewText()==null || x.getReviewText().equals("null")){
//                    list.add(x.getReviewGrade().getStar()+"\n"+"-");
//                }
//                else{
//                    list.add(x.getReviewGrade().getStar()+"\n"+x.getReviewText());
//                }
//            }
//            else {
//                list.add("-");
//            }
//
//            return list;
//        }).collect(Collectors.toList());
//        addInfo(order, rowIndex);
//        sendFile(chatId, bot);
//    }
//
//    private void            sendChefReport2(long chatId, DefaultAbsSender bot, List<Order> orders) throws TelegramApiException, IOException {
//        sheets                      = workbook.createSheet("Отчет");
//        sheet                       = workbook.getSheetAt(0);
//
//        if (orders == null || orders.size() == 0) {
//            bot.execute(new SendMessage(chatId, "Записи отсутствуют"));
//            return;
//        }
//
//        BorderStyle thin            = BorderStyle.THIN;
//        short black                 = IndexedColors.BLACK.getIndex();
//        short red                   = IndexedColors.RED.getIndex();
//        XSSFCellStyle styleTitle    = setStyle(workbook, thin, black, style);
//        int rowIndex                = 0;
//        createTitle(styleTitle, rowIndex, Arrays.asList("№;Данные повара;Дата;Заказ;Сумма;Статус;Причина отмены;Данные курьера".split(Const.SPLIT)));
//        List<List<String>> order = orders.stream().map(x -> {
//            List<String> list = new ArrayList<>();
////            if (x.getIdStatus() <=2){
////                appealTaskArchive = appealTaskArchiveDao.getOneByTaskId(x.getId()); // x.
////            }else {
////                appealTaskArchive = new AppealTaskArchive();
////            }
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//            list.add(String.valueOf(x.getId()));
//            if(x.getChef()!=null){
//                list.add(x.getChef().getFullName()+", "+x.getChef().getPhone());
//            }
//            else{
//                list.add("-");
//            }
//
//            list.add(formatter.format(x.getOrderedDate()));
//            list.add(getFoodList(x));
//            list.add(String.valueOf(sum));
//
//            if(x.getRefuseReason()==null){
//                list.add(isFinishedToString(x.isFinished()));
//                list.add("-");
//            }
//            else{
//                list.add("Отменен");
//                list.add(x.getRefuseReason());
//            }
//            if(x.getCourier()!=null){
//                list.add(x.getCourier().getFullName()+", "+x.getCourier().getPhone());
//            }else{
//                list.add("-");
//            }
//
//
//
//            return list;
//        }).collect(Collectors.toList());
//        addInfo(order, rowIndex);
//        sendFile(chatId, bot);
//    }
//
//    private void            makeSuggestionComplaintReport(long chatId, DefaultAbsSender bot, List<SuggestionComplaint> suggestionComplaints) throws TelegramApiException, IOException {
//        sheets                      = workbook.createSheet("Отчет");
//        sheet                       = workbook.getSheetAt(0);
//
//        if (suggestionComplaints == null || suggestionComplaints.size() == 0) {
//            bot.execute(new SendMessage(chatId, "Записи отсутствуют"));
//            return;
//        }
//
//        BorderStyle thin            = BorderStyle.THIN;
//        short black                 = IndexedColors.BLACK.getIndex();
//        short red                   = IndexedColors.RED.getIndex();
//        XSSFCellStyle styleTitle    = setStyle(workbook, thin, black, style);
//        int rowIndex                = 0;
//        createTitle(styleTitle, rowIndex, Arrays.asList("№;ФИО;Номер телефона;Текст;Тип сообщения;Ответ;Ответсвенный;Дата".split(Const.SPLIT)));
//        List<List<String>> order = suggestionComplaints.stream().map(x -> {
//            List<String> list = new ArrayList<>();
////            if (x.getIdStatus() <=2){
////                appealTaskArchive = appealTaskArchiveDao.getOneByTaskId(x.getId()); // x.
////            }else {
////                appealTaskArchive = new AppealTaskArchive();
////            }
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//
//            list.add(String.valueOf(x.getId()));
//            list.add(x.getUser().getFullName());
//            list.add(x.getUser().getPhone());
//            list.add(x.getText());
//            list.add(x.getAppealType().getName());
//
//
//
//            if(x.getResponse()==null || x.getResponse().equals("null")){
//                list.add("-");
//
//            }
//            else{
//                list.add(x.getResponse());
//            }
//            if(x.getResponsible()!=null){
//                list.add(x.getResponsible().getFullName());
//            }
//            else{
//                list.add("-");
//            }
//            list.add(formatter.format(x.getUploadedDate()));
//            return list;
//        }).collect(Collectors.toList());
//        addInfo(order, rowIndex);
//        sendFile(chatId, bot);
//    }
//    private double calculateKpi(List<Order> orders){
//        double gradeSum = 0;
//        double kpi  =0;
//
//        for(Order o:orders){
//            int stars = 0;
//            try{
//                stars = o.getReviewGrade().getId();
//            }
//            catch (Exception e){
//            }
//            if(stars==3){
//                gradeSum+=2;
//            }
//            else if(stars==4){
//                gradeSum+=3;
//            }
//            else if(stars==5){
//                gradeSum+=5;
//            }
//        }
//        try{
//            kpi = orders.size()+((gradeSum/5)/5);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return kpi;
//    }
//    private int calculateEmployeeOfMonth(User user, List<Courier> couriers){
//
//        List<User> courierUsers = new ArrayList<>();
//        for(Courier courier:couriers){
//            courierUsers.add(courier.getUser());
//        }
//        int employeeOfMonth = 0;
//        double maxKpi=-10;
//        long maxCourierId=-1;
//        for(User u:courierUsers){
//            List<Order> orders = orderRepository.findOrdersByCourier(u);
//            double kpi = calculateKpi(orders);
//            if(kpi>maxKpi){
//                maxKpi = kpi;
//                maxCourierId = u.getId();
//            }
//            if(maxCourierId == user.getId()){
//                employeeOfMonth++;
//            }
//        }
//        return employeeOfMonth;
//    }
//    private int calculateRefuses(List<Order> orders){
//        int count = 0;
//        for(Order order:orders){
//            if(order.getRefuseReason()!=null){
//                count++;
//            }
//        }
//        return count;
//    }
//    private void            sendKpiReport(long chatId, DefaultAbsSender bot, List<Courier> couriers, List<Order> orderList, Date start, Date end) throws TelegramApiException, IOException {
//        sheets                      = workbook.createSheet("Отчет");
//        sheet                       = workbook.getSheetAt(0);
//
//        if (couriers == null || couriers.size() == 0) {
//            bot.execute(new SendMessage(chatId, "Записи отсутствуют"));
//            return;
//        }
//
//        BorderStyle thin            = BorderStyle.THIN;
//        short black                 = IndexedColors.BLACK.getIndex();
//        short red                   = IndexedColors.RED.getIndex();
//        XSSFCellStyle styleTitle    = setStyle(workbook, thin, black, style);
//        int rowIndex                = 0;
//        createTitle(styleTitle, rowIndex, Arrays.asList("№;Курьер;Количество заказов;Отмены заказов;Общая сумма заказов;Сотрудник месяца;Оценка".split(Const.SPLIT)));
//
//        List<List<String>> couriersInList = couriers.stream().map(x -> {
//            List<String> list = new ArrayList<>();
//
//            List<Order> ordersOfHim = new ArrayList<>();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//            for(Order o:orderList){
//                if(o.getCourier()!=null){
//                    if(o.getCourier().getChatId().equals(x.getUser().getChatId())){
//                        ordersOfHim.add(o);
//                    }
//                }
//
//            }
//            list.add(String.valueOf(x.getId()));
//            list.add(x.getUser().getFullName()+"\n"+x.getUser().getPhone());
//            list.add(String.valueOf(ordersOfHim.size()));
//            list.add(String.valueOf(calculateRefuses(ordersOfHim)));
//            list.add(calculateSum(ordersOfHim)+"₸");
//            list.add(String.valueOf(calculateEmployeeOfMonth(x.getUser(), couriers)));
//            list.add(String.valueOf(calculateKpi(ordersOfHim)));
//            return list;
//        }).collect(Collectors.toList());
//        addInfo(couriersInList, rowIndex);
//        sendFile(chatId, bot);
//    }
//
//
//
//    private int getLanguageId(long chatId) {
//        return LanguageService.getLanguage(chatId).getId();
//    }
//
//    private void            addInfo(List<List<String>> reports, int rowIndex) {
//        for (List<String> report : reports) {
//            sheets.createRow(++rowIndex);
//            insertToRow(rowIndex, report, style);
//        }
//        for (int i = 0; i< 21; i++){
//            if (i == 9 || i == 10 || i == 17) {
//                sheets.setColumnWidth(9, 20000);
//                sheets.setColumnWidth(10, 20000);
//                sheets.setColumnWidth(17, 20000);
//                continue;
//            }
//            sheets.autoSizeColumn(i);
//        }
//    }
//
//    private void            createTitle(XSSFCellStyle styleTitle, int rowIndex, List<String> title) {
//        sheets.createRow(rowIndex);
//        insertToRow(rowIndex, title, styleTitle);
//    }
//
//    private void            insertToRow(int row, List<String> cellValues, CellStyle cellStyle) {
//        int cellIndex = 0;
//        BorderStyle thin            = BorderStyle.THIN;
//
//        short black                 = IndexedColors.BLACK.getIndex();
//        short red                 = IndexedColors.BLACK.getIndex();
//        short green                 = IndexedColors.GREEN.getIndex();
//        XSSFCellStyle styleTitle    = setStyle(workbook, thin, black, style);
//
//        for (String cellValue : cellValues) {
////            if (cellIndex == 5 || cellIndex == 6){
////                cellStyle = setStyle(workbook, thin, green, style);
////            }
////            else cellStyle = setStyle(workbook, thin, black, style);
//            addCellValue(row, cellIndex++, cellValue, cellStyle);
//        }
//    }
//
//    private void            addCellValue(int rowIndex, int cellIndex, String cellValue, CellStyle cellStyle) {
//        sheets.getRow(rowIndex).createCell(cellIndex).setCellValue(getString(cellValue));
//        sheet.getRow(rowIndex).getCell(cellIndex).setCellStyle(cellStyle);
//    }
//
//
//    private String          getString(String nullable) {
//        if (nullable == null) return "";
//        return nullable;
//    }
//
//
//    private XSSFCellStyle   setStyleWithColor(XSSFWorkbook workbook, BorderStyle thin, short color, XSSFCellStyle style) {
//        style.setWrapText(true);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
//        style.setBorderTop(thin);
//        style.setBorderBottom(thin);
//        style.setBorderRight(thin);
//        style.setBorderLeft(thin);
//        style.setTopBorderColor(color);
//        style.setRightBorderColor(color);
//        style.setBottomBorderColor(color);
//        style.setLeftBorderColor(color);
//        style.getFont().setBold(true);
//        BorderStyle tittle = BorderStyle.MEDIUM;
//
//        XSSFFont titleFont = workbook.createFont();
//        titleFont.setBold(true);
//        titleFont.setColor(color);
//        titleFont.setFontHeight(10);
//
//        XSSFCellStyle styleTitle = workbook.createCellStyle();
//        styleTitle.setWrapText(true);
//        styleTitle.setAlignment(HorizontalAlignment.CENTER);
//        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
//        styleTitle.setBorderTop(tittle);
//        styleTitle.setBorderBottom(tittle);
//        styleTitle.setBorderRight(tittle);
//        styleTitle.setBorderLeft(tittle);
//        styleTitle.setTopBorderColor(color);
//        styleTitle.setRightBorderColor(color);
//        styleTitle.setBottomBorderColor(color);
//        styleTitle.setLeftBorderColor(color);
//        styleTitle.setFont(titleFont);
//        style.setFillForegroundColor(new XSSFColor(new Color(0, 94, 94)));
//        return styleTitle;
//    }
//
//
//    private XSSFCellStyle   setStyle(XSSFWorkbook workbook, BorderStyle thin, short black, XSSFCellStyle style) {
//        style.setWrapText(true);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
//        style.setBorderTop(thin);
//        style.setBorderBottom(thin);
//        style.setBorderRight(thin);
//        style.setBorderLeft(thin);
//        style.setTopBorderColor(black);
//        style.setRightBorderColor(black);
//        style.setBottomBorderColor(black);
//        style.setLeftBorderColor(black);
//        style.getFont().setBold(true);
//        BorderStyle tittle = BorderStyle.MEDIUM;
//
//        XSSFFont titleFont = workbook.createFont();
//        titleFont.setBold(true);
//        titleFont.setColor(black);
//        titleFont.setFontHeight(10);
//
//        XSSFCellStyle styleTitle = workbook.createCellStyle();
//        styleTitle.setWrapText(true);
//        styleTitle.setAlignment(HorizontalAlignment.CENTER);
//        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
//        styleTitle.setBorderTop(tittle);
//        styleTitle.setBorderBottom(tittle);
//        styleTitle.setBorderRight(tittle);
//        styleTitle.setBorderLeft(tittle);
//        styleTitle.setTopBorderColor(black);
//        styleTitle.setRightBorderColor(black);
//        styleTitle.setBottomBorderColor(black);
//        styleTitle.setLeftBorderColor(black);
//        styleTitle.setFont(titleFont);
//        style.setFillForegroundColor(new XSSFColor(new Color(0, 94, 94)));
//        return styleTitle;
//    }
//
//
//
//    private void            sendFile(long chatId, DefaultAbsSender bot) throws IOException, TelegramApiException {
//        File file = new File("C:\\Users\\QBOTS-COMP\\Desktop\\"+"ОТЧЕТ.xlsx");
////        File file = new File("C:\\Users\\Orken\\Desktop\\"+"ОТЧЕТ.xlsx");
//        file.getParentFile().mkdirs();
//        try (FileOutputStream outputStream = new FileOutputStream(file)) {
//            workbook.write(outputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try (FileInputStream fileInputStream = new FileInputStream(file)) {
//            SendDocument sendDocument = new SendDocument();
//            sendDocument.setChatId(Long.toString(chatId));
//            sendDocument.setDocument("ОТЧЕТ.xlsx", fileInputStream);
//            bot.execute(sendDocument);
//            botUtil.deleteMessage(chatId, msgId);
//        }
//
//    }
//
//    private void            sendFile(long chatId, DefaultAbsSender bot, String fileName, String path) throws IOException, TelegramApiException {
//        File file = new File(path);
//        try (FileInputStream fileInputStream = new FileInputStream(file)) {
//            bot.execute(new SendDocument().setChatId(chatId).setDocument(fileName, fileInputStream));
//        }
//        file.delete();
//    }
//
//    private String uploadFile(String fileId){
//        Bot bot = new Bot();
//        Objects.requireNonNull(fileId);
//        GetFile getFile = new GetFile().setFileId(fileId);
//        try{
//            org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFile);
//            return file.getFilePath();
//        } catch (TelegramApiException e){
//            throw new IllegalMonitorStateException();
//        }
//    }
//
//
//}
