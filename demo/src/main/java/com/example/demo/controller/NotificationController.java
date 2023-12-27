package com.example.demo.controller;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.exception.NotFoundCarException;
import com.example.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.text.ParseException;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    @PostMapping("notification/exittimeupdate")
    public ResponseEntity<?> carUpdateExitTime(@RequestBody HashMap<String, Object> map) throws ParseException {
        //차량 출차 시 출차 시간 업데이트.
        try {
            notificationService.updateCurrentCarExitTime(map);
            return ResponseEntity.ok("출차 입력 성공");
        } catch (ParseException e) {
            // ParseException이 발생했을 때 실패 응답 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력 형식이 올바르지 않습니다.");
        } catch (Exception e) {
            // 기타 예외가 발생했을 때 실패 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }
    @PostMapping("notification/service/car-register")
    public ResponseEntity<?> NotificationCarRegister(@RequestBody HashMap<String, Object> map) {
        /**
        * 1. 맵을 넘겨주고 비동기 메소드 동작
        * 2. 알림 서비스 등록차량 or 미등록차량 판별
        * 3. 판별 결과에 따라 새로 등록할지 아니면 기존 등록 차량 정보를 업데이트할지 선택
        */
        try {
            notificationService.notification_alarm(map);
            return ResponseEntity.ok("알림서비스 차량 번호 등록 성공");
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        } catch (NotFoundCarException | ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력 형식이 올바르지 않습니다.");
        }
    }

//    @PostMapping("notification/service/register")
//    public ResponseEntity<?> notificationServiceRegister(@ModelAttribute NotificationCarNumberDTO notificationCarNumberDTO,
//                                              Model model, HttpServletRequest request) {
//
//        String inputCarNumber = notificationCarNumberDTO.getCarN();
//        String inputPhoneNumber = notificationCarNumberDTO.getPhoneNumber();
//
//        if (notificationService.isExist(inputCarNumber).isEmpty()) {
//            model.addAttribute("msg", "해당번호로 입차한 차량이 없습니다.");
//            model.addAttribute("url", "notificationService");
//            return ResponseEntity.status(HttpStatus.FOUND)
//                    .location(URI.create("/notification/messageRedirect"))
//                    .build();
//        }
//        if (notificationService.isExistPhoneNumber(inputCarNumber) == null) {
//            notificationService.updatePhoneNumber(inputCarNumber, inputPhoneNumber);
//            model.addAttribute("msg", "주차 알림 서비스 신규등록이 완료되었습니다.");
//            model.addAttribute("url", "current");
//        } else {
//            model.addAttribute("msg", "등록번호 확인 완료!");
//            model.addAttribute("url", "current");
//        }
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .location(URI.create("/notification/messageRedirect"))
//                .build();
//    }
}
