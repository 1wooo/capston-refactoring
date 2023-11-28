package com.example.demo.controller;

import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Entity.Member;
import com.example.demo.service.JoinService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "joinPage/join";
    }
    @PostMapping("/joinProc")
    public String joinProc(UserRegistrationDto userRegistrationDto) {

        boolean isJoinPossible = joinService.join(userRegistrationDto);
        if (isJoinPossible) {
            // 회원가입 완료 or 실패
        }

        return "redirect:/";
    }
}
