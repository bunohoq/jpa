package com.test.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.jpa.entity.Item;
import com.test.jpa.model.ItemDTO;

//엔티티 > CRUD 구현
//- 인터페이스명 > 엔티티명+Repository
//- extends JpaRepository<엔티티, >
public interface ItemRepository extends JpaRepository<Item, Long> {

	Optional<Item> findByName(String name);

	Optional<Item> findByNameIs(String string);

	Optional<Item> findByNameEquals(String string);

	//List<Item> findByColor(String string);
	Optional<Item> findByColor(String string);

	List<Item> findByColorIgnoreCase(String string);

	Optional<Item> findFirstByColor(String string);

	Optional<Item> findFirstByPrice(int i);

	List<Item> findFirst3ByColor(String string);

	List<Item> findTop5ByColor(String string);

	Optional<Item> findByNameAndColor(String string, String string2);

	List<Item> findByNameOrColor(String string, String string2);

	List<Item> findByPriceGreaterThan(int i);

	List<Item> findByPriceGreaterThanEqual(int i);

	List<Item> findByPriceLessThanEqual(int i);

	List<Item> findByColorAndPriceGreaterThan(String string, int i);

	List<Item> findByPriceBetween(int i, int j);

	List<Item> findByQtyIsNull();

	List<Item> findByDescriptionIsNull();

	List<Item> QtyIsNull();

	List<Item> findByDescriptionIsNotNullAndColorAndPriceGreaterThanEqual(String string, int i);

	List<Item> findByColorIn(List<String> colors);

	//List<Item> findByColorNoIn(List<String> colors);

	List<Item> findByColorNotIn(List<String> colors);

	List<Item> findByQtyIsNullAndDescriptionIsNull();

	List<Item> findByNameStartsWith(String string);

	List<Item> findByNameEndsWith(String string);

	List<Item> findByDescriptionContains(String string);

	List<Item> findByDescriptionNotContains(String string);

	List<Item> findByDescriptionLike(String string);

	List<Item> findAllByOrderByNameAsc();

	List<Item> findAllByOrderByNameDesc();

	List<Item> findByColorOrderByPriceDesc(String string);

	List<Item> findAllByOrderByColorAscPriceDesc();

	List<Item> findByPriceGreaterThanOrderByPriceAsc(Integer price);

	List<Item> findByPriceGreaterThanOrderByPriceDesc(Integer price);

	List<Item> findAllByOrderByPriceAsc();

	List<Item> findByPriceGreaterThan(Sort by, Integer price);

	//JPQL
	//- select * from tblItem
	//@Query("select i from Item as i")
	@Query(value = "select * from tblItem", nativeQuery = true)
	List<Item> m25();

	@Query("select i.name from Item as i")
	List<String> m26();

//	@Query("select i from Item as i where i.color = ?1")
//	List<Item> m27(String color);
	
	@Query("select i from Item as i where i.color = :color")
	List<Item> m27(@Param(value="color") String color);

	@Query("select i from Item as i where i.color = :#{#dto.color} and i.price >= :#{#dto.price}")
	List<Item> m28(@Param(value="dto") ItemDTO dto);

}

















