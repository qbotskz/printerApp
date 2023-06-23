package com.akimatBot.utils.reports;

import com.akimatBot.RestoranApplication;
import com.akimatBot.config.Bot;
import com.akimatBot.entity.custom.FoodOrder;
import com.akimatBot.entity.custom.Guest;
import com.akimatBot.entity.custom.OrderItem;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.entity.enums.SocialNetwork;
import com.akimatBot.entity.standart.User;
import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.repository.repos.MessageRepository;
import com.akimatBot.repository.repos.OrderRepository;
import com.akimatBot.repository.repos.PropertiesRepo;
import com.akimatBot.repository.repos.UserRepository;
import com.akimatBot.services.LanguageService;
import com.akimatBot.utils.Const;
import com.akimatBot.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j

public class OrderReportDaily {
    protected MessageRepository messageRepository = TelegramBotRepositoryProvider.getMessageRepository();
    protected UserRepository userRepository = TelegramBotRepositoryProvider.getUserRepository();
    protected PropertiesRepo propertiesRepo = TelegramBotRepositoryProvider.getPropertiesRepo();


    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private final XSSFCellStyle style = workbook.createCellStyle();
    private Sheet sheets;
    private final XSSFCellStyle       hLinkStyle      = workbook.createCellStyle();
    private final XSSFCreationHelper creationHelper  = workbook.getCreationHelper();

    List<FoodOrder> orders;


    OrderRepository orderRepository;

    public OrderReportDaily() {
        orderRepository = TelegramBotRepositoryProvider.getOrderRepository();
    }

    public void sendCompReport() {

            createSummary();
    }

    private void createSummary() {
        sheets = workbook.createSheet("Сводный");
        int rowIndex = 0;


        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 1);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);

        orders = orderRepository.findAllByCreatedDateBetweenOrderById(start.getTime(), end.getTime());

        createTitle(setStyle(), rowIndex,
                Arrays.asList(("Номер чека;Дата;Вид Оплаты;Официант;Товар" +
                ";Ед изм;Количество;Цена;Сумма").split(Const.SPLIT)));
//        rowIndex = 1;

        List<List<String>> reports = new ArrayList<>();

        for (FoodOrder foodOrder : orders){
            for (Guest guest : foodOrder.getGuests()) {
                for (OrderItem item : guest.getOrderItems()) {
                    List<String> list = new ArrayList<>();
                    list.add(String.valueOf(foodOrder.getId()));
                    list.add(DateUtil.getDbMmYyyyHhMmSs(foodOrder.getCreatedDate()));
//                    list.add(foodOrder.getCheque() != null ? foodOrder.getCheque().get().getName() : "");
                    list.add(foodOrder.getWaiter().getFullName());
                    list.add(item.getFood().getFoodName(Language.ru));
                    list.add("");
                    list.add(String.valueOf(item.getQuantity()));
                    list.add(String.valueOf(item.getPrice()));
                    list.add(String.valueOf(item.getQuantity() * item.getPrice()));

                    reports.add(list);
                }
            }
        }

        addInfo(reports, rowIndex);


        sendFile();
