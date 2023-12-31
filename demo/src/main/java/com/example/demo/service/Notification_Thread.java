package com.example.demo.service;

import com.example.demo.DTO.MessageDTO;

public class Notification_Thread extends Thread {
    private IllegalCarServiceInterface illegalCarServiceInterface;
    private SmsService smsService;
    private int sec;
    private String carNumber;
    public Notification_Thread(SmsService smsService, IllegalCarServiceInterface illegalCarServiceInterface, String carNumber) {
        this.sec = 0;
        this.illegalCarServiceInterface = illegalCarServiceInterface;
        this.smsService = smsService;
        this.carNumber = carNumber;
    }
    public void run() {
        MessageDTO sendMsg = new MessageDTO();
//        System.out.println(this.seq + " thread start.");  // 쓰레드 시작
//        while (true) {
//            try {
//                Thread.sleep(1000);  // 1초 대기한다.
//            } catch (Exception e) {
//            }
//            sec += 1;
//            if (illegalCarServiceInterface.isOverTIme(carNumber)){
//                String phone = illegalCarServiceInterface.isExistPhoneNumber(carNumber);
//                CarNumber illegalCarNumberDTO = new CarNumber();
//                illegalCarNumberDTO.setCarN(carNumber);
//                illegalCarNumberDTO.setFine(100000);
//                illegalCarNumberDTO.setIllegalCode(1);
//                illegalCarNumberDTO.setTimestamp(illegalCarServiceInterface.getEnteringCarTimestamp(carNumber));
//                illegalCarServiceInterface.illegalCarRegister(illegalCarNumberDTO);
//
//                if (phone != null){
//                    sendMsg.setContent("법적 허용 충전시간 초과");
//                    sendMsg.setTo(phone);
//                    try {
//                        smsService.sendSms(sendMsg);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    } catch (URISyntaxException e) {
//                        throw new RuntimeException(e);
//                    } catch (InvalidKeyException e) {
//                        throw new RuntimeException(e);
//                    } catch (NoSuchAlgorithmException e) {
//                        throw new RuntimeException(e);
//                    } catch (UnsupportedEncodingException e) {
//                        throw new RuntimeException(e);
//                    }
//                    break;
//                }
//            }
//            if (sec == 30){
//                String phoneN = illegalCarServiceInterface.isExistPhoneNumber(carNumber);
//                if (phoneN != null){
//                    sendMsg.setContent("주차시간 30분 소요되었습니다.");
//                    sendMsg.setTo(phoneN);
//                    try {
//                        smsService.sendSms(sendMsg);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    } catch (URISyntaxException e) {
//                        throw new RuntimeException(e);
//                    } catch (InvalidKeyException e) {
//                        throw new RuntimeException(e);
//                    } catch (NoSuchAlgorithmException e) {
//                        throw new RuntimeException(e);
//                    } catch (UnsupportedEncodingException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//
//            } else if (sec == 40) {
//                String phoneN = illegalCarServiceInterface.isExistPhoneNumber(carNumber);
//                if (phoneN != null){
//                    sendMsg.setContent("주차시간 60분 소요되었습니다.");
//                    sendMsg.setTo(phoneN);
//                    try {
//                        smsService.sendSms(sendMsg);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    } catch (URISyntaxException e) {
//                        throw new RuntimeException(e);
//                    } catch (InvalidKeyException e) {
//                        throw new RuntimeException(e);
//                    } catch (NoSuchAlgorithmException e) {
//                        throw new RuntimeException(e);
//                    } catch (UnsupportedEncodingException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                // 1시간 알림
//            } else if (sec == 55) {
//                String phoneN = illegalCarServiceInterface.isExistPhoneNumber(carNumber);
//                if (phoneN != null){
//                    sendMsg.setContent("허용 주차시간이 종료되었습니다. 출차해주시기 바랍니다.");
//                    sendMsg.setTo(phoneN);
//                    try {
//                        smsService.sendSms(sendMsg);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    } catch (URISyntaxException e) {
//                        throw new RuntimeException(e);
//                    } catch (InvalidKeyException e) {
//                        throw new RuntimeException(e);
//                    } catch (NoSuchAlgorithmException e) {
//                        throw new RuntimeException(e);
//                    } catch (UnsupportedEncodingException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                // 시간 종료 알림
//                break;
//            }
//        }
    }
}
