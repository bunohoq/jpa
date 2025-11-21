package com.test.jpa.entity;

import com.test.jpa.model.ItemDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//엔티티(Entity) 클래스
//- 역할: tblItem 테이블을 자바와 중계해주는 역할
//- 자바에서 Item 클래스를 조작 > (개발자X) > tblItem 반영

//DTO > 아무 기능이 없는 상자
//Entity > 기능이 많은 상자

//Entity <- (연결) -> 테이블

@Entity
@Getter
//@Setter
@ToString
@Builder
@NoArgsConstructor //Entity는 반드시 기본 생성자가 필수 구현
@AllArgsConstructor
@Table(name = "tblItem") // JPA > DB > tbl_item
public class Item {
	
	@Id //PK 필수!!
	@Column(name = "seq") //실제 테이블의 컬럼명. 생략 가능
	@SequenceGenerator(name = "seqItemGen", allocationSize = 1, sequenceName = "seqItem")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqItemGen")
	private Long seq;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "price", nullable = false)
	private Integer price;
	
	@Column(name = "color", nullable = false, length = 50)
	private String color;
	
	//@Column(name = "qty", nullable = true)
	@Column(name = "qty")
	private Integer qty;
	
	@Column(name = "description", length = 1000)
	private String description;
	
	public ItemDTO toDTO() {
		
		return ItemDTO.builder()
				.seq(this.seq)
				.name(this.name)
				.price(this.price)
				.color(this.color)
				.qty(this.qty)
				.description(this.description)
				.build();
	}
	
	//Setter 역할
	public void update(String name, Integer price, String color, Integer qty, String description) {
		
		this.name = name;
		this.price = price;
		this.color = color;
		this.qty = qty;
		this.description = description;
		
	}
	
}












