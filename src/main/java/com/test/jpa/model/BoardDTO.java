package com.test.jpa.model;

import com.test.jpa.entity.Board;

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
public class BoardDTO {

	private Long seq;
	private String subject;
	private String content;
	private String regdate;
	private String user; //id
	
	public Board toEntity() {
		
		return Board.builder()
					.seq(this.seq)
					.subject(this.subject)
					.content(this.content)
					.regdate(this.regdate)
					//.user(user.getId())
					.build();
	}
	
}

