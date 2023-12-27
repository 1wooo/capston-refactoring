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

    }
}
