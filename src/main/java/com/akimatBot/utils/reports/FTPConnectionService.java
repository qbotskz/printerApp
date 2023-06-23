package com.akimatBot.utils.reports;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FTPConnectionService {

    /*ftp://itonline.kz:50021/bot

        log: $altai

        pass: @99abc123
        */

    private final   String          server     =  "itonline.kz";//"91.215.136.38";
    private final   int             port       =  50021;

    private final   String          directory       =  "/bot/";
    private final   String          user       =  "$altai";//"Администратор";
    private final   String          password   =  "@99abc123";//"DreamTeam2022";
    private         FTPClient       ftp;
    private         boolean         success;
    private         List<FTPFile>   ftpFiles;

    public FTPClient     connectInit() {
        ftp                 = new FTPClient();
        try {
            ftp.connect(server, port);

            showServerReply(ftp);
            int replyCode   = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode))
                log.error("can't connect ftp server");
            boolean success = ftp.login(user, password);
            showServerReply(ftp);
            ftp.changeWorkingDirectory(directory);
            System.out.println("Current directory ->" + ftp.printWorkingDirectory());

            if (!success) {
                log.error("Error log to server!");
            } else {
                log.info("Logged in server!");
                return ftp;
            }

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            ftp = null;
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("Error FtpConnection : ", e); }
        return null;
    }

    public void uploadFile(String fileName, FileInputStream fileInputStream){

        try {

            FileInputStream fs = new FileInputStream("src/main/resources/images/123.jpg");

            boolean asd =  ftp.storeFile(fileName, fs);
//            boolean asd =  ftp.storeFile(fileName, fs);

            System.out.println("Inserted -> " + asd);
            System.out.println("Reply code -> " + ftp.getReplyCode());

            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean  searchFile(String fileName) {
        FTPFile checkFile = ftpFiles.stream().filter(ftpFile -> fileName.equals(ftpFile.getName())).findAny().orElse(null);
        if (checkFile != null) return true;
        return false;
    }
    public List<File>  getAllFiles() {
        try {
            List<File> files = new ArrayList<>();
            System.out.println("Before ftp.listFiles()");
            for (FTPFile ftpFile : getListFtpFiles()) {
                for (int i = 0; i < 20; i++) {
                    try {
                        System.out.println("Attempt " + (i + 1) + " " + ftpFile.getName());
                        ftp.setDataTimeout(2 * 1000);
                        ftp.setDefaultTimeout(2 * 1000);
                        ftp.setSoTimeout(2 * 1000);
                        ftp.setConnectTimeout(2 * 1000);
                        ftp.setControlKeepAliveReplyTimeout(2 * 1000);

                        InputStream iStream = ftp.retrieveFileStream(ftpFile.getName());
                        File file = new File(ftpFile.getName());
                        FileUtils.copyInputStreamToFile(iStream, file);
                        if (file.length() <1){
                            System.out.println("File lenght = 0");
                            i--;
                            continue;
                        }

                        files.add(file);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (i == 4 || i == 12) {
                            connectInit();
                        }
                        e.printStackTrace();
                    }
                    if (i == 9) {
                        System.out.println("Failure!!!");
                    }
                }

            }

            return files;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    private FTPFile[] getListFtpFiles() {
        for (int i = 0; i < 500; i++) {
            try{
                connectInit();
                System.out.println("Attempt " + (i+1));
                ftp.setDataTimeout(3 * 1000);
                ftp.setDefaultTimeout(3 * 1000);
                ftp.setSoTimeout(3* 1000);
                ftp.setConnectTimeout(3 * 1000);
                ftp.setControlKeepAliveReplyTimeout(3 * 1000);
                FTPFile[] asd = ftp.listFiles();
//                System.out.println(ftp.listDirectories().length  + " directory size");
                if (asd.length>0) {
                    return asd ;
                }
                System.out.println("FTP Files size =" + asd.length);
            }catch (Exception e){
                if (i == 8 || i == 15){
                    connectInit();
                }
                e.printStackTrace();
            }
            if (i == 9){
                System.out.println("Failure!!!");
            }
        }
        return null;
    }
//    public List<FTPFile>  getAllFiles() {
//        try {
//            return Arrays.asList(ftp.listDirectories());
//        } catch (Exception e) {
//            e.printStackTrace();
////            throw new RuntimeException(e);
//        }
//        return new ArrayList<>();
//
//    }

    public File     downloadFile(String fileName, String fileNameServer) throws IOException {
        File downloadFile           = new File(fileName);
        OutputStream outputStream   = new BufferedOutputStream(new FileOutputStream(downloadFile));
        System.out.println("Step 4 " + fileName + " " + fileNameServer);

        if (ftp != null) {
            for (int i = 0; i < 10; i++) {
                try{
                    System.out.println("Attempt " + (i+1));
                    ftp.setDataTimeout(2 * 1000);
                    ftp.setDefaultTimeout(2 * 1000);
                    ftp.setSoTimeout(2 * 1000);
                    ftp.setConnectTimeout(2 * 1000);
                    ftp.setControlKeepAliveReplyTimeout(2 * 1000);

                    success = ftp.retrieveFile(fileNameServer, outputStream);
                    break;
                }catch (Exception e){
                    if (i == 4){
                        connectInit();
                    }
                    e.printStackTrace();
                }
                if (i == 9){
                    System.out.println("Failure!!!");
                }
            }
            System.out.println("Step 667");

        }

        outputStream.close();
        return downloadFile;
    }


    public boolean  deleteFile(String fileName) throws IOException { return ftp.deleteFile(fileName); }

    private void    showServerReply(FTPClient ftp) {
        String[] relies = ftp.getReplyStrings();
        if (relies != null && relies.length > 0) for (String aReply : relies) log.info("Server : " + aReply);
    }
}
