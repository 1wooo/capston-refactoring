package com.example.demo.controller;

import com.example.demo.DTO.carNumber;
import com.example.demo.service.TableServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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
    public ResponseEntity<Void> illegalCarNumberRemove(@RequestParam List<String> illegalCarNumberTableID) {
        for (String id : illegalCarNumberTableID) {
            Long tableId = Long.valueOf(id);
            tableServiceInterface.illegalCarRemove(tableId);
        }
        // ResponseEntity를 사용하여 HttpStatus와 함께 응답을 반환
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/mainPage/tables")).build();
    }

    @PostMapping("table/illegal_register") // 모델에서 넘어오는 위반차량정보 receive
    public void illegalCarNumberRegister(@RequestBody HashMap<String, Object> map) throws ParseException {
        tableServiceInterface.illegalCarRegister(map);
    }
}
