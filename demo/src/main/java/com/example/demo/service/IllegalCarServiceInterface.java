package com.example.demo.service;

import com.example.demo.Entity.CarNumber;
import java.text.ParseException;
import java.util.*;

public interface IllegalCarServiceInterface {
    List<CarNumber> getAll();

    void illegalCarRegister(HashMap<String, Object> map) throws ParseException;

    void illegalCarRemove(Long id);

    List<Long> getMonthData();
}
