package com.test.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.jpa.entity.Item;

//엔티티 > CRUD 구현
//- 인터페이스명 > 엔티티명+Repository
//- extends JpaRepository<엔티티, >
public interface ItemRepository extends JpaRepository<Item, Long> {
	
}

















