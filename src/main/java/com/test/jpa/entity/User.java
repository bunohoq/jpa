package com.test.jpa.entity;

import java.util.List;

import com.test.jpa.model.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblUser")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@Column(length = 50)
	private String id;
	
	@Column(nullable = false, length = 50)
	private String pw;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	//나To상대방
	@OneToOne(mappedBy = "user")
	private UserInfo userInfo;
	
	@OneToMany(mappedBy = "user")
	private List<Board> boards;
	
	public UserDTO toDTO() {
		
		return UserDTO.builder()
					.id(this.id)
					.pw(this.pw)
					.name(this.name)
					.build();
	}
	
}










