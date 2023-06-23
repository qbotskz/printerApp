package com.akimatBot.services;


import com.akimatBot.entity.custom.Food;
import com.akimatBot.entity.enums.Language;
import com.akimatBot.repository.repos.FoodRepository;
import com.akimatBot.web.dto.FoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;



@Service
@Transactional
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    CacheService cacheService;


    public List<Food> searchFood(String foodName, int page, Language language) {
        System.out.println("Degugggggggggggg");
        int a = (page-1)*10 +1;
        int b = page*10;
        if (language.equals(Language.ru))
            return foodRepository.searchRu(foodName, a ,b);
        return foodRepository.searchKz(foodName, a ,b);
    }

//    @Autowired
//    public BookService(BookRepo bookRepo, AuthorRepo authorRepo, GenreRepo genreRepo) {
//        this.foodRepository = bookRepo;
//        this.authorRepo = authorRepo;
//        this.genreRepo = genreRepo;
//    }




//    @Transactional
//    public void saveOrUpdate(BookDTO bookDTO, DefaultAbsSender bot) {
//        Book book = foodRepository.findById(bookDTO.getId());
//        if (book != null) {
//            book.setName(bookDTO.getName());
//            book.addGenre(bookDTO.getGenre());
//            book.setPriceWithDiscount(bookDTO.getPriceWithDiscount());
//            book.setPrice(bookDTO.getPrice());
//
//            book.setAvailable(bookDTO.isAvailable());
//            //book.setId(oldBook.getId());
//
//            if (!book.getPhoto().equals(bookDTO.getPhoto())||book.getPhotoFileId()==null) {
//                book.setPhoto(bookDTO.getPhoto());
//                book.setPhotoFileId(getFileId(bookDTO, bot));
//            }
//
//        } else {
//            book = new Book();
//            book.setId(bookDTO.getId());
//            book.setName(bookDTO.getName());
//            book.addGenre(bookDTO.getGenre());
//            book.setAvailable(bookDTO.isAvailable());
//
//
//            book.setPhoto(bookDTO.getPhoto());
//            book.setPriceWithDiscount(bookDTO.getPriceWithDiscount());
//            book.setPrice(bookDTO.getPrice());
//
//
//            book.setPhotoFileId(getFileId(bookDTO, bot));
//            foodRepository.save(book);
//
//        }
//
//
//        Set<Author> newAuthors = new HashSet<>();
//
//        for (Author author : bookDTO.getAuthors()) {
//            Author author1 = authorRepo.findByName(author.getName());//todo
//            if (author1 == null) {
//                author1 = new Author();
//                author1.setName(author.getName());
//                author1.setId(author.getId());
//            }
//
//
//            if (author1.getBooks() == null) {
//                author1.setBooks(new ArrayList<>(Collections.singletonList(book)));
//            } else {
//                boolean isNeededToAdd = true;
//                for (Book book1 : author1.getBooks()) {//todo
//                    if (book1.getId() == book.getId()) {
//                        isNeededToAdd = false;
//                        break;
//                    }
//                }
//                if (isNeededToAdd) author1.getBooks().add(book);
//            }
//            authorRepo.save(author1);
//            newAuthors.add(author1);
//        }
//        book.setAuthors(newAuthors);
//
//        foodRepository.save(book);
//
//
//    }

//    @Transactional
//    public void setAlUnavailable() {
//        List<Book> allBooks = foodRepository.findAll();
//        for (Book book: allBooks)
//        {
//            book.setAvailable(false);
//            foodRepository.save(book);
//        }
//    }

