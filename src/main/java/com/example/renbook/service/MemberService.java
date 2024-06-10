package com.example.renbook.service;

import com.example.renbook.domain.LoginDto;
import com.example.renbook.domain.Member;
import com.example.renbook.domain.MemberDto;
import com.example.renbook.repositroy.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(MemberDto memberDto) {
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setPassword(memberDto.getPassword());
        member.setJoinDate(LocalDateTime.now());

        memberRepository.join(member);
    }

    public Member login(LoginDto loginDto) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(loginDto.getMemberId());

        if (optionalMember.isPresent()) {
            Member loginedMember = optionalMember.get();
            //로그인 성공 여부 판별
            if (canLogin(loginDto, loginedMember)) {
                return loginedMember;
            } else {
                return null;
            }
        }

        return null;
    }

    private static boolean canLogin(LoginDto loginDto, Member loginedMember) {
        return loginedMember.getMemberId().equals(loginDto.getMemberId()) && loginedMember.getPassword().equals(loginDto.getPassword());
    }


}
