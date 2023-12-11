package com.example.demo.service;

import com.example.demo.DTO.MessageDTO;
import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.carNumber;
import com.example.demo.repo.CarNumberRepo;
import com.example.demo.repo.NotificationCarNumberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationCarNumberRepo notificationCarNumberRepo;
    private final IllegalCarServiceInterface illegalCarServiceInterface;
    private final SmsService smsService;

    private final CarNumberRepo carNumberRepo;

    private carNumber createCarNumberFromMap(HashMap<String, Object> map) throws ParseException {
        carNumber car = new carNumber();
        car.setCarN((String) map.get("carNumber"));
        car.setIllegalCode((int) map.get("illegalCode"));
        car.setFine((int) map.get("fine"));

        // 날짜 처리 메소드 호출
        car.setTimestamp(createTimestampFromMap(map));

        return car;
    }

    private Timestamp createTimestampFromMap(HashMap<String, Object> map) throws ParseException {
        String timeStr = (String) map.get("EnterDate");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = formatter.parse(timeStr);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(Calendar.HOUR, 12);
        return new java.sql.Timestamp(cal.getTime().getTime());
    }
    @Override
    public Boolean isOverTIme(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findBycarN(carNumber);
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
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findBycarN(carNumber);
        NotificationCarNumberDTO car = tmp.get();
        return car.getTimestamp();
    }

    @Override
    public void updateCurrentCarExitTime(HashMap<String, Object> map) throws ParseException {
        carNumber car = createCarNumberFromMap(map);
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findBycarN(car.getCarN());
        if (tmp.isPresent()) {
            NotificationCarNumberDTO carForUpdate = tmp.get();
            carForUpdate.setExitTime(car.getTimestamp());
        }
    }

    @Override
    public void resetNewCarExitTime(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findBycarN(carNumber);
        NotificationCarNumberDTO carForUpdate = tmp.get();
        carForUpdate.setExitTime(null);
        // 등록차량이 다시 들어왔을때 출차시간 리셋
    }

    @Override
    public void updateEnteringTime(String carNumber, Timestamp timestamp) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findBycarN(carNumber);
        NotificationCarNumberDTO carForupdate = tmp.get();
        carForupdate.setTimestamp(timestamp);
    }

    @Override
    public void updatePhoneNumber(String carNumber, String phoneNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findBycarN(carNumber);
        NotificationCarNumberDTO carForupdate = tmp.get();

        carForupdate.setPhoneNumber(phoneNumber);
    }

    @Override
    public String isExistPhoneNumber(String carNumber) {
        Optional<NotificationCarNumberDTO> bytoPhoneNumber = notificationCarNumberRepo.findBycarN(carNumber);
        String phoneNumber = bytoPhoneNumber.get().getPhoneNumber();
        if (bytoPhoneNumber.isPresent()) {
            return phoneNumber;
        } else {
            return null;
        }
    }

    @Override
    public Optional<NotificationCarNumberDTO> isExist(String carNumber) {
        Optional<NotificationCarNumberDTO> findCar = notificationCarNumberRepo.findBycarN(carNumber);
        return findCar; // 있으면 True 없으면 False
    }


    @Override
    public void NotificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO) {
        notificationCarNumberRepo.save(notificationCarNumberDTO);
    }

    @Override
    @Async
    public void Notification_alarm(String carNumber) {
        MessageDTO sendMsg = new MessageDTO();
        int sec = 0;



    }
}