//
//    private String getFileId(BookDTO bookDTO, DefaultAbsSender bot) {
//        try {
//
//            String destinationFile = "C:\\photos\\" + bookDTO.getId() + ".jpg";
//
//            File theDir = new File("C:\\photos\\");
//            if (!theDir.exists()) {
//                theDir.mkdirs();
//            }
//
//            saveImage(bookDTO.getPhoto(), destinationFile);
//
//            return sendP(destinationFile, bot);
//        } catch (Exception e) {
//            e.printStackTrace();
//            //todo
//
//            SendPhoto sendPhoto = new SendPhoto();
//            sendPhoto.setParseMode("html");
//            sendPhoto.setChatId("671895693");  //Данияр агай
//            //sendPhoto.setChatId("468703155");//todo
//
//
//            InputFile inputFile = new InputFile();
//            inputFile.setMedia(new File("src/main/resources/photos/img.png"));
//            sendPhoto.setPhoto(inputFile);
//
//            try {
//                List<PhotoSize> photoSizes = bot.execute(sendPhoto).getPhoto();
//                PhotoSize photoSize = photoSizes.get(0);
//
//                return photoSize.getFileId();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//            return "AgACAgIAAxkBAAIJTGNyE--auJMprmAmgD0a57ccLAg4AAKnwDEbZ3aRS0Wqtqi2dKAuAQADAgADeQADKwQ";
//        }
//    }
//
//    private String sendP(String url, DefaultAbsSender bot) {
//        SendPhoto sendPhoto = new SendPhoto();
//        sendPhoto.setParseMode("html");
//
//        sendPhoto.setChatId("671895693");  //Данияр агай
////        sendPhoto.setChatId("468703155");//todo
//
//
//
//        InputFile inputFile = new InputFile();
//        inputFile.setMedia(new File(url));
//
//        sendPhoto.setPhoto(inputFile);
//        try {
//            List<PhotoSize> photoSizes = bot.execute(sendPhoto).getPhoto();
//            PhotoSize photoSize = photoSizes.get(0);
//
//            return photoSize.getFileId();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void saveImage(String imageUrl, String destinationFile) throws IOException {
//        URL url = new URL(imageUrl);
//        InputStream is = url.openStream();
//        OutputStream os = new FileOutputStream(destinationFile);
//
//        byte[] b = new byte[2048];
//        int length;
//
//        while ((length = is.read(b)) != -1) {
//            os.write(b, 0, length);
//        }
//
//        is.close();
//        os.close();
//    }

//    public List<Book> findAllByGenreId(int genreId) {
//        return foodRepository.findAllByGenresContainingAndAvailableTrueOrderByNameAsc(genreRepo.findByGenreId(genreId));
//        //return bookRepo.findAllByGenresContainingAndAvailableTrue(genreRepo.findByGenreId(genreId));
//    }
//    public List<Book> findAllByGenreId(int genreId, int page) {
//        return foodRepository.getBooksOfGenre(genreId, ((page-1)*10)+1 , page*10);
//        //return bookRepo.findAllByGenresContainingAndAvailableTrue(genreRepo.findByGenreId(genreId));
//    }

    public Food findById(Long foodId) {
        return foodRepository.findById(foodId);
    }

    public List<Food> getAllStopList() {
        return foodRepository.findAllByRemainsNotNullAndActivatedTrueOrderById();
    }

    public List<FoodDTO> getFoodsDTO(List<Food> foods, Language language) {
        List<FoodDTO> dto = new ArrayList<>();
        for (Food food : foods){
            dto.add(food.getFoodDTO(language));
        }
        return dto;
    }

    public Food save(Food food) {
        return foodRepository.save(food);
    }

    @Transactional
    public Food addToStopList(FoodDTO foodDTO) {
        Food food  = findById(foodDTO.getId());
        if (food != null && foodDTO.getRemains() >= 0) {
            food.setRemains(foodDTO.getRemains());
            food.setLastChanged(new Date());
            cacheService.createFoodCategoriesCache();
            return save(food);
        }
        return null;
    }
    @Transactional
    public boolean removeFromStopList(FoodDTO foodDTO) {
        Food food  = findById(foodDTO.getId());
        if (food != null) {
            food.setRemains(null);
            food.setLastChanged(new Date());
            save(food);
            cacheService.createFoodCategoriesCache();
            return true;
        }
        return false;
    }

//    public List<Book> searchBook(String bookName, int page) {
//        int a = (page-1)*10 +1;
//        int b = page*10;
//        return foodRepository.search(bookName, a ,b);
//    }
}
