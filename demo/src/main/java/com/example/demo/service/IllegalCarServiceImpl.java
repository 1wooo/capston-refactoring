package com.example.demo.service;

import com.example.demo.DTO.carNumber;
import com.example.demo.repo.CarNumberRepoInterface;
import com.example.demo.repo.NotificationCarNumberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// 위반차량관리와 관련된 모든 서비스 구현
@Service
@Transactional
@RequiredArgsConstructor
public class IllegalCarServiceImpl implements IllegalCarServiceInterface {

    private CarNumberRepoInterface carNumberRepoInterface;
    private NotificationCarNumberRepo notificationCarNumberRepo;

    @Override
    public List<carNumber> getAll() {
        return carNumberRepoInterface.findAll();
    }
    @Override
    public List<Long> getMonthData() {
        return carNumberRepoInterface.getData();
    }
    @Override
    public void illegalCarRegister(HashMap<String, Object> map) throws ParseException{
        carNumber car = createCarNumberFromMap(map);
        carNumberRepoInterface.save(car);
    }

    private carNumber createCarNumberFromMap(HashMap<String, Object> map) throws ParseException {
        carNumber car = new carNumber();
        car.setCarN((String) map.get("carNumber"));
        car.setIllegalCode((int) map.get("illegalCode"));
        car.setFine((int) map.get("fine"));

        // 날짜 처리 메소드 호출
        car.setTimestamp(createTimestampFromMap(map));

        return car;
    }

    private Timestamp createTimestampFromMap(HashMap<String, Object> map) throws ParseException {
        String timeStr = (String) map.get("EnterDate");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = formatter.parse(timeStr);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(Calendar.HOUR, 12);
        return new java.sql.Timestamp(cal.getTime().getTime());
    }
    // 테이블 DB와 연결 시 사용함
    @Override
    public void illegalCarRemove(Long id) {
        Optional<carNumber> tmp = carNumberRepoInterface.findById(id);
        carNumber requestedCar = tmp.get();

        requestedCar.setDeleteCode("Y");
    } // 테이블 페이지에 삭제버튼과 연결 (불법주정차 차량관련)
}
