package com.example.demo.service;

import com.example.demo.Entity.Member;
import com.example.demo.Entity.MemberDetails;
import com.example.demo.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepo memberRepo;

    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> member = memberRepo.findByMemberId(loginId);
        if (member.isPresent()) {
            return new MemberDetails(member.get());
        }else {
            throw new UsernameNotFoundException("아이디 없음");
        }
    }
}
