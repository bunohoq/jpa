package com.test.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QueryDSLConfig {
	
	private final EntityManager em;
	
	//<bean class="com.querydsl.jpa.impl.JPAQueryFactory">
	//	<constructor-arg ref="em" />
	//</bean>

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		
		return new JPAQueryFactory(em);
	}
}
