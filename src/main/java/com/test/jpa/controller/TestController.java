package com.test.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.jpa.entity.Item;
import com.test.jpa.model.ItemDTO;
import com.test.jpa.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
	
	private final ItemRepository itemRepository;
	
	//tblItem > CRUD
	
	@GetMapping("/m1")
	public String m1(Model model) {
		
		return "m1";
	}
	
	/*
	
		JPA 쿼리 실행 방식
		
		1. Query Method
			- 30~40%
			- 단순 업무
		
		2. JPQL, Java Persistence Query Language
			- 10~20%
			- 1번 + 2번 > 불가능한 경우
		
		3. Query DSL
			- 30~40%
			- 복잡한 업무
	
	*/
	
	@PostMapping("/m1ok")
	public String m1ok(Model model, ItemDTO dto) {
		
		//[C]RUD
		//- 레코드 추가하기(insert)
		//- 추가할 정보 > (사용) > 엔티티 객체 생성(*****)
		//- ItemDTO > (매핑, 변환) > Item
		
		//Item item = new Item(dto.getSeq(), dto.getName(), dto.getPrice(), dto.getColor(), dto.getQty(), dto.getDescription());
		
		Item item = Item.builder()
						.seq(dto.getSeq())
						.name(dto.getName())
						.price(dto.getPrice())
						.color(dto.getColor())
						.qty(dto.getQty())
						.description(dto.getDescription())
						.build();
		
		itemRepository.save(item);
		
		return "result";
	}

	@GetMapping("/m")
	public String m(Model model) {
		
		return "result";
	}
	
}







