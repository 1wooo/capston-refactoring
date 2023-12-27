package com.example.demo.service;

import com.example.demo.DTO.MessageDTO;
import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.exception.NotFoundCarException;
import com.example.demo.repo.CarNumberRepo;
import com.example.demo.repo.NotificationCarNumberRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationCarNumberRepo notificationCarNumberRepo;
    private final SmsService smsService;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Future<?> taskFuture;


    public NotificationCarNumberDTO createCarNumberFromMap(HashMap<String, Object> map) throws ParseException {
        NotificationCarNumberDTO car = new NotificationCarNumberDTO();
        car.setCarN((String) map.get("carNumber"));
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
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findByCarN(carNumber);
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
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findByCarN(carNumber);
        NotificationCarNumberDTO car = tmp.get();
        return car.getTimestamp();
    }

    @Override
    public void updateCurrentCarExitTime(HashMap<String, Object> map) throws ParseException, NotFoundCarException {
        NotificationCarNumberDTO car = createCarNumberFromMap(map);
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findByCarN(car.getCarN());
        if (tmp.isPresent()) {
            NotificationCarNumberDTO carForUpdate = tmp.get();
            carForUpdate.setExitTime(car.getTimestamp());
        } else throw new NotFoundCarException("출차 차량 확인 불가");
    }

    @Override
    public void resetNewCarExitTime(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findByCarN(carNumber);
        NotificationCarNumberDTO carForUpdate = tmp.get();
        carForUpdate.setExitTime(null);
        // 등록차량이 다시 들어왔을때 출차시간 리셋
    }

    @Override
    public void updateEnteringTime(String carNumber, Timestamp timestamp) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findByCarN(carNumber);
        NotificationCarNumberDTO carForupdate = tmp.get();
        carForupdate.setTimestamp(timestamp);
    }

    @Override
    public void updatePhoneNumber(String carNumber, String phoneNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepo.findByCarN(carNumber);
        NotificationCarNumberDTO carForupdate = tmp.get();

        carForupdate.setPhoneNumber(phoneNumber);
    }

    @Override
    public String isExistPhoneNumber(String carNumber) {
        Optional<NotificationCarNumberDTO> bytoPhoneNumber = notificationCarNumberRepo.findByCarN(carNumber);
        return bytoPhoneNumber.map(NotificationCarNumberDTO::getPhoneNumber).orElse(null);
    }

    @Override
    public Optional<NotificationCarNumberDTO> isExist(String carNumber) {
        Optional<NotificationCarNumberDTO> findCar = notificationCarNumberRepo.findByCarN(carNumber);
        return findCar; // 있으면 True 없으면 False
    }


    @Override
    public void notificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO) {
        notificationCarNumberRepo.save(notificationCarNumberDTO);
    }

    @Override
    @Async
    public void notification_alarm(HashMap<String, Object> map) throws InterruptedException, ParseException, NotFoundCarException {
        NotificationCarNumberDTO car = createCarNumberFromMap(map);
        Optional<NotificationCarNumberDTO> notificationCarNumberDTO = isExist(car.getCarN());

        if (notificationCarNumberDTO.isEmpty()) {
            notificationCarRegister(car);
        } else {
            updateEnteringTime(car.getCarN(), car.getTimestamp());
            resetNewCarExitTime(car.getCarN());
        }

        String phoneNumber = isExistPhoneNumber(car.getCarN());

        for (int i = 10; i > 0; i--) {
            Thread.sleep(1000);
        } // 30초 대기

        if (shouldTerminate(car.getCarN(), phoneNumber)) {
            return;
        }
        sendMessage("주차시간 30분 소요되었습니다.", phoneNumber);


        for (int i = 10; i > 0; i--) {
            Thread.sleep(1000);
        } // 30초 대기

        if (shouldTerminate(car.getCarN(), phoneNumber)) {
            return;
        }
        sendMessage("주차시간 60분 소요되었습니다.", phoneNumber);

        for (int i = 10; i > 0; i--) {
            Thread.sleep(1000);
        } // 30초 대기

        if (shouldTerminate(car.getCarN(), phoneNumber)) {
            return;
        }
        sendMessage("주차시간 90분 소요되었습니다.", phoneNumber);

        for (int i = 10; i > 0; i--) {
            Thread.sleep(1000);
        } // 30초 대기
        if (shouldTerminate(car.getCarN(), phoneNumber)) {
            return;
        }
        sendMessage("법적 허용 주차시간 초과되었습니다. 출차 부닥드립니다.", phoneNumber);
    }

    public void sendMessage(String msg, String phoneNumber) {
        MessageDTO sendMsg = new MessageDTO();

        if (phoneNumber != null) {
            sendMsg.setContent(msg);
            sendMsg.setTo(phoneNumber);
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
            System.out.println(msg);
        }
    }

    public boolean shouldTerminate(String carNumber, String phoneNumber) {
        Optional<NotificationCarNumberDTO> findCar = notificationCarNumberRepo.findByCarN(carNumber);
        if (findCar.get().getExitTime() != null) {
            if (isOverTIme(carNumber)) sendMessage("법적 허용 충전시간 초과", phoneNumber);
            return true;
        }
        return false;
    }
}
