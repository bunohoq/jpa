package com.test.jpa.entity;

import java.util.List;

import com.test.jpa.model.BoardDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblBoard")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	@Id
	@SequenceGenerator(name = "seqBoardGen", allocationSize = 1, sequenceName = "seqBoard")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqBoardGen")
	private Long seq;
	
	@Column(nullable = false, length = 1000)
	private String subject;
	
	@Column(nullable = false, length = 4000)
	private String content;
	
	@Column(nullable = false)
	private String regdate;
	
	//private String user; //id
	
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;
	
	@OneToMany(mappedBy = "board")
	private List<Tagging> taggings;
	
	public BoardDTO toDTO() {
		
		return BoardDTO.builder()
					.seq(this.seq)
					.subject(this.subject)
					.content(this.content)
					.regdate(this.regdate)
					.user(user.getName())
					.build();
	}

}








