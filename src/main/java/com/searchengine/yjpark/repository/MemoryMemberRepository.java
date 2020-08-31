package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository{

    // 공유 자원에 동시성 문제가 있어 ConcurrentHashMap을 권장하지만 간단하게 HashMap 사용
    private static Map<Long, Member> store = new HashMap<>();
    // 여기도 마찬가지로 실무에서 atomiclong을 사용하지만 간단하게 long 사용
    private static long sequence = 0L;

    // member 저장하기
    @Override
    public Member save(Member member) {
        // store 하기 전에 id setting
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    // member 찾기
    @Override
    public Optional<Member> findById(Long id) {
        // null이 반환될 가능성이 있다면 Optional로 감싸줌
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }
}
