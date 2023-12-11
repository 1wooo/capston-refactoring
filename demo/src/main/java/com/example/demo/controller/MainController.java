package com.example.demo.controller;

import com.example.demo.Entity.Member;
import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.carNumber;
import com.example.demo.s3.S3Service;
import com.example.demo.service.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private TableServiceInterface tableServiceInterface;
    private SmsService smsService;

    @GetMapping("mainPage/index")
    public String getMainPage() {
        return "mainPage/index";
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
        Optional<NotificationCarNumberDTO> DTOforGetEnteringTime = tableServiceInterface.isExist(notificationCarNumberDTO.getCarN());
        model.addAttribute("enteringTime", DTOforGetEnteringTime.get().getTimestamp());

        return "notification/current";
    }
    // 알림서비스 //
}

