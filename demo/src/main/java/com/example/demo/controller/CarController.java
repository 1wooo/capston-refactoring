package com.example.demo.controller;

import com.example.demo.DTO.carNumber;
import com.example.demo.service.TableServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final TableServiceInterface tableServiceInterface;
    @PostMapping("mainPage/tables/carRemove")
    public String illegalCarNumberRemove(@RequestParam List<String> illegalCarNumberTableID) {

        for (int i = 0; i < illegalCarNumberTableID.size(); i++) {
            Long id = Long.valueOf(illegalCarNumberTableID.get(i));
            tableServiceInterface.illegalCarRemove(id);
        }
        return "redirect:/mainPage/tables";
    }

    @PostMapping("loginPage/testApi") // 모델에서 넘어오는 위반차량정보 receive
    public void ApiTest(@RequestBody HashMap<String, Object> map) throws ParseException {

        carNumber car = new carNumber();
        car.setCarN((String) map.get("carNumber"));
        car.setIllegalCode((int) map.get("illegalCode"));
        car.setFine((int) map.get("fine"));
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
        System.out.println("New timestamp: "+ newTime);

        car.setTimestamp(newTime);

//        System.out.println(map);
        tableServiceInterface.illegalCarRegister(car);
    }
}
