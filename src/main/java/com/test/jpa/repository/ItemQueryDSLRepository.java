package com.test.jpa.repository;

import static com.test.jpa.entity.QItem.item;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.jpa.entity.Item;
import com.test.jpa.entity.QItem;
import com.test.jpa.model.ItemDTO;

import lombok.RequiredArgsConstructor;

//DAO or Service 역할
@Repository
@RequiredArgsConstructor
public class ItemQueryDSLRepository {

	private final JPAQueryFactory factory; //mapper 역할

	public List<Item> m29() {
		
		/*
			QClass의 메서드를 사용한 쿼리 작성
			
			- selectFrom: 해당 엔티티에서 모든 컬럼 조회
			
			모든 select() 이후 > 마지막에 사용
			- fetch(): 리스트 조회(다중 행 가져오기). List<엔티티>, 결과가 없으면 빈 리스트 반환
			- fetchOne(): 단일 행 조회(1개 행 가져오기). 엔티티 1개를 반환. 결과가 다중 행이면 예외 발생 
			- fetchFirst(): 단일 행 조회(1개 행 가져오기). 엔티티 1개를 반환. 결과가 다중 행이면 첫번째 행을 반환
			- fetchResults(): 페이지 정보 포함
			- fetchCount(): 카운트 반환
			
		*/
		
		//select * from 테이블
		//- 순수 엔티티(Item) X
		//- QClass O
		List<Item> list = factory
							.selectFrom(item) //SQL(JPQL) 생성 + ResultSet 생성
							.fetch();		  //selectList() + Mapping
		
		return list;
	}

	public Item m30(String name) {
		
		//JPQL
		//- 테이블 > 엔티티
		//- 컬럼 > 엔티티.필드
		//- select name from tblItem
		//- select i.name from Item as i
		
		return factory
				.selectFrom(item)
				.where(item.name.eq(name))
				.fetchOne();
	}

	public List<String> m31() {
		
		//모든 컬럼: select * from > selectFrom()
		//특정 컬럼: select 컬럼 from > select() + from()
				
		return factory
					.select(item.name)	//select name
					.from(item)			//from tblItem
					.fetch();			//mapping
	}

	public List<Tuple> m32() {
		
		return factory
					.select(item.name, item.color, item.price)
					.from(item)
					.fetch();
	}

	public List<ItemDTO> m33() {
		
		//select 결과 > 엔티티 > (매핑) > DTO
		
		//사전 준비물
		//- DTO 생성자
		
		return factory
					.select(Projections.constructor(ItemDTO.class, item.name, item.color, item.price))
					.from(item)
					.fetch();
	}

	public List<Item> m34(ItemDTO dto) {
		
		/*
		
			where()
			
			- 동등 비교
				- where(item.color.eq("black"))
				- where(item.color.ne("black"))
				- where(item.description.isNull())
				- where(item.description.isNotNull())
			
			- 범위 비교(숫자, 날짜)
				- where(item.price.gt(300000))
				- where(item.price.lt(300000))
				- where(item.price.goe(300000))
				- where(item.price.loe(300000))
				- where(item.price.between(300000, 500000))
		
			- 열거형(in)
				- where(item.color.in("red", "yellow", "blue"))
				- where(item.color.notIn("red", "yellow", "blue"))
		
			- 패턴 문자열
				- where(item.description.startsWith("최신"))
				- where(item.description.endsWith("입니다."))
				- where(item.description.contains("스마트"))
				- where(item.description.like("%스마트%"))
		
			- 논리 연산
				- and()
				- or()
				- not()
		
		*/
		
		return factory
					.selectFrom(item)
					.where(item.color.eq("white")
							.and(item.price.gt(100000))
							.and(item.qty.isNotNull()))
					.fetch();
	}

	public List<Item> m35() {
		
		/*
			정렬
			- orderBy(정렬기준)
			
			정렬기준
			- 엔티티.컬럼.기준()
				- asc()
				- desc()
				- nullsLast()
				- nullsFirst()
		*/
		
		return factory
					.selectFrom(item)
					//.orderBy(item.color.desc())
//					.orderBy(item.color.asc()
//							, item.price.desc()
//							, item.qty.asc())
					//.orderBy(item.qty.asc().nullsFirst())
					.orderBy(item.qty.desc().nullsLast())
					.fetch();
	}

	public List<Item> m36(int offset, int limit) {
		
		return factory
					.selectFrom(item)
					.offset(offset)
					.limit(limit)
					.fetch();
	}

	public Object m37() {
		
		//count(), sum(), avg(), max(), min()
		
		//select * from..
//		return factory
//				.selectFrom(item)
//				.fetchCount();
		
		//select count(*) from..
		//select count(qty) from..
		return factory
				//.select(item.count())
				//.select(item.count())
				.select(item.qty.avg())
				.from(item)
				.fetchOne();
				
	}

	public Tuple m37_1() {
		
		return factory
					.select(item.count()
							, item.price.max()
							, item.price.min()
							, item.price.sum()
							, item.price.avg())
					.from(item)
					.fetchOne();
	}

	public List<Tuple> m38() {
		
		//groupBy()
		//having()
		
		return factory
					.select(item.color, item.count(), item.price.avg())
					.from(item)
					.groupBy(item.color)
					.having(item.count().goe(5))
					.fetch();
	}

	public List<Item> m41() {
		// select * from tblItem where price >= (select avg(price) from tblItem)
		
		QItem item2 = QItem.item;
		
		return factory
					.selectFrom(item)
					.where(item.price.goe(
							JPAExpressions.select(item2.price.avg()).from(item2)
							)
					)
					.fetch();
			
	}

	public List<Tuple> m42() {
		
		//select name, price, (select avg(price) from tblItem) from tblItem;
		QItem item2 = QItem.item;
		return factory
					.select(item.name
							, item.price
							, JPAExpressions
								.select(item2.price.avg())
								.from(item2))
					.from(item)
					.fetch();
	}

		public List<Item> m43(ItemDTO dto) {
		
			//조건절 생성기
			BooleanBuilder builder = new BooleanBuilder();
			
			if (dto.getColor() != null) {
				builder.and(item.color.eq(dto.getColor()));
			}
			
			if (dto.getPrice() != null) {
				builder.and(item.price.goe(dto.getPrice()));
			}
			
			return factory
						.selectFrom(item)
						.where(builder)
						.fetch();
		}
	
}