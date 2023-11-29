package com.example.demo.service;

import com.example.demo.Entity.Member;
import com.example.demo.repo.CarNumberRepoInterface;
import com.example.demo.repo.MemberRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepo memberRepo;
    private final CarNumberRepoInterface carNumberRepoInterface;

    public MemberServiceImpl(MemberRepo memberRepo, CarNumberRepoInterface carNumberRepoInterface) {
        this.memberRepo = memberRepo;
        this.carNumberRepoInterface = carNumberRepoInterface;
    }

    @Override
    public void register(Member member) throws Exception {
        memberRepo.save(member);
    }

    @Override
    public Optional<Member> login(String MemberId, String MemberPassword) throws Exception {

        Optional<Member> findMember = memberRepo.findByMemberId(MemberId);
        System.out.println(findMember);
//        Member returnMember = findMember.get(); // null인 경우에 대해 예외처리 리팩토링 필요 !!!여기

        return findMember;
    }
}
