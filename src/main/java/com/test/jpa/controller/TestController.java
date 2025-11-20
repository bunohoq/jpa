package com.test.jpa.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

		@GetMapping("/m11")
		public String m11(Model model) {
			
			//After, Before
			//GreaterThan(GreaterThanEqual), LessThan(LessThanEqual)
			//Between
			
			//After, GreaterThan(GreaterThanEqual) : 크다
			//Before, LessThan(LessThanEqual) : 작다
			
			//After, Before : 날짜시간 비교
			//GreaterThan, LessThan : 숫자 비교
			//Between : 날짜시간, 숫자비교
			
			//List<Item> list = itemRepository.findByPriceGreaterThan(550000);
			//List<Item> list = itemRepository.findByPriceGreaterThanEqual(550000);
			//List<Item> list = itemRepository.findByPriceLessThanEqual(550000);
			//List<Item> list = itemRepository.findByColorAndPriceGreaterThan("blue", 200000);
			List<Item> list = itemRepository.findByPriceBetween(100000, 3000000);
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			
			return "result";
		}

		@GetMapping("/m12")
		public String m12(Model model) {
			
			//isEmpty, isNUll
			//isNotEmpty, isNotNull
			// - isNull > null 체크(where tel is null)
			// - isEmpty > 빈문자열, 집합(size: 0) 등을 체크
			
			//qty, description
			//List<Item> list = itemRepository.findByQtyIsNull();
			//List<Item> list = itemRepository.findByDescriptionIsNull();
			//List<Item> list = itemRepository.findByDescriptionIsNullAndQtyIsNull();
			List<Item> list = itemRepository.findByDescriptionIsNotNullAndColorAndPriceGreaterThanEqual("white", 300000);
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			
			
			return "result";
		}

		@GetMapping("/m13")
		public String m13(Model model) {
			
			//In, NotIn
			// - 열거형 조건
			// - where color in ('white', 'black)
			// - 매개변수 > List 전달
			
			List<String> colors = List.of("white", "black"); ///수정불가능
			
			//List<Item> list = itemRepository.findByColorIn(colors);
			List<Item> list = itemRepository.findByColorNotIn(colors);
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			return "result";
		}
		
		@GetMapping("/m14")
		public String m14(Model model) {
			
			//StartsWith, StartingWith
			//EndsWith, EndingWith
			//Contains
			//Like
			
			//List<Item> list = itemRepository.findByNameStartsWith("스마트");
			//List<Item> list = itemRepository.findByNameEndsWith("스피커");
			//List<Item> list = itemRepository.findByDescriptionContains("기능");
			//List<Item> list = itemRepository.findByDescriptionNotContains("기능");
			List<Item> list = itemRepository.findByDescriptionLike("%기능%");
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			return "result";
		}

		@GetMapping("/m15")
		public String m15(Model model) {
			
			//정렬
			//- OrderBy컬럼명Asc
			//- OrderBy컬럼명Desc
			
			//다중 정렬
			//- OrderBy컬럼명Asc컬럼명Desc컬럼명Asc
			
			//List<Item> list = itemRepository.findAll();
			//List<Item> list = itemRepository.findAllByOrderByNameAsc();
			//List<Item> list = itemRepository.findAllByOrderByNameDesc();
			//List<Item> list = itemRepository.findByColorOrderByPriceDesc("blue");
			List<Item> list = itemRepository.findAllByOrderByColorAscPriceDesc();
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			
			return "result";
		}

		@GetMapping("/m15_1")
		public String m15_1(Model model
				, @RequestParam("price") Integer price
				, @RequestParam("order") String orderString) {
			
			//- /m15_1?price=300000&order=asc
			//- /m15_1?price=300000&order=desc
			
			//List<Item> list = itemRepository.findByPriceGreaterThan(price);
			//List<Item> list = itemRepository.findByPriceGreaterThanOrderByPriceAsc(price);
			//List<Item> list = itemRepository.findByPriceGreaterThanOrderByPriceDesc(price);
			
			Direction order = Sort.Direction.ASC;

			if (orderString.equals("desc")) {
				order = Sort.Direction.DESC;
			}
			
			List<Item> list 
			= itemRepository.findByPriceGreaterThan(Sort.by(order, "price"), price);
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			return "result";
		}


		@GetMapping("/m16")
		public String m16(Model model) {
			
			//List<Item> list = itemRepository.findAllByOrderByPriceAsc();
			//List<Item> list = itemRepository.findAll(Sort.by("price"));
			//List<Item> list = itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price"	));
			
			List<Item> list = itemRepository
					.findAll(
							Sort.by(
									Sort.Order.asc("color"), 
									Sort.Order.desc("price")
									)
							);
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			return "result";
		}

		@GetMapping("/m17")
		public String m17(Model model) {
			
			//페이징
			PageRequest pageRequest = PageRequest.of(0, 10);
			
			//list 대신 page
			Page<Item> list = itemRepository.findAll(pageRequest);
			
			//Page 배열 > 페이징과 관련된 여러가지 정보를 제공
			System.out.println(list.getNumber()); //0 > 페이지번호
			System.out.println(list.getNumberOfElements()); //10 > 가져온 엔티티수
			System.out.println(list.getTotalElements()); //31 
			System.out.println(list.getTotalPages());	//4
			System.out.println(list.getSize());	//10
			
			
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			return "result";
		}

		@GetMapping("/m18")
		public String m18(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
			
			//페이징 구현
			//
			
			page--;
			
			PageRequest pageRequest = PageRequest.of(page, 10);
			Page<Item> list = itemRepository.findAll(pageRequest);
			
			//페이지 바
			String temp = "";
			for (int i=1; i<=list.getTotalPages(); i++) {
				temp += """
						<a href="/m18?page=%d">%d</a>
						""".formatted(i,i);
		
			}
			
			model.addAttribute("temp", temp);
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			return "result";
		}

		@GetMapping("/m19")
		public String m19(Model model, @PageableDefault(size = 10, sort = "price", direction = Sort.Direction.DESC) Pageable pageable ) {
			
			//PageRequest > 직접 생성
			//Pageable > 매개변수(page, size, sort)
			
			//- /m19?page=1
			Page<Item> list = itemRepository.findAll(pageable);
			
			System.out.println(list.getNumber());
			System.out.println(list.getTotalPages());
			
			System.out.println(list.hasContent());
			System.out.println(list.hasNext());		//true > 다음페이지가 있나
			System.out.println(list.hasPrevious());	//false > 전페이지가 있나
			System.out.println(list.nextOrLastPageable());
			System.out.println(list.nextPageable());
			System.out.println(list.previousOrFirstPageable());
			System.out.println(list.previousPageable());
			System.out.println(list.isFirst());		//true > 첫번째 페이지?
			System.out.println(list.isLast());		
			
			
			//페이징
			//이전페이지 다음페이지
			
			String temp = "";
			
			temp += """
					<a href="/m19?page=%d">이전페이지</a>
					""".formatted(list.previousOrFirstPageable().getPageNumber());
			temp += """
					<a href="/m19?page=%d">다음페이지</a>
					""".formatted(list.nextOrLastPageable().getPageNumber());
			model.addAttribute("temp", temp);
			
			List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
			model.addAttribute("dtoList", dtoList);
			
			return "result";
		}

		@GetMapping("/m")
		public String m(Model model) {
			
			return "result";
		}
		
	}