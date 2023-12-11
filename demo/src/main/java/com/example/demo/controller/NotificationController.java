package com.example.demo.controller;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.service.NotificationSessionConst;
import com.example.demo.service.Notification_Thread;
import com.example.demo.service.IllegalCarServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final IllegalCarServiceInterface illegalCarServiceInterface;
    @PostMapping("notification/exittimeupdate")
    public ResponseEntity<?> carUpdateExitTime(@RequestBody HashMap<String, Object> map) throws ParseException {
        //차량 출차 시 출차 시간 업데이트.
        try {
            illegalCarServiceInterface.updateCurrentCarExitTime(map);
            return ResponseEntity.ok("출차 입력 성공");
        } catch (ParseException e) {
            // ParseException이 발생했을 때 실패 응답 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력 형식이 올바르지 않습니다.");
        } catch (Exception e) {
            // 기타 예외가 발생했을 때 실패 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }
    @PostMapping("notification/notificationRegister")
    public void NotificationCarRegister(@RequestBody HashMap<String, Object> map) throws ParseException {
        NotificationCarNumberDTO notificationCarNumberDTO = new NotificationCarNumberDTO();
        notificationCarNumberDTO.setCarN((String) map.get("carNumber"));
        //알림서비스 스레드 동작
        Notification_Thread notification_thread = new Notification_Thread(smsService, illegalCarServiceInterface, (String) map.get("carNumber"));
        notification_thread.start();
        //알림서비스 스레드 동작

        /**
         * 1. 맵을 넘겨주고 비동기 메소드 동작
         * 2. 알림 서비스 등록차량 or 미등록차량 판별
         * 3. 판별 결과에 따라 새로 등록할지 아니면 기존 등록 차량 정보를 업데이트할지 선택
         */

        // 날짜처리코드
        String timeStr = (String) map.get("EnterDate");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = formatter.parse(timeStr);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        // 날짜처리코드
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.HOUR, 12);
        java.sql.Timestamp newTime = new java.sql.Timestamp(cal.getTime().getTime());

        notificationCarNumberDTO.setTimestamp(newTime);

        if (!illegalCarServiceInterface.isExist((String) map.get("carNumber")).isPresent()) {
            // 알림 서비스 등록 안된 차량이면 일단 db에 전화번호 없이 신규등록
            illegalCarServiceInterface.NotificationCarRegister(notificationCarNumberDTO);
        } else {
            // 알림 서비스 등록 경력 있는 차량이면 (or db에 이미 존재하는 차량번호) 입차시간 업데이트 해주고 출차시간 리셋
            illegalCarServiceInterface.updateEnteringTime((String)map.get("carNumber"), timestamp);
            illegalCarServiceInterface.resetNewCarExitTime((String)map.get("carNumber"));
        }
    }
    @PostMapping("notification/notificationService")
    public String notificationServiceRegister(@ModelAttribute NotificationCarNumberDTO notificationCarNumberDTO,
                                              Model model, HttpServletRequest request) {

        String inputCarNumber = notificationCarNumberDTO.getCarN();
        String inputPhoneNumber = notificationCarNumberDTO.getPhoneNumber();

        if (!illegalCarServiceInterface.isExist(inputCarNumber).isPresent()) {
            model.addAttribute("msg", "해당번호로 입차한 차량이 없습니다.");
            model.addAttribute("url", "notificationService");
            return "notification/messageRedirect";
        }
        if (illegalCarServiceInterface.isExistPhoneNumber(inputCarNumber) == null) {
            illegalCarServiceInterface.updatePhoneNumber(inputCarNumber, inputPhoneNumber);
            model.addAttribute("msg", "주차 알림 서비스 신규등록이 완료되었습니다.");
            model.addAttribute("url", "current");
        } else {
            model.addAttribute("msg", "등록번호 확인 완료!");
            model.addAttribute("url", "current");
        }

        HttpSession session = request.getSession();
        session.setAttribute(NotificationSessionConst.NOTIFY_CAR, illegalCarServiceInterface.isExist(inputCarNumber).get());

        return "notification/messageRedirect";
    }
}
