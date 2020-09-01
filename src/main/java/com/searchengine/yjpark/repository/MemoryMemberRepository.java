package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        // Id를 store 에서 get으로 꺼내면 됨
        // null이 반환될 가능성이 있다면 Optional로 감싸줌
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 람다 활용 - loop로 돌리면서 member의 name이 @param과 같은지
        // 찾으면 바로 반환시키고
        // 없으면 Optional에 포함시켜서 반환
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // 반환을 List로 하면 편하게 사용할 수 있음
        // store에 있는 value로 ArrayList로 생성
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
