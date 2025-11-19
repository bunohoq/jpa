package com.test.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.jpa.entity.Item;
import com.test.jpa.model.ItemDTO;

//엔티티 > CRUD 구현
//- 인터페이스명 > 엔티티명+Repository
//- extends JpaRepository<엔티티, >
public interface ItemRepository extends JpaRepository<Item, Long> {

	Optional<Item> findByName(String string);

	Optional<Item> findByNameIs(String string);

	Optional<Item> findByNameEquals(String string);

	Optional<Item> findByColor(String string);
	
	List<Item> findByColorIgnoreCase(String string);

	Optional<Item> findFirstByColor(String string);

	Optional<Item> findFirstByPrice(int i);

	List<Item> findFirst3ByColor(String string);

	List<Item> findFirst5ByColor(String string);

	Optional<Item> findByNameAndColor(String string, String string2);

	List<Item> findByNameOrColor(String string, String string2);

}

















