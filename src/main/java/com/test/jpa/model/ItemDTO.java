package com.test.jpa.model;

import com.test.jpa.entity.Item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
	
	private Long seq;
	private String name;
	private Integer price;
	private String color;
	private Integer qty;
	private String description;
	
	public ItemDTO(String name, String color, Integer price) {
		 this.name = name;
		 this.color = color;
		 this.price = price;
	}
	
	public Item toEntity() {
		
		//매핑 메서드
		return Item.builder()
					.seq(this.seq)
					.name(this.name)
					.price(this.price)
					.color(this.color)
					.qty(this.qty)
					.description(this.description)
					.build();
	}

}











