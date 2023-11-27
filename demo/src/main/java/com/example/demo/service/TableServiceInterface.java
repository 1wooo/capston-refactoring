package com.example.demo.service;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.carNumber;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface TableServiceInterface {
    List<carNumber> getAll();
    public void illegalCarRegister(carNumber carnumber);

    public void illegalCarRemove(Long id);

    public void NotificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO);

    Optional<NotificationCarNumberDTO> isExist(String carNumber);

    String isExistPhoneNumber(String carNumber);

    List<Long> getMonthData();
    public void updatePhoneNumber(String carNumber, String phoneNumber);
    public void updateEnteringTime(String carNumber, java.sql.Timestamp timestamp);

    @Transactional
    void resetNewCarExitTime(String carNumber);

    @Transactional
    void updateCurrentCarExitTime(String carNumber, java.sql.Timestamp timestamp);

    Timestamp getEnteringCarTimestamp(String carNumber);

    Boolean isOverTIme(String carNumber);
}
