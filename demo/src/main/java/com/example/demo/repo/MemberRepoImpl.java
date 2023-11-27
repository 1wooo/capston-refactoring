package com.example.demo.repo;

import com.example.demo.Entity.Member;

import javax.persistence.EntityManager;
import java.util.Optional;

public class MemberRepoImpl implements MemberRepo {

    private final EntityManager em;

    public MemberRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Member> findByMemberId(String memberid) {
        Optional<Member> findMember = em.createQuery(
                        "FROM Member m WHERE m.memberId LIKE :memberid",Member.class)
                .setParameter("memberid", memberid)
                .getResultList()
                .stream().findAny();
        return findMember;
    }
}
