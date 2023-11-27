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
public class carNumber {
    public carNumber(){
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "carn")
    private String CarN;

    @Column(name = "illegalcode")
    private int illegalCode;

    @Column(name = "violation_time")
    private Timestamp timestamp;

    @Column(name = "fine")
    private int fine;

    public String getDeleteCode() {
        return deleteCode;
    }

    public void setDeleteCode(String deleteCode) {
        this.deleteCode = deleteCode;
    }

    @Column(name = "deleteCode")
    private String deleteCode;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getIllegalCode() {
        return illegalCode;
    }

    public void setIllegalCode(int illegalCode) {
        this.illegalCode = illegalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarN() {
        return CarN;
    }

    public void setCarN(String carN) {
        CarN = carN;
    }
}
