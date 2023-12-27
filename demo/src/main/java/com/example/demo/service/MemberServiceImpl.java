package com.example.demo.service;

import com.example.demo.Entity.Member;
import com.example.demo.repo.CarNumberRepo;
import com.example.demo.repo.MemberRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepo memberRepo;

    public MemberServiceImpl(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public void register(Member member) throws Exception {
        memberRepo.save(member);
    }

}
