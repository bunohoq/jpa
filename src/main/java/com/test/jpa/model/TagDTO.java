package com.test.jpa.model;

import com.test.jpa.entity.Tag;

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
public class TagDTO {
	
	private Long seq;
	private String tag;
	
	public Tag toEntity() {
		
		return Tag.builder()
					 .seq(this.seq)
					 .tag(this.tag)
					 .build();
	}

}
