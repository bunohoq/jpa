package com.test.jpa.repository;

import static com.test.jpa.entity.QUser.user;
import static com.test.jpa.entity.QUserInfo.userInfo;

import java.util.List;

import static com.test.jpa.entity.QBoard.board;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.jpa.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueryDSLRepository {

	private final JPAQueryFactory factory;

	public User m39() {
		
		/*
			조인
			- join()		: inner join
			- innerJoin()	: inner join
			- leftJoin()	: left outer join
			- rightJoin()	: right outer join
		*/
		
		return factory
				.selectFrom(user)
				.join(user.userInfo, userInfo)
				.where(user.id.eq("hong"))
				.fetchOne();
	}

	public List<User> m40() {
		
		return factory
				.selectFrom(user)
				//.join(user.boards, board)
				.leftJoin(user.boards, board)
				.join(user.userInfo, userInfo)
				.fetch();
	}
	
	
	
}