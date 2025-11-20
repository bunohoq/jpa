package com.test.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.jpa.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

}
