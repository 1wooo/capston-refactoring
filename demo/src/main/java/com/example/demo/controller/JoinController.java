package com.example.demo.controller;

import com.example.demo.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@NoArgsConstructor
//@AllArgsConstructor
public class JoinController {

    @GetMapping("/join")
    public String join() {
        return "joinPage/join";
    }
    @PostMapping("/joinProc")
    public String joinProc(Member member) {
        return "redirect:/";
    }
}
