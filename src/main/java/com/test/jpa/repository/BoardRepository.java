package com.test.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.jpa.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
