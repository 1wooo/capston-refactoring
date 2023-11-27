package com.example.demo.service;
import com.example.demo.Entity.Member;
import java.util.Optional;

public interface MemberServiceInterface {

    public void register(Member member) throws Exception;

    public Optional<Member> login(String MemberId, String MemberPassword) throws Exception;
}
