package com.searchengine.yjpark;

import com.searchengine.yjpark.repository.*;
import com.searchengine.yjpark.service.MemberService;
import com.searchengine.yjpark.service.ServiceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
         // 구현체 생성
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
    @Bean
    public ServiceService serviceService() { return new ServiceService(serviceRepo)}

    @Bean
    public ServiceRepository serviceRepository() {
        // 구현체 생성
        return new JdbcTemplateServiceRepository(dataSource);
    }
}
