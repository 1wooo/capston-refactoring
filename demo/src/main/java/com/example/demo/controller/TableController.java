package com.example.demo.controller;

import com.example.demo.DTO.carNumber;
import com.example.demo.service.TableServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TableController {
    private final TableServiceInterface tableServiceInterface;
    @GetMapping("mainPage/tables")
    public String getTablePage(HttpServletRequest request, Model model) {
        List<carNumber> cars = tableServiceInterface.getAll();
        model.addAttribute("illegalCars", cars);
        return "mainPage/tables";
    }
}
