package com.test.jpa.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.jpa.JpaApplication;
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
	
	@GetMapping("/m2")
	public String m2(Model model, @RequestParam("seq") Long seq) {
		
		//C[R]UD
		//- 1개의 레코드 가져오기
		//- itemRepository.findById(PK)
		//- itemRepository.getById(PK)
		//- itemRepository.getOne(PK)
		
		//ID == PK
		//- PK로 검색하기 > 레코드 1개 > 엔티티 1개
		Optional<Item> item = itemRepository.findById(seq);
		
		//select i1_0.seq,i1_0.color,i1_0.description,i1_0.name,i1_0.price,i1_0.qty  from  tblItem i1_0  where  i1_0.seq=?
		//System.out.println(item);
		
//		if (item.isPresent()) {
//			
//			//Item > ItemDTO
//			model.addAttribute("dto", item.get().toDTO());
//			
//		}
		
		item.ifPresent(entity -> model.addAttribute("dto", entity.toDTO()));
		
		return "result";
	}
	
	@GetMapping("/m3")
	public String m3(Model model, @RequestParam("seq") Long seq) {
		
		//m3?seq=10
		
		Optional<Item> item = itemRepository.findById(seq);
		
		item.ifPresent(value -> model.addAttribute("dto", value.toDTO()));
		
		return "m3";
	}
	
	@PostMapping("/m3ok")
	public String m3ok(Model model, ItemDTO dto) {
		
		//CR[U]D
		//- 레코드 수정하기
		//A. 엔티티 직접 생성 후(DTO 기반) > 값을 수정 > 수정하기(save)
		//B. 검색 > 엔티티 반환 > 값을 수정 > 수정하기
		
		//공통
//		Item item = Item.builder()
//						.seq(31L)
//						.name("뱅앤룹 스피커")
//						.price(3500000)
//						.color("blue")
//						.qty(10)
//						.description("휴대용 스피커입니다.")
//						.build();

		//Item item = dto.toEntity();
		
		//Item item = dto.toEntity();
		
				Optional<Item> result = itemRepository.findById(dto.getSeq());
				
				Item result2 = result.get(); //DB에서 가져온 엔티티
				result2.update(dto.getName(), dto.getPrice(), dto.getColor(), dto.getQty(), dto.getDescription());
				
				itemRepository.save(result2);
				
				return "result";
			}

		@GetMapping("/m4")
		public String m4(Model model, @RequestParam("seq") Long seq) {				
			//m4?seq=31
			
			//CRU[D]
			//- 레코드 삭제하기
				
			//A. 엔티티 직접 생성 후 > 삭제
			//B. 엔티티 검색 반환 후 > 삭제
				
//			Item item = Item.builder()
//							.seq(seq)
//							.build();
				
			Optional<Item> item = itemRepository.findById(seq);
			
			itemRepository.delete(item.get());		
			
			return "result";
		}
		
		
		@GetMapping("/m5")
		public String m5(Model model) {
			
			/*
			 
			 1. Query Method
			 - 정해진 키워드 사용 > 메서드명 생성 > 메서드 호출 > 메서드명에 따라 정해진 SQL 생성
			 - 정해진 행동 키워드 + 컬럼명
			  
			 */
			
			//Optional<Item> item = itemRepository.findById(1L);
			
			//findBy + 컬럼명
			
			Optional<Item> item =  itemRepository.findByName("노트북");
			//Item item =  itemRepository.findByName("키보드");
			
			 //model.addAttribute("dto", item.toDTO());
			
			return "result";
		}
		
		@GetMapping("/m6")
		public String m6(Model model) {
			
			//select count(*)
			Long count = itemRepository.count();
			
			//레코드 존재 유무
			Boolean exist = itemRepository.existsById(1L);
			
			model.addAttribute("count", count);
			model.addAttribute("exist", exist);
			
			return "result";
		}
		@GetMapping("/m7")
		public String m7(Model model) {
			
			//전체 레코드 가져오기
			List<Item> list =itemRepository.findAll();
			
			//List<엔티티> >> List<DTO>
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());

			model.addAttribute("dtoList", dtoList);
			
			return "result";
		}
		@GetMapping("/m8")
		public String m8(Model model) {
			
			//Is, Equals
			//Optional<Item> item =  itemRepository.findByName("뱅앤룹 스피커");
			//Optional<Item> item =  itemRepository.findByNameIs("뱅앤룹 스피커");
			//Optional<Item> item =  itemRepository.findByNameEquals("뱅앤룹 스피커");
			
			
			//model.addAttribute("dto", item.get().toDTO());
			
			//List<Item> list = itemRepository.findByColor("blue");
			//List<Item> list = itemRepository.findByColorIgnoreCase("BLUE");
			
			//List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			
			//model.addAttribute("dtoList", dtoList);
			
			Optional<Item> item =  itemRepository.findByColor("blue");
			
			model.addAttribute("dto", item.get().toDTO());
			
		
			return "result";
			
		}
		
		@GetMapping("/m9")
		public String m9(Model model) {
			
			//First, Top
			// - 가져올 레코드의 개수를 지정한다.
			// - 결과셋에서 ㅟ에서부터 몇개
			// - select * from table where rownum <= 3; //Oracle
			// - select * from table limit 0, 3; //MySQL
			// - select top 3 * from table; //MS-SQL
			
			//Optional<Item> item = itemRepository.findFirstByColor("white");
			//Optional<Item> item = itemRepository.findFirstByPrice(55000);
			
			//model.addAttribute("dto", item.get().toDTO());
			
			List<Item> list = itemRepository.findFirst5ByColor("white");
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			
			return "result";
		}

		@GetMapping("/m10")
		public String m10(Model model) {
			
			//And, Or
			//Optional<Item> item =  itemRepository.findByNameAndColor("뱅앤룹 스피커", "blue");
			
			//model.addAttribute("dto", item.get().toDTO());
			
			List<Item> list = itemRepository.findByNameOrColor("뱅앤룹 스피커", "blue");


			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			

			
			return "result";
		}

		@GetMapping("/m")
		public String m(Model model) {
			
			return "result";
		}
		
	}