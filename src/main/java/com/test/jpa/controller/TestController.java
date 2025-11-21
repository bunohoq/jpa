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

import com.querydsl.core.Tuple;
import com.test.jpa.config.QueryDSLConfig;
import com.test.jpa.entity.Board;
import com.test.jpa.entity.Item;
import com.test.jpa.entity.Tag;
import com.test.jpa.entity.User;
import com.test.jpa.entity.UserInfo;
import com.test.jpa.model.BoardDTO;
import com.test.jpa.model.ItemDTO;
import com.test.jpa.repository.BoardRepository;
import com.test.jpa.repository.ItemQueryDSLRepository;
import com.test.jpa.repository.ItemRepository;
import com.test.jpa.repository.TagRepository;
import com.test.jpa.repository.UserInfoRepository;
import com.test.jpa.repository.UserQueryDSLRepository;
import com.test.jpa.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final QueryDSLConfig queryDSLConfig;

    private final TagRepository tagRepository;
	
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;
	private final UserInfoRepository userInfoRepository;
	private final BoardRepository boardRepository;
	private final ItemQueryDSLRepository itemQueryDSLRepository;
	private final UserQueryDSLRepository userQueryDSLRepository;
	
	
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
		
		//공통 > 최종 목적 > 엔티티 확보
//		Item item = Item.builder()
//						.seq(52L)
//						.name("카페 책상")
//						.price(350000)
//						.color("yellow")
//						.qty(100)
//						.description("공부 카페용 책상입니다.")
//						.build();
		
		//Item item = dto.toEntity();
		
		Optional<Item> result = itemRepository.findById(dto.getSeq());
		
		Item result2 = result.get(); //DB에서 가져온 엔티티
		result2.update(dto.getName(), dto.getPrice(), dto.getColor(), dto.getQty(), dto.getDescription());
		
		itemRepository.save(result2);
		
		return "result";
	}

	@GetMapping("/m4")
	public String m4(Model model, @RequestParam("seq") Long seq) {
		
		//m4?seq=52
		
		//CRU[D]
		//- 레코드 삭제하기
		
		//A. 엔티티 직접 생성 후 > 삭제
		//B. 엔티티 검색 반환 후 > 삭제
		
//		Item item = Item.builder()
//						.seq(seq)
//						.build();
		
		//안정성
		Optional<Item> item = itemRepository.findById(seq);
		
		if (item.isPresent()) {
			itemRepository.delete(item.get());
		} else {
			
		}
		
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
		
		Optional<Item> item = itemRepository.findByName("스마트폰");
		//Item item = itemRepository.findByName("키보드");
		
		model.addAttribute("dto", item.get().toDTO());
		
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
		List<Item> list = itemRepository.findAll();
		
		//List<엔티티> >> List<DTO>
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}

	@GetMapping("/m8")
	public String m8(Model model) {
		
		//Is, Equals
		//Optional<Item> item = itemRepository.findByName("노트북");
		//Optional<Item> item = itemRepository.findByNameIs("노트북");
		//Optional<Item> item = itemRepository.findByNameEquals("노트북");
				
		//model.addAttribute("dto", item.get().toDTO());
		
		//List<Item> list = itemRepository.findByColor("yellow");
		//List<Item> list = itemRepository.findByColorIgnoreCase("Yellow");
		
//		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
//		model.addAttribute("dtoList", dtoList);
		
		Optional<Item> item = itemRepository.findByColor("black");
		
		model.addAttribute("dto", item.get().toDTO());
		
		return "result";
	}

	@GetMapping("/m9")
	public String m9(Model model) {
		
		//First, Top
		//- 가져올 레코드의 개수를 지정한다.
		//- 결과셋에서 위에서부터 몇개
		//- select * from table where rownum <= 3; //Oracle
		//- select * from table limit 0, 3; //MySQL
		//- select top 3 * from table; //MS-SQL
		
		//Optional<Item> item = itemRepository.findFirstByColor("white");
		//Optional<Item> item = itemRepository.findFirstByPrice(55000);
		
		//model.addAttribute("dto", item.get().toDTO());
		
		
		//List<Item> list = itemRepository.findFirst3ByColor("white");
		
		List<Item> list = itemRepository.findTop5ByColor("white");
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}

	@GetMapping("/m10")
	public String m10(Model model) {
		
		//And, Or
		//Optional<Item> item = itemRepository.findByNameAndColor("노트북", "black");
		//model.addAttribute("dto", item.get().toDTO());
		
		List<Item> list = itemRepository.findByNameOrColor("노트북", "black");
		
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
		//Between : 날짜시간, 숫자 비교
		
		//List<Item> list = itemRepository.findByPriceGreaterThan(550000);
		//List<Item> list = itemRepository.findByPriceGreaterThanEqual(550000);
		//List<Item> list = itemRepository.findByPriceLessThanEqual(550000);
		//List<Item> list = itemRepository.findByColorAndPriceGreaterThan("white", 500000);
		
		List<Item> list = itemRepository.findByPriceBetween(300000, 500000);
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}

	@GetMapping("/m12")
	public String m12(Model model) {
		
		//isEmpty, isNull
		//isNotEmpty, isNotNull
		//- isNull > null 체크(where tel is null)
		//- isEmpty > 빈문자열, 집합(size: 0) 등을 체크
		
		//qty, description
		//List<Item> list = itemRepository.findByQtyIsNull();
		//List<Item> list = itemRepository.findByDescriptionIsNull();
		//List<Item> list = itemRepository.findByDescriptionIsNullAndQtyIsNull();
		//List<Item> list = itemRepository.findByDescriptionIsNotNullAndColorAndPriceGreaterThanEqual("white", 300000);
		
		List<Item> list = itemRepository.findByQtyIsNullAndDescriptionIsNull();
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}

	@GetMapping("/m13")
	public String m13(Model model) {
		
		//In, NotIn
		//- 열거형 조건
		//- where color in ('white', 'black')
		//- 매개변수 > List 전달
		
		List<String> colors = List.of("white", "black"); //수정 불가능
		
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
		//Like > 사용자 정의
		
		//List<Item> list = itemRepository.findByNameStartsWith("스마트");
		//List<Item> list = itemRepository.findByNameEndsWith("폰");
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
		//List<Item> list = itemRepository.findByColorOrderByPriceDesc("black");
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
		
		//정렬 > 다른 방식
		//List<Item> list = itemRepository.findAllByOrderByPriceAsc();
		
		//List<Item> list = itemRepository.findAll(Sort.by("price"));//asc
		//List<Item> list = itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
		
		//List<Item> list = itemRepository.findAll(Sort.by("color", "price"));
		
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
		PageRequest pageRequest = PageRequest.of(3, 10);
		
		//List 대신 Page
		Page<Item> list = itemRepository.findAll(pageRequest);
		
		//Page 배열 > 페이징과 관련된 여러가지 정보를 제공
		System.out.println(list.getNumber()); //0 > 페이지 번호
		System.out.println(list.getNumberOfElements()); //10 > 가져온 엔티티수
		System.out.println(list.getTotalElements()); //31 > 총 엔티티 수
		System.out.println(list.getTotalPages()); //4 > 총 페이지 수
		System.out.println(list.getSize()); //10 > 한 페이지당 엔티티 수
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}

	@GetMapping("/m18")
	public String m18(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
		
		//페이징 구현
		//- /m18
		//- /m18?page=1
		//- /m18?page=2
		//- /m18?page=3
		page--;
		
		System.out.println(page);
		
		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<Item> list = itemRepository.findAll(pageRequest);
		
		//페이지 바
		String temp = "";
		for (int i=1; i<=list.getTotalPages(); i++) {
			temp += """
					<a href="/m18?page=%d">%d</a>
					""".formatted(i, i);
		}
		
		model.addAttribute("temp", temp);
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}

	@GetMapping("/m19")
	public String m19(Model model, @PageableDefault(size = 10, sort = "price", direction = Sort.Direction.DESC) Pageable pageable) {
		
		//PageRequest > 직접 생성
		//Pageable > 매개변수(page, size, sort)
		
		//- /m19
		//- /m19?page=1
		//- /m19?page=2
		//- /m19?page=3
		Page<Item> list = itemRepository.findAll(pageable);
		
		System.out.println(list.getNumber());
		System.out.println(list.getTotalPages());
		
		System.out.println(list.hasContent());
		System.out.println(list.hasNext()); //true
		System.out.println(list.hasPrevious()); //false
		System.out.println(list.nextOrLastPageable());
		System.out.println(list.nextPageable());
		System.out.println(list.previousOrFirstPageable());
		System.out.println(list.previousPageable());
		System.out.println(list.isFirst());
		System.out.println(list.isLast());
		
		//페이징
		//이전페이지 다음페이지
		
		String temp = "";
		
		temp += """
				<a href="/m19?page=%d">이전 페이지</a>
				""".formatted(list.previousOrFirstPageable().getPageNumber());
		
		temp += """
				<a href="/m19?page=%d">다음 페이지</a>
				""".formatted(list.nextOrLastPageable().getPageNumber());
		
		model.addAttribute("temp", temp);
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}
	
	@GetMapping("/m20")
	public String m20(Model model) {
		
		//1:1
		//- tblUser:tblUserInfo
		//- User:UserInfo
		
		//User 가져오기
		//Optional<User> user = userRepository.findById("hong");
				
		//model.addAttribute("user", user.get().toDTO());
		//model.addAttribute("userinfo", user.get().getUserInfo().toDTO());
		
		return "result";
	}
	
	@GetMapping("/m21")
	public String m21(Model model) {
		
		//1:1
		//- tblUser:tblUserInfo
		//- User:UserInfo
		
		Optional<UserInfo> userinfo = userInfoRepository.findById("hong");
		
		model.addAttribute("userinfo", userinfo.get().toDTO());
		model.addAttribute("user", userinfo.get().getUser().toDTO());
		
		return "result";
	}

	@GetMapping("/m22")
	public String m22(Model model) {
		
		//1:N
		//- tblUser:tblBoard
		//- User:Board
		
		//Optional<User> user = userRepository.findById("hong");
		
		//model.addAttribute("user", user.get().toDTO());
		//model.addAttribute("boards", user.get().getBoards());
		
		List<Board> list = boardRepository.findAll();
		
		List<BoardDTO> blist = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("blist", blist);
		
		return "result";
	}

	@GetMapping("/m23")
	public String m23(Model model) {
		
		//1:N N:1
		//N:N
		Optional<Board> board = boardRepository.findById(10L);
		
		System.out.println(board);
		System.out.println(board.get().getTaggings());
		System.out.println(board.get().getTaggings().get(0).getTag().getTag());
		System.out.println(board.get().getTaggings().get(1).getTag().getTag());
		
		model.addAttribute("board", board.get());
		
		return "result";
	}

	@GetMapping("/m24")
	public String m24(Model model) {
		
		List<Tag> tlist = tagRepository.findAll();
		
		model.addAttribute("tlist", tlist);
		
		return "result";
	}
	
	@GetMapping("/m25")
	public String m25(Model model) {
	
		/*
			2. JPQL, Java Persistence Query Language
			- JPA에서 질의에 사용하는 전용 질의문(JPA 전용 SQL)
			- JPQL이 객체를 대상(*****)으로 만들어졌다.
			- SQL과 많이 유사
		*/
		
		List<Item> list = itemRepository.m25();
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}
	
	@GetMapping("/m26")
	public String m26(Model model) {
		
		List<String> names = itemRepository.m26();
		
		model.addAttribute("names", names);
		
		return "result";
	}
	
	@GetMapping("/m27")
	public String m27(Model model
		, @RequestParam(name = "color", defaultValue = "black") String color) {
		
		//- /m27
		//- /m27?color=white
		List<Item> list = itemRepository.m27(color);
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}
	
	@GetMapping("/m28")
	public String m28(Model model, ItemDTO dto) {
		
		//- /m28?color=white&price=300000
		List<Item> list = itemRepository.m28(dto);
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}
	
	@GetMapping("/m29")
	public String m29(Model model) {
		
		/*
			                               방언(Dialect)
			findFirst3By..                 (변환) > rownum
			                               (변환) > limit
			                               (변환) > top
			
			Query Method > (변환) > JPQL > (변환) > SQL
			Query DSL    > (변환) > JPQL > (변환) > SQL
			
			3. Query DSL
			- JPQL 작성을 도와주는 동적 쿼리 빌더
			- 정해진 메서드를 사용 > 쿼리 생성
			- 엔티티의 관련된 조작을 하는 메서드를 생성해주는 QClass를 생성
			
		*/
		
		//전체 리스트 조회
		List<Item> list = itemQueryDSLRepository.m29();
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}
	
	@GetMapping("/m30")
	public String m30(Model model, @RequestParam(name = "name", defaultValue = "노트북") String name) {
		
		//- /m30?name=태블릿
		
		//레코드 1개 반환
		//- 조건절: where() 메서드
		//- fetchOne()
		
		Item item = itemQueryDSLRepository.m30(name);
		
		model.addAttribute("dto", item.toDTO());
		
		return "result";
	}
	
	@GetMapping("/m31")
	public String m31(Model model) {
		
		List<String> names = itemQueryDSLRepository.m31();
		
		model.addAttribute("names", names);
		
		return "result";
	}
	
	@GetMapping("/m32")
	public String m32(Model model) {
		
		//일부 컬럼 조회 > Entity 사용X
		//1. Tuple
		//2. DTO
		
		List<Tuple> tupleList = itemQueryDSLRepository.m32();
		
		model.addAttribute("tupleList", tupleList);
		
		return "result";
	}
	
	@GetMapping("/m33")
	public String m33(Model model) {
		
		//일부 컬럼 조회 > Entity 사용X
		//1. Tuple
		//2. DTO
		
		//일부 컬럼 조회
		//1. Entity > 편함, 모든컬럼. 사용하지 않을 컬럼까지 가져옴(낭비)
		//2. Tuple > 조금 편함. 컬럼 인덱스 접근(불편)
		//3. DTO > 불편함. Tuple보다 안정적
		
		List<ItemDTO> list = itemQueryDSLRepository.m33();
		
		model.addAttribute("dtoList", list);
		
		return "result";
	}
	
	@GetMapping("/m34")
	public String m34(Model model, ItemDTO dto) {
		
		//where()
		List<Item> list = itemQueryDSLRepository.m34(dto);
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}
	
	@GetMapping("/m35")
	public String m35(Model model) {
		
		//정렬
		List<Item> list = itemQueryDSLRepository.m35();
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}
	
	@GetMapping("/m36")
	public String m36(Model model
			, @RequestParam(name = "page", defaultValue = "1") Integer page) {
		
		//페이징
		//- offset: 가져올 레코드의 시작 위치(begin)
		//- limit: 가져올 개수(size)
		
		//- offset: 0
		//- limit: 10
		int offset = (page - 1) * 10;
		int limit = 10;
		
		List<Item> list = itemQueryDSLRepository.m36(offset, limit);
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		return "result";
	}
	
	@GetMapping("/m37")
	public String m37(Model model) {
		
		//집계함수
		//Object num = itemQueryDSLRepository.m37();
		//model.addAttribute("num", num);
	
		Tuple tuple = itemQueryDSLRepository.m37_1();
		model.addAttribute("tuple", tuple);
		
		
		return "result";
	}
	
	@GetMapping("/m38")
	public String m38(Model model) {
		
		//그룹 + 집계
		List<Tuple> glist = itemQueryDSLRepository.m38();
		
		model.addAttribute("glist", glist);
		
		return "result";
	}

	@GetMapping("/m39")
	public String m39(Model model) {
		
		//Join
		//1:1
		User user = userQueryDSLRepository.m39();
		
		model.addAttribute("user", user.toDTO());
		model.addAttribute("userinfo", user.getUserInfo().toDTO());
		
		
		return "result";
	}

	@GetMapping("/m40")
	public String m40(Model model) {
		
		//Join
		//1:N
		List<User> ulist = userQueryDSLRepository.m40();
		
		model.addAttribute("ulist", ulist);
		
		return "result";
	}

	@GetMapping("/m41")
	public String m41(Model model) {
		
		//서브쿼리
		//- where절(O)
		//- select절(O)
		//- from절(X)
		
		// select * from tblItem where price >= (select avg(price) from tblItem)
		List<Item> list = itemQueryDSLRepository.m42();
		
		List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
		model.addAttribute("dtoList", dtoList);
		
		
		return "result";
	}

	@GetMapping("/m")
	public String m(Model model) {
		
		return "result";
	}
	
}