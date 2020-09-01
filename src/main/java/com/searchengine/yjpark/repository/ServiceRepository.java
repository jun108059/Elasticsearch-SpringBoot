package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.Member;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
