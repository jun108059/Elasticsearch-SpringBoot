package com.searchengine.yjpark.service;

import com.searchengine.yjpark.domain.Member;
import com.searchengine.yjpark.repository.MemberRepository;
import com.searchengine.yjpark.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * @param member
     * @return
     */
    public Long join(Member member) {
        // 같은 이름의 중복 회원은 X
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증 코드
     * @param member
     */
    private void validateDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByName(member.getName());
        // result 값이 존재하면 로직 동작(Optional로 감싸서 가능)
        result.ifPresent(m -> {
            // 멤버의 값이 존재할 경우(m) throw
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        /**
         memberRepository.findByName(member.getName())
         .isPresent(m -> {
         // 멤버의 값이 존재할 경우(m) throw
         throw new IllegalStateException("이미 존재하는 회원입니다.");
         });
         */
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
