package com.example.demo.service;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.exception.NotFoundCarException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationAlarmServiceImpl implements NotificationAlarmService {
    private final NotificationService notificationService;
    @Override
    @Async
    public void notification_alarm(HashMap<String, Object> map) throws NotFoundCarException, InterruptedException, ParseException {

        NotificationCarNumberDTO car = notificationService.createCarNumberFromMap(map);
        Optional<NotificationCarNumberDTO> notificationCarNumberDTO = notificationService.isExist(car.getCarN());

        if (notificationCarNumberDTO.isEmpty()) {
            notificationService.notificationCarRegister(car);
        } else {
            notificationService.updateEnteringTime(car.getCarN(), car.getTimestamp());
            notificationService.resetNewCarExitTime(car.getCarN());
        }

        String phoneNumber = notificationService.isExistPhoneNumber(car.getCarN());

        for (int i = 10; i > 0; i--) {
            Thread.sleep(1000);
        } // 30초 대기

        if (notificationService.shouldTerminate(car.getCarN(), phoneNumber)) {
            return;
        }
        notificationService.sendMessage("주차시간 30분 소요되었습니다.", phoneNumber);


        for (int i = 10; i > 0; i--) {
            Thread.sleep(1000);
        } // 30초 대기

        if (notificationService.shouldTerminate(car.getCarN(), phoneNumber)) {
            return;
        }
        notificationService.sendMessage("주차시간 60분 소요되었습니다.", phoneNumber);

        for (int i = 10; i > 0; i--) {
            Thread.sleep(1000);
        } // 30초 대기

        if (notificationService.shouldTerminate(car.getCarN(), phoneNumber)) {
            return;
        }
        notificationService.sendMessage("주차시간 90분 소요되었습니다.", phoneNumber);

        for (int i = 10; i > 0; i--) {
            Thread.sleep(1000);
        } // 30초 대기
        if (notificationService.shouldTerminate(car.getCarN(), phoneNumber)) {
            return;
        }
        notificationService.sendMessage("법적 허용 주차시간 초과되었습니다. 출차 부닥드립니다.", phoneNumber);
    }
}
