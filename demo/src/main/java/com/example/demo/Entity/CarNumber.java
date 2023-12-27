package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class CarNumber {
    public CarNumber(){
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "carn")
    private String carN;

    @Column(name = "illegalcode")
    private int illegalCode;

    @Column(name = "violation_time")
    private Timestamp timestamp;

    @Column(name = "fine")
    private int fine;

    @Column(name = "deleteCode")
    private String deleteCode;

}
