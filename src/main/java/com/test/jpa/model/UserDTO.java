package com.test.jpa.model;

import com.test.jpa.entity.User;

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
public class UserDTO {
	
	private String id;
	private String pw;
	private String name;
	
	public User toEntity() {
		
		return User.builder()
				.id(this.id)
				.pw(this.pw)
				.name(this.name)
				.build();
	}
	
}









