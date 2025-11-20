package com.test.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.jpa.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
