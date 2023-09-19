package com.akimatBot.web.controllers.printer;


import com.akimatBot.entity.custom.PDFFilesToPrint;
import com.akimatBot.entity.custom.PrintPayment;
import com.akimatBot.entity.custom.PrintPrecheck;
import com.akimatBot.repository.repos.PDFFilesToPrintRepo;
import com.akimatBot.repository.repos.PrintPaymentRepo;
import com.akimatBot.repository.repos.PrintPrecheckRepo;
import com.akimatBot.web.dto.OrderItemDeleteDTO;
import com.akimatBot.web.dto.PrintKitchenDTO;
import com.akimatBot.web.dto.PrintPaymentDTO;
import com.akimatBot.web.dto.PrintPrecheckDTO;
import com.akimatBot.web.websocets.entities.KitchenPrintEntityRepo;
import com.akimatBot.web.websocets.entities.OrderItemDeleteEntity;
import com.akimatBot.web.websocets.entities.OrderItemDeleteEntityRepo;
import com.akimatBot.web.websocets.entities.PrintKitchenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/printer")
public class PrinterController {


    @Autowired
    KitchenPrintEntityRepo kitchenPrintEntityRepo;

    @Autowired
    OrderItemDeleteEntityRepo orderItemDeleteEntityRepo;
    @Autowired
    PrintPrecheckRepo printPrecheckRepo;

    @Autowired
    PrintPaymentRepo printPaymentRepo;


    @Autowired
    PDFFilesToPrintRepo pdfFilesToPrintRepo;

    private static final String path = "D:/qrestoran/printerDocuments/";

    @GetMapping("/getAvailablePrintKitchens")
    public ResponseEntity<Object> getAllTable() {
        List<PrintKitchenEntity> printKitchenEntities = kitchenPrintEntityRepo.findAllByOrderById();
        List<PrintKitchenDTO> dtos = new ArrayList<>();

        for (PrintKitchenEntity printKitchenEntity : printKitchenEntities) {
            dtos.add(printKitchenEntity.getDTO());
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @GetMapping("/getAllCancelPrint")
    public ResponseEntity<Object> getAllCancelPrint() {
        List<OrderItemDeleteEntity> deleteEntities = orderItemDeleteEntityRepo.findAllByPrintedFalseOrderById();
        List<OrderItemDeleteDTO> dtos = new ArrayList<>();

        for (OrderItemDeleteEntity orderItemDeleteEntity : deleteEntities) {
            dtos.add(orderItemDeleteEntity.getDTO());
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/updatePrintKitchen")
    public void updatePrintKitchen(@RequestBody PrintKitchenDTO printKitchenDTO) {
        System.out.println(printKitchenDTO.toString());
        kitchenPrintEntityRepo.delete(new PrintKitchenEntity(printKitchenDTO.getId()));
    }


    @PostMapping("/updateCancelOrderItem")
    public void updateCancelOrderItem(@RequestBody OrderItemDeleteDTO orderItemDeleteDTO) {
        System.out.println(orderItemDeleteDTO.toString());
        orderItemDeleteEntityRepo.setPrinted(orderItemDeleteDTO.getId());
    }

    //todo precheck

    @GetMapping("/getAllPrecheck")
    public ResponseEntity<Object> getAllPrecheck() {

        List<PrintPrecheck> printPrechecks = printPrecheckRepo.findAllByPrintedFalseOrderById();
        List<PrintPrecheckDTO> dtos = new ArrayList<>();
        for (PrintPrecheck printPrecheck : printPrechecks) {
            dtos.add(printPrecheck.getDTO());
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/updatePrintPrecheck")
    public void updatePrintPrecheck(@RequestBody PrintPrecheckDTO printPrecheckDTO) {
        System.out.println(printPrecheckDTO.toString());
        printPrecheckRepo.setPrinted(printPrecheckDTO.getId());
    }

    //todo payment

    @GetMapping("/getPayments")
    public ResponseEntity<Object> getPayments() {

        List<PrintPayment> printPayments = printPaymentRepo.findAllByOrderById();
        List<PrintPaymentDTO> dtos = new ArrayList<>();
        for (PrintPayment printPayment : printPayments) {
            dtos.add(printPayment.getDTO());
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/updatePayment")
    public void updatePayment(@RequestBody PrintPaymentDTO printPaymentDTO) {
        System.out.println(printPaymentDTO.toString());
        printPaymentRepo.delete(new PrintPayment(printPaymentDTO.getId()));
    }


    @GetMapping("/getNotPrintedFile")
    public ResponseEntity<Resource> getNotPrintedFile() throws IOException {
        PDFFilesToPrint pdfFilesToPrint = pdfFilesToPrintRepo.getNotPrintedFirst();
        if (pdfFilesToPrint != null) {

            File file = new File(path + pdfFilesToPrint.getFileName());

            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");


            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
    }

    @PostMapping("updateNotPrintedFile")
    public void updateNotPrintedFile(@RequestParam String fileName) {
        pdfFilesToPrintRepo.printedByName(fileName);
        System.out.println(fileName);
    }

}
