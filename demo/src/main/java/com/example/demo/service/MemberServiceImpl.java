package com.example.demo.service;

import com.example.demo.Entity.Member;
import com.example.demo.repo.CarNumberRepoInterface;
import com.example.demo.repo.SpringDataJpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberServiceInterface {

    @Autowired
    private SpringDataJpaMemberRepository springDataJpaMemberRepository;
    @Autowired
    private CarNumberRepoInterface carNumberRepoInterface;

    @Override
    public void register(Member member) throws Exception {
        springDataJpaMemberRepository.save(member);
    }

    @Override
    public Optional<Member> login(String MemberId, String MemberPassword) throws Exception {

        Optional<Member> findMember = springDataJpaMemberRepository.findByMemberId(MemberId);
        System.out.println(findMember);
//        Member returnMember = findMember.get(); // null인 경우에 대해 예외처리 리팩토링 필요 !!!여기

        return findMember;
    }
}
