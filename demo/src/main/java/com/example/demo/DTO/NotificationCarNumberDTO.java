package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
public class NotificationCarNumberDTO {
    public NotificationCarNumberDTO(){
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "carnumber")
    private String carN;

    @Column(name = "entering_time")
    private Timestamp timestamp;

    @Column(name = "exit_time")
    private Timestamp exitTime;
    @Column(name = "phoneNumber")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarN() {
        return carN;
    }

    public void setCarN(String carN) {
        this.carN = carN;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
