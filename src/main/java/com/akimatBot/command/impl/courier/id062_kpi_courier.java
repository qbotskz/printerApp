//package com.akimatBot.command.impl.courier;
//
//import com.akimatBot.command.Command;
//import com.akimatBot.entity.custom.Courier;
//import com.akimatBot.entity.custom.Order;
//import com.akimatBot.entity.standart.Role;
//import com.akimatBot.entity.standart.User;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class id062_kpi_courier extends Command {
//    private User user;
//    private Courier courier;
//    private List<Courier> couriers;
//    private Map<String, Double> courierKpi;
//    private List<Order> orders;
//    private List<User> users;
//    private double maxKpi;
//
//    @Override
//    public boolean execute() throws TelegramApiException, IOException, SQLException, Exception {
//        switch (waitingType){
//            case START:
//                couriers = courierRepository.findAll();
//                courierKpi = new HashMap<>();
//                users = userRepository.findAllByRolesContains(new Role(3));
//                Date date = new Date();
//                user = userRepository.findByChatId(chatId);
//                double myKpi = 0;
//                for(User u: users){
//                    courier = courierRepository.findCourierByUser(u);
//                    orders = orderRepository.findByFinishedTrueAndCourier(u);
//                    Date start = removeTime(firstDay(new Date()));
//                    Date end = addTime(new Date());
//
//                    double gradeSum = 0;
//                    double kpi = 0;
//                    for(Order o:orders){
//
//                        System.out.println("removeTime(o.getOrderedDate()) - "+removeTime(o.getOrderedDate()).toString());
//                        System.out.println("removeTime(date) - "+removeTime(date).toString());
//                        System.out.println("addTime(o.getOrderedDate()) - "+addTime(o.getOrderedDate()).toString());
//                        System.out.println("removeTime(firstDay(date))) - "+removeTime(firstDay(date)).toString());
//                        if(   removeTime(o.getOrderedDate()).before(addTime(date)) &&
//                                addTime(o.getOrderedDate()).after(removeTime(firstDay(date))) ){
////                            System.out.println("Order Id - "+o.getId());
////                            System.out.println("Order date - "+removeTime(o.getOrderedDate()).toString());
////                            System.out.println("First day of current month - " + removeTime(firstDay(date)).toString() );
////                            System.out.println("Now - " + removeTime(date).toString() );
////                            System.out.println("=================================================");
//                            int stars = 0;
//                            try{
//                                stars = o.getReviewGrade().getId();
//                            }
//                            catch (Exception e){
//                            }
//                            if(stars==3){
//                                gradeSum+=2;
//                            }
//                            else if(stars==4){
//                                gradeSum+=3;
//                            }
//                            else if(stars==5){
//                                gradeSum+=5;
//                            }
//                        }
//
//                    }
//                    try{
//                        kpi = orders.size()+((gradeSum/5)/5);
//                        if(u.getChatId().equals(chatId)){
//                            myKpi = kpi;
//                        }
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    courierKpi.put(u.getFullName(), kpi);
//                    System.out.println("Courier: "+u.getFullName()+", KPI - "+kpi);
//                }
////                Map<String, Double> result = courierKpi.entrySet()
////                        .stream()
////                        .sorted(Map.Entry.comparingByValue())
////                        .collect(Collectors.toMap(
////                                Map.Entry::getKey,
////                                Map.Entry::getValue,
////                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//                Map<String, Double> result = courierKpi.entrySet()
//                        .stream()
//                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
//                                (entry1, entry2) -> entry2, LinkedHashMap::new));
//                Iterator<Map.Entry<String, Double>> itr = result.entrySet().iterator();
//                int count = 1;
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                User user = calculateEmployeeOfMonth(userRepository.findAllByRolesContains(new Role(3)));
//                String text="KPI за период " + formatter.format(firstDay(date))+" - "+formatter.format(date)+
//                        "\n⭐ РАБОТНИК МЕСЯЦА - "+user.getFullName()+", KPI -"+maxKpi+"\n==================\n";
//
//
//                while (itr.hasNext()) {
//                    Map.Entry<String, Double> entry = itr.next();
//                    text+=count+". "+entry.getKey()+" - "+entry.getValue()+"\n";
//                    count++;
//                }
//                text+="==================\n";
//                text+="Ваш KPI - "+myKpi;
//                sendMessage(text);
//        }
//        return false;
//    }
//
//    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
//        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
//        list.sort(Map.Entry.comparingByValue());
//
//        Map<K, V> result = new LinkedHashMap<>();
//        for (Map.Entry<K, V> entry : list) {
//            result.put(entry.getKey(), entry.getValue());
//        }
//
//        return result;
//    }
//
//    private User calculateEmployeeOfMonth(List<User> couriers){
//
//        Date start = removeTime(firstDay(new Date()));
//        Date end = addTime(new Date());
//
//        int employeeOfMonth = 0;
//        maxKpi=-10;
//        long maxCourierId=-1;
//        for(User u:couriers){
//            orders = orderRepository.findOrdersByCourierAndOrderedDateBetweenAndFinishedTrue(u, start, end);
//            double kpi = calculateKpi();
//            if(kpi>maxKpi){
//                maxKpi = kpi;
//                maxCourierId = u.getId();
//            }
//
//        }
//        return userRepository.findById(maxCourierId);
//    }
//    private double calculateKpi(){
//        double gradeSum = 0;
//        double kpi  =0;
//
//        Date date  = new Date();
//        for(Order o:orders){
//            if(   removeTime(o.getOrderedDate()).before(addTime(date)) &&
//                    addTime(o.getOrderedDate()).after(removeTime(firstDay(date))) ){
//                int stars = 0;
//                try{
//                    stars = o.getReviewGrade().getId();
//                }
//                catch (Exception e){
//                }
//                if(stars==3){
//                    gradeSum+=2;
//                }
//                else if(stars==4){
//                    gradeSum+=3;
//                }
//                else if(stars==5){
//                    gradeSum+=5;
//                }
//            }
//
//        }
//        try{
//            kpi = orders.size()+((gradeSum/5)/5);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return kpi;
//    }
//    public static Date firstDay(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        return cal.getTime();
//    }
//    public static Date removeTime(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.set(Calendar.HOUR, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 00);
//        cal.set(Calendar.MILLISECOND, 0000);
//        return cal.getTime();
//    }
//    public static Date addTime(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.set(Calendar.HOUR, 23);
//        cal.set(Calendar.MINUTE, 59);
//        cal.set(Calendar.SECOND, 59);
//        cal.set(Calendar.MILLISECOND, 0);
//        return cal.getTime();
//    }
//}
