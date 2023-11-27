package com.example.demo.service;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.carNumber;
import com.example.demo.repo.CarNumberRepoInterface;
import com.example.demo.repo.NotificationCarNumberRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

// 위반차량관리와 관련된 모든 서비스 구현
@Service
public class mainServiceImpl implements TableServiceInterface {

    @Autowired
    private CarNumberRepoInterface carNumberRepoInterface;
    @Autowired
    private NotificationCarNumberRepoInterface notificationCarNumberRepoInterface;

    @Override
    public List<carNumber> getAll() {
        return carNumberRepoInterface.findAll();
    }
    @Override
    public List<Long> getMonthData() {
        List<Long> MonthData = carNumberRepoInterface.getData();

        return MonthData;
    } // 대시보드 차트 DB연결할 때 쓰는 클래스
    @Override
    public void illegalCarRegister(carNumber carnumber) {
        carNumberRepoInterface.save(carnumber);
    }
    // 테이블 DB와 연결 시 사용함
    @Override
    @Transactional
    public void illegalCarRemove(Long id) {
        Optional<carNumber> tmp = carNumberRepoInterface.findById(id);
        carNumber requestedCar = tmp.get();

        requestedCar.setDeleteCode("Y");
    } // 테이블 페이지에 삭제버튼과 연결 (불법주정차 차량관련)

    @Override
    public void NotificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO) {
        notificationCarNumberRepoInterface.save(notificationCarNumberDTO);
    } // 입차시 알림서비스 DB에 일단 등록
    @Override
    public Optional<NotificationCarNumberDTO> isExist(String carNumber) {
        Optional<NotificationCarNumberDTO> findCar = notificationCarNumberRepoInterface.findBycarN(carNumber);
        return findCar; // 있으면 True 없으면 False
    } // 현재 차량이 입차했는지?

    @Override
    public String isExistPhoneNumber(String carNumber) {
        Optional<NotificationCarNumberDTO> bytoPhoneNumber = notificationCarNumberRepoInterface.findBycarN(carNumber);
        String phoneNumber = bytoPhoneNumber.get().getPhoneNumber();
        if (bytoPhoneNumber.isPresent()) {
            return phoneNumber;
        } else {
            return null;
        }
    } // 해당 차량번호에 대해 전화번호가 등록이 돼있는지?

    @Override
    @Transactional
    public void updatePhoneNumber(String carNumber, String phoneNumber) {

        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForupdate = tmp.get();

        carForupdate.setPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public void updateEnteringTime(String carNumber, Timestamp timestamp) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForupdate = tmp.get();

        carForupdate.setTimestamp(timestamp);
    }

    @Override
    @Transactional
    public void resetNewCarExitTime(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForUpdate = tmp.get();
        carForUpdate.setExitTime(null);
        // 등록차량이 다시 들어왔을때 출차시간 리셋
    }

    @Override
    @Transactional
    public void updateCurrentCarExitTime(String carNumber, Timestamp timestamp) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForUpdate = tmp.get();
        carForUpdate.setExitTime(timestamp);
    }

    @Override
    public Timestamp getEnteringCarTimestamp(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO car = tmp.get();
        return car.getTimestamp();
    }

    @Override
    public Boolean isOverTIme(String carNumber) {
        Optional<NotificationCarNumberDTO> tmp = notificationCarNumberRepoInterface.findBycarN(carNumber);
        NotificationCarNumberDTO carForUpdate = tmp.get();

        Timestamp enterTime = carForUpdate.getTimestamp();
        Timestamp exitTime = carForUpdate.getExitTime();

        if (exitTime == null) {
            return false;
        }

        Calendar enTime = Calendar.getInstance();
        Calendar exTime = Calendar.getInstance();

        enTime.setTime(enterTime);
        exTime.setTime(exitTime);

        int diffMIN = exTime.get(Calendar.MINUTE) - enTime.get(Calendar.MINUTE);
        int diffHOUR = exTime.get(Calendar.HOUR) - enTime.get(Calendar.HOUR);

        if (diffMIN >= 1 || diffHOUR >= 1) {
            return true;
        }
        else return false;
    }

}
