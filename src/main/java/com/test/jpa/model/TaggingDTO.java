package com.test.jpa.model;

import com.test.jpa.entity.Tagging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaggingDTO {

	private Long seq;
	private Long tagSeq;
	private Long boardSeq;
	
	public Tagging toEntity() {
		
		return Tagging.builder()
					.seq(this.seq)
					//.tag()
					//.board()
					.build();
	}
	
}




