package com.example.demo.service;

import com.example.demo.DTO.NotificationCarNumberDTO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Optional;

public interface NotificationService {
    Boolean isOverTIme(String carNumber);
    Timestamp getEnteringCarTimestamp(String carNumber);
    void updateCurrentCarExitTime(HashMap<String, Object> map) throws ParseException;
    void resetNewCarExitTime(String carNumber);
    void updateEnteringTime(String carNumber, Timestamp timestamp);
    void updatePhoneNumber(String carNumber, String phoneNumber);
    String isExistPhoneNumber(String carNumber); Optional<NotificationCarNumberDTO> isExist(String carNumber);
    void NotificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO);
}