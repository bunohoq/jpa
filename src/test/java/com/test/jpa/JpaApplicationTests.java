package com.test.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.jpa.model.ItemDTO;

@SpringBootTest
class JpaApplicationTests {

	@Test
	void contextLoads() {
		
		ItemDTO dto1 = new ItemDTO();
		
		dto1.setName("마우스");
		dto1.setPrice(2000);
		
		ItemDTO dto2 = new ItemDTO(1L, "마우스", 2000, "blue", 10, "마우스입니다.");
		
		//builder 디자인 패턴
		ItemDTO dto3 = ItemDTO.builder()
						.name("마우스")
						.price(2000)
						.color("blue")
						.build();
		
	}

}








