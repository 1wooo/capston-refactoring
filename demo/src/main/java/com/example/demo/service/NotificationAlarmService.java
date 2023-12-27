package com.example.demo.service;

import com.example.demo.exception.NotFoundCarException;

import java.text.ParseException;
import java.util.HashMap;

public interface NotificationAlarmService {
    void notification_alarm(HashMap<String, Object> map) throws NotFoundCarException, InterruptedException, ParseException;

}
