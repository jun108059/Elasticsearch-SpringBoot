package com.searchengine.yjpark.service;

import com.searchengine.yjpark.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.IllegalBlockSizeException;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test
    void 회원가입() {
        // given - 이런 상황에
        Member member = new Member();
        member.setName("Youngjun");

        // when - 실행됐을 때
        Long saveId = memberService.join(member);

        // then - 결과가 이렇게 되어야 된다
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("youngjun");

        Member member2 = new Member();
        member2.setName("youngjun");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
        }

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}