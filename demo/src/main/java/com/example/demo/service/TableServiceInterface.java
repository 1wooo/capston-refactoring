package com.example.demo.service;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.carNumber;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

public interface TableServiceInterface {
    List<carNumber> getAll();

    void illegalCarRegister(HashMap<String, Object> map) throws ParseException;

    void illegalCarRemove(Long id);

    void NotificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO);

    Optional<NotificationCarNumberDTO> isExist(String carNumber);

    String isExistPhoneNumber(String carNumber);

    List<Long> getMonthData();
    void updatePhoneNumber(String carNumber, String phoneNumber);
    void updateEnteringTime(String carNumber, java.sql.Timestamp timestamp);

    @Transactional
    void resetNewCarExitTime(String carNumber);

    @Transactional
    void updateCurrentCarExitTime(String carNumber, java.sql.Timestamp timestamp);

    Timestamp getEnteringCarTimestamp(String carNumber);

    Boolean isOverTIme(String carNumber);
}
