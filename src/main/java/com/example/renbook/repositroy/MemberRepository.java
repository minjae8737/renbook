package com.example.renbook.repositroy;

import com.example.renbook.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager entityManager;


    public void join(Member member) {
        entityManager.persist(member);
    }

    public Optional<Member> findByMemberId(String memberId) {
        String jpql = "SELECT m FROM Member m where m.memberId = :memberId";

        try {
            Member findMember = entityManager.createQuery(jpql, Member.class).setParameter("memberId", memberId).getSingleResult();
            return Optional.of(findMember);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}
