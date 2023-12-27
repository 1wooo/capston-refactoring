package com.example.demo.controller;

import com.example.demo.DTO.CarNumber;
import com.example.demo.service.IllegalCarServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IllegalCarController {
    private final IllegalCarServiceInterface illegalCarServiceInterface;
    @GetMapping("mainPage/tables")
    public String getTablePage(HttpServletRequest request, Model model) {
        List<CarNumber> cars = illegalCarServiceInterface.getAll();
        if (!cars.isEmpty()) {
            model.addAttribute("illegalCars", cars);
        }
        return "mainPage/tables";
    }
}