//        sendFile(766856789, RestoranApplication.bot);
    }


    private int getLanguageId(long chatId) {
        return LanguageService.getLanguage(chatId).getId();
    }

    private void addInfo(List<List<String>> reports, int rowIndex) {
        for (List<String> report : reports) {
            sheets.createRow(++rowIndex);
            insertToRow(rowIndex, report, style);
//            insertToRowURL(rowIndex, report, hLinkStyle);

        }
        for (int i = 0; i < 15; i++) {
            sheets.autoSizeColumn(i);
        }
    }

    private void            insertToRowURL(int row, List<String> cellValues, CellStyle cellStyle) {
        addCellValueLink(row, 9, cellValues.get(9), cellStyle);
        addCellValueLink(row, 12, cellValues.get(12), cellStyle);
    }

    private void            addCellValueLink(int rowIndex, int cellIndex, String cellValue, CellStyle cellStyle) {
        try {
            XSSFHyperlink link = creationHelper.createHyperlink(HyperlinkType.URL);
            link.setAddress(cellValue);
            sheets.getRow(rowIndex).getCell(cellIndex).setHyperlink(link);
            sheets.getRow(rowIndex).getCell(cellIndex).setCellStyle(cellStyle);
        } catch (Exception e) {
            if (e.getMessage().contains("Address of hyperlink must be a valid URI")) sheets.getRow(rowIndex).getCell(cellIndex).setCellValue(" ");
        }
    }

    private void createTitle(XSSFCellStyle styleTitle, int rowIndex, List<String> title) {
        sheets.createRow(rowIndex);
        insertToRow(rowIndex, title, styleTitle);
    }

    private void insertToRow(int row, List<String> cellValues, CellStyle cellStyle) {
        int cellIndex = 0;
        for (String cellValue : cellValues) {
//            if ((cellIndex == 9 || cellIndex == 12) && row > 0){
//                addCellValue(row, cellIndex++, "Скачать файл", cellStyle);
//            }else{
                addCellValue(row, cellIndex++, cellValue, cellStyle);

//            }
        }
    }

    private void addCellValue(int rowIndex, int cellIndex, String cellValue, CellStyle cellStyle) {
        sheets.getRow(rowIndex).createCell(cellIndex).setCellValue(getString(cellValue));
        sheets.getRow(rowIndex).getCell(cellIndex).setCellStyle(cellStyle);
    }


    private String getString(String nullable) {
        if (nullable == null) return "";
        return nullable;
    }


    private XSSFCellStyle setStyle() {
        short black = IndexedColors.BLACK.getIndex();

        BorderStyle tittle = BorderStyle.THIN;

        XSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setBold(true);
        titleFont.setColor(black);
        titleFont.setFontHeight(10);

        XSSFCellStyle styleTitle = workbook.createCellStyle();
        styleTitle.setWrapText(true);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitle.setBorderTop(tittle);
        styleTitle.setBorderBottom(tittle);
        styleTitle.setBorderRight(tittle);
        styleTitle.setBorderLeft(tittle);
        styleTitle.setTopBorderColor(black);
        styleTitle.setRightBorderColor(black);
        styleTitle.setBottomBorderColor(black);
        styleTitle.setLeftBorderColor(black);
        styleTitle.setFont(titleFont);

        styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        styleTitle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

        return styleTitle;
    }

    private XSSFCellStyle setStyle(short color) {
        short black = IndexedColors.BLACK.getIndex();

        BorderStyle tittle = BorderStyle.THIN;

        XSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setBold(true);
        titleFont.setColor(black);
        titleFont.setFontHeight(10);

        XSSFCellStyle styleTitle = workbook.createCellStyle();
        styleTitle.setWrapText(true);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitle.setBorderTop(tittle);
        styleTitle.setBorderBottom(tittle);
        styleTitle.setBorderRight(tittle);
        styleTitle.setBorderLeft(tittle);
        styleTitle.setTopBorderColor(black);
        styleTitle.setRightBorderColor(black);
        styleTitle.setBottomBorderColor(black);
        styleTitle.setLeftBorderColor(black);
        styleTitle.setFont(titleFont);

        styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        styleTitle.setFillForegroundColor(color);

        return styleTitle;
    }


    private void sendFile()  {
        try {


        String fileName = "Отчет.xlsx";
//        String.format(fileName, new Date().getTime());
        String path = "src/main/resources/reports/" + fileName;
        try  {
            File file = new File(path);
            file.mkdir();
            FileOutputStream stream = new FileOutputStream(path);
            workbook.write(stream);
        } catch (IOException e) {
            log.error("Can't send File error: ", e);
        }

        FTPConnectionService ftpConnectionService = new FTPConnectionService();
        ftpConnectionService.connectInit();
        ftpConnectionService.uploadFile(fileName, new FileInputStream(path));
//        sendFile(766856789, RestoranApplication.bot, fileName, path);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendFile(long chatId, DefaultAbsSender bot, String fileName, String path) throws IOException, TelegramApiException {
        File file = new File(path);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            SendDocument sendDocument = new SendDocument();
            InputFile inputFile = new InputFile();
            inputFile.setMedia(fileInputStream,fileName);

            sendDocument.setDocument(inputFile);
            sendDocument.setChatId("766856789");
            bot.execute(sendDocument);
        }
        file.delete();
    }

    private String uploadFile(String fileId) {
        Bot bot = new Bot();
        Objects.requireNonNull(fileId);
        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);
        try {
            org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFile);
            return file.getFilePath();
        } catch (TelegramApiException e) {
            throw new IllegalMonitorStateException();
        }
    }


}
