package com.searchengine.yjpark.service;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Member;
import com.searchengine.yjpark.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ServiceService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    @Autowired
    public ServiceService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 데이터베이스 정보 등록
     * @param
     * @return
     */
    public String registrationDB(DataBaseInfo dataBaseInfo) {

        memberRepository.save(member);
        return member.getId();
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
