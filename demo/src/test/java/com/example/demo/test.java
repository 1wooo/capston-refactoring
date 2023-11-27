//package com.example.demo;
//
//import com.example.demo.DTO.carNumber;
//import com.example.demo.s3.S3Service;
//import com.example.demo.service.Notification_Thread;
//import com.example.demo.service.SmsService;
//import com.example.demo.service.TableServiceInterface;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//
//import java.sql.Timestamp;
//import java.util.Calendar;
//
//@SpringBootTest
//public class test {
//
//    @Autowired
//    SmsService smsService;
//    @Autowired
//    TableServiceInterface tableServiceInterface;
//    @Autowired
//    S3Service s3Service;
//
//    @Test
//    public void test() {
//
//        Notification_Thread th1 = new Notification_Thread(this.smsService, this.tableServiceInterface, "65나3482");
//        th1.start();
//
//        while (th1.isAlive()) {
//        }
//    }
//
//
////    @Test
////    public void te() {
////
////        Timestamp enterTime = Timestamp.valueOf("2022-11-30 19:01:01.00000");
////        Timestamp exitTime = Timestamp.valueOf("2022-11-30 20:01:55.00000");
////
////        Calendar enTime = Calendar.getInstance();
////        Calendar exTime = Calendar.getInstance();
////
////        enTime.setTime(enterTime);
////        exTime.setTime(exitTime);
////
////        int diffHour = exTime.get(Calendar.HOUR) - enTime.get(Calendar.HOUR);
////        int diffMin = exTime.get(Calendar.MINUTE) - enTime.get(Calendar.MINUTE);
////
////        System.out.println(diffMin);
////        System.out.println(diffHour);
////    }
////    @Test
////    @Commit
////    public void updatePhoneNumberTest() {
////
////        tableServiceInterface.updatePhoneNumber("12주1233", "01098442244");
////
////    }
////
////    @Test
////    @Transactional
////    public void test3() {
////        String existPhoneNumber = tableServiceInterface.isExistPhoneNumber("12주1233");
////        String ex2 = tableServiceInterface.isExistPhoneNumber("73더9872");
////        Assertions.assertThat(existPhoneNumber).isEqualTo("01041418835");
////        Assertions.assertThat(ex2).isEqualTo(null);
////
////    }
////
////    @Test
////    public void SmsTest() throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
////        MessageDTO testSMS = new MessageDTO();
////
////        testSMS.setTo("01088178254");
////        testSMS.setContent("ㅋㅋ엄준식 API Test ㅋㅋ ㅋㅋ ㅋㅋ ㅋㅋ ㅋㅋ 엄준식~~~~~~~");
////
////        smsService.sendSms(testSMS);
////    }
//}
