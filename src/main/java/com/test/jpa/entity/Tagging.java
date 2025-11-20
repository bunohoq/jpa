package com.test.jpa.entity;

import com.test.jpa.model.TaggingDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblTagging")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tagging {

	@Id
	@SequenceGenerator(name = "seqTaggingGen", allocationSize = 1, sequenceName = "seqTagging")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTaggingGen")
	private Long seq;
	
	@ManyToOne
	@JoinColumn(name = "tseq")
	private Tag tag;
	
	@ManyToOne
	@JoinColumn(name = "bseq")
	private Board board;
	
	public TaggingDTO toDTO() {
		
		return TaggingDTO.builder()
						 .seq(this.seq)
						 .tagSeq(this.tag.getSeq())
						 .boardSeq(this.board.getSeq())
						 .build();
	}
}
