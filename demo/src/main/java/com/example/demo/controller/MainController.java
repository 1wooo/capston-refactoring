package com.example.demo.controller;

import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final NotificationService notificationService;
    private final IllegalCarServiceInterface illegalCarServiceInterface;
    private final SmsService smsService;

    @GetMapping("mainPage/index")
    public String getMainPage(Model model) {
        List<Long> monthData = illegalCarServiceInterface.getMonthData();
        model.addAttribute("monthData", monthData);
        return "mainPage/index";
    }
    @GetMapping("/")
    public String getMainPage2(Model model) {
        List<Long> monthData = illegalCarServiceInterface.getMonthData();
        model.addAttribute("monthData", monthData);
        return "mainPage/index";
    }

    @GetMapping("mainPage/carlog")
    public String getCarlogPage() {
        return "mainPage/carlog";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "joinPage/join";
    }

    // 알림서비스 //
    @GetMapping("notification/notificationService")
    public String getNotificationServiceWeb() {
        return "notification/notificationService";
    }

    @GetMapping("notification/current")
    public String notificationCurrentInfo(@SessionAttribute(name = NotificationSessionConst.NOTIFY_CAR, required = false)
                                              NotificationCarNumberDTO notificationCarNumberDTO,
                                          Model model) {
        if (notificationCarNumberDTO == null) {
            return "notification/notificationService";
        }
        Optional<NotificationCarNumberDTO> DTOforGetEnteringTime = notificationService.isExist(notificationCarNumberDTO.getCarN());
        model.addAttribute("enteringTime", DTOforGetEnteringTime.get().getTimestamp());

        return "notification/current";
    }
    // 알림서비스 //
    @PostMapping("notification/service/register")
    public String notificationServiceRegister(@ModelAttribute NotificationCarNumberDTO notificationCarNumberDTO,
                                                         Model model, HttpServletRequest request) {

        String inputCarNumber = notificationCarNumberDTO.getCarN();
        String inputPhoneNumber = notificationCarNumberDTO.getPhoneNumber();

        if (notificationService.isExist(inputCarNumber).isEmpty()) {
            model.addAttribute("msg", "해당번호로 입차한 차량이 없습니다.");
            model.addAttribute("url", "notificationService");
            return "notification/messageRedirect";
        }
        if (notificationService.isExistPhoneNumber(inputCarNumber) == null) {
            notificationService.updatePhoneNumber(inputCarNumber, inputPhoneNumber);
            model.addAttribute("msg", "주차 알림 서비스 신규등록이 완료되었습니다.");
            model.addAttribute("url", "current");
        } else {
            model.addAttribute("msg", "등록번호 확인 완료!");
            model.addAttribute("url", "current");
        }

        HttpSession session = request.getSession();
        session.setAttribute(NotificationSessionConst.NOTIFY_CAR, notificationService.isExist(inputCarNumber).get());

        return "notification/messageRedirect";
    }
}

