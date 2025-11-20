package com.test.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	
	private Long seq;
	private String subject;
	private String content;
	private String regdate;
	private String user;	//id
	
	public BoardDTO toEntity() {
		
		return BoardDTO.builder()
				.seq(this.seq)
				.subject(this.subject)
				.content(this.content)
				.regdate(this.regdate)
				//.user(user.getId())
				.build();
						
	}

}
