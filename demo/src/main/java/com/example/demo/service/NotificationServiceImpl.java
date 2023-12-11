package com.example.demo.service;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.carNumber;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Optional;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public Boolean isOverTIme(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForUpdate = tmp.get();

        Timestamp enterTime = carForUpdate.getTimestamp();
        Timestamp exitTime = carForUpdate.getExitTime();

        if (exitTime == null) {
            return false;
        }

        Calendar enTime = Calendar.getInstance();
        Calendar exTime = Calendar.getInstance();

        enTime.setTime(enterTime);
        exTime.setTime(exitTime);

        int diffMIN = exTime.get(Calendar.MINUTE) - enTime.get(Calendar.MINUTE);
        int diffHOUR = exTime.get(Calendar.HOUR) - enTime.get(Calendar.HOUR);

        if (diffMIN >= 1 || diffHOUR >= 1) {
            return true;
        }
        else return false;
    }

    @Override
    public Timestamp getEnteringCarTimestamp(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO car = tmp.get();
        return car.getTimestamp();
    }

    @Override
    public void updateCurrentCarExitTime(HashMap<String, Object> map) throws ParseException {
        carNumber car = createCarNumberFromMap(map);
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(car.getCarN());
        if (tmp.isPresent()) {
            NotificationCarNumberDTO carForUpdate = tmp.get();
            carForUpdate.setExitTime(car.getTimestamp());
        }
    }

    @Override
    public void resetNewCarExitTime(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForUpdate = tmp.get();
        carForUpdate.setExitTime(null);
        // 등록차량이 다시 들어왔을때 출차시간 리셋
    }

    @Override
    public void updateEnteringTime(String carNumber, Timestamp timestamp) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForupdate = tmp.get();
        carForupdate.setTimestamp(timestamp);
    }

    @Override
    public void updatePhoneNumber(String carNumber, String phoneNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForupdate = tmp.get();

        carForupdate.setPhoneNumber(phoneNumber);
    }

    @Override
    public String isExistPhoneNumber(String carNumber) {
        Optional<NotificationCarNumberDTO> bytoPhoneNumber = notificationCarNumberRepoInterface.findBycarN(carNumber);
        String phoneNumber = bytoPhoneNumber.get().getPhoneNumber();
        if (bytoPhoneNumber.isPresent()) {
            return phoneNumber;
        } else {
            return null;
        }
    }

    @Override
    public Optional<NotificationCarNumberDTO> isExist(String carNumber) {
        Optional<NotificationCarNumberDTO> findCar = notificationCarNumberRepoInterface.findBycarN(carNumber);
        return findCar; // 있으면 True 없으면 False
    }


    @Override
    public void NotificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO) {
        notificationCarNumberRepoInterface.save(notificationCarNumberDTO);
    }
}
