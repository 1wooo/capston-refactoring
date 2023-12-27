package com.example.demo.service;

import com.example.demo.DTO.UserRegistrationDto;
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
    public boolean join(UserRegistrationDto userRegistrationDto) {
        if (memberRepo.existsByMemberId(userRegistrationDto.getUsername())) {
            return false;
        }

        String rawPassword = userRegistrationDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        Member newMember = new Member();
        newMember.setMemberId(userRegistrationDto.getUsername());
        newMember.setPassword(encPassword);
        newMember.setRole(Role.valueOf("ROLE_ADMIN"));
        memberRepo.save(newMember);

        return true;
    }
}
