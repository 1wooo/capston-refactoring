package com.example.demo.service;

import com.example.demo.DTO.MessageDTO;
import com.example.demo.DTO.carNumber;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Notification_Thread extends Thread {
    private TableServiceInterface tableServiceInterface;
    private SmsService smsService;
    private int sec;
    private String carNumber;
    public Notification_Thread(SmsService smsService, TableServiceInterface tableServiceInterface, String carNumber) {
        this.sec = 0;
        this.tableServiceInterface = tableServiceInterface;
        this.smsService = smsService;
        this.carNumber = carNumber;
    }
    public void run() {
        MessageDTO sendMsg = new MessageDTO();
//        System.out.println(this.seq + " thread start.");  // 쓰레드 시작
        while (true) {
            try {
                Thread.sleep(1000);  // 1초 대기한다.
            } catch (Exception e) {
            }
            sec += 1;
            if (tableServiceInterface.isOverTIme(carNumber)){
                String phone = tableServiceInterface.isExistPhoneNumber(carNumber);
                com.example.demo.DTO.carNumber illegarCarNumberDTO = new carNumber();
                illegarCarNumberDTO.setCarN(carNumber);
                illegarCarNumberDTO.setFine(100000);
                illegarCarNumberDTO.setIllegalCode(1);
                illegarCarNumberDTO.setTimestamp(tableServiceInterface.getEnteringCarTimestamp(carNumber));
                tableServiceInterface.illegalCarRegister(illegarCarNumberDTO);

                if (phone != null){
                    sendMsg.setContent("법적 허용 충전시간 초과");
                    sendMsg.setTo(phone);
                    try {
                        smsService.sendSms(sendMsg);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
            if (sec == 30){
                String phoneN = tableServiceInterface.isExistPhoneNumber(carNumber);
                if (phoneN != null){
                    sendMsg.setContent("주차시간 30분 소요되었습니다.");
                    sendMsg.setTo(phoneN);
                    try {
                        smsService.sendSms(sendMsg);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }

            } else if (sec == 40) {
                String phoneN = tableServiceInterface.isExistPhoneNumber(carNumber);
                if (phoneN != null){
                    sendMsg.setContent("주차시간 60분 소요되었습니다.");
                    sendMsg.setTo(phoneN);
                    try {
                        smsService.sendSms(sendMsg);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 1시간 알림
            } else if (sec == 55) {
                String phoneN = tableServiceInterface.isExistPhoneNumber(carNumber);
                if (phoneN != null){
                    sendMsg.setContent("허용 주차시간이 종료되었습니다. 출차해주시기 바랍니다.");
                    sendMsg.setTo(phoneN);
                    try {
                        smsService.sendSms(sendMsg);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 시간 종료 알림
                break;
            }
        }

//        System.out.println(this.seq + " thread end.");  // 쓰레드 종료
    }
}
