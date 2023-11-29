package com.example.demo.controller;

import com.example.demo.Entity.Member;
import com.example.demo.service.MemberService;
import com.example.demo.service.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "loginPage/HomeLogin";
    }

}
