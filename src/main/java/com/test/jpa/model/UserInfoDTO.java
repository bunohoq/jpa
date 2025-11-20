package com.test.jpa.model;

import com.test.jpa.entity.UserInfo;

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
public class UserInfoDTO {
	
	private String id;
	private Integer age;
	private String address;
	private String gender;
	
	public UserInfo toEntity() {
		
		return UserInfo.builder()
					.id(this.id)
					.age(this.age)
					.address(this.address)
					.gender(this.gender)
					.build();
	}

}






