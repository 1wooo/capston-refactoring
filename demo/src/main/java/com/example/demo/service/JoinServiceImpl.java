package com.example.demo.service;

import com.example.demo.Entity.Member;
import com.example.demo.Entity.Role;
import com.example.demo.repo.MemberRepo;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinServiceImpl implements JoinService{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepo memberRepo;

    public JoinServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepo memberRepo) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepo = memberRepo;
    }

    @Override
    public boolean join(Member member) {
        if (memberRepo.existsByMemberId(member.getMemberId())) {
            return false;
        }

        String rawPassword = member.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.setPassword(encPassword);
        member.setRole(Role.valueOf("ROLE_ADMIN"));
        memberRepo.save(member);

        return true;
    }
}
