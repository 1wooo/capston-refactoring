package com.example.demo.controller;

import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private IllegalCarServiceInterface illegalCarServiceInterface;
    private SmsService smsService;

    @GetMapping("mainPage/index")
    public String getMainPage() {
        return "mainPage/index";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "joinPage/join";
    }

    // 알림서비스 //
    @GetMapping("notification/notificationService")
    public String getNotificationServiceWeb(@SessionAttribute(name = NotificationSessionConst.NOTIFY_CAR, required = false)
                                                NotificationCarNumberDTO notificationCarNumberDTO) {
        if (notificationCarNumberDTO == null) {
            return "notification/notificationService";
        }

        return "notification/notificationService";
    }

    @GetMapping("notification/current")
    public String notificationCurrentInfo(@SessionAttribute(name = NotificationSessionConst.NOTIFY_CAR, required = false)
                                              NotificationCarNumberDTO notificationCarNumberDTO,
                                          Model model) {
        if (notificationCarNumberDTO == null) {
            return "notification/notificationService";
        }
        Optional<NotificationCarNumberDTO> DTOforGetEnteringTime = illegalCarServiceInterface.isExist(notificationCarNumberDTO.getCarN());
        model.addAttribute("enteringTime", DTOforGetEnteringTime.get().getTimestamp());

        return "notification/current";
    }
    // 알림서비스 //
}

