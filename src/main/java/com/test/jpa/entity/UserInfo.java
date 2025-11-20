package com.test.jpa.entity;

import com.test.jpa.model.UserInfoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblUserInfo")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	
	@Id
	@Column(length = 50)
	private String id;
	
	@Column(nullable = false)
	private Integer age;
	
	@Column(nullable = false, length = 500)
	private String address;
	
	@Column(length = 1)
	private String gender;
	
	@OneToOne
	@JoinColumn(name = "id")
	private User user;
	
	public UserInfoDTO toDTO() {
		
		return UserInfoDTO.builder()
					.id(this.id)
					.age(this.age)
					.address(this.address)
					.gender(this.gender)
					.build();
	}

}






