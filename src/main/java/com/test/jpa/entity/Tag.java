package com.test.jpa.entity;

import java.util.List;

import com.test.jpa.model.TagDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblTag")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

	@Id
	@SequenceGenerator(name = "seqTagGen", allocationSize = 1, sequenceName = "seqTag")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTagGen")
	private Long seq;
	
	@Column(nullable = false, length = 100)
	private String tag;
	
	@OneToMany(mappedBy = "tag")
	private List<Tagging> taggings;
	
	public TagDTO toDTO() {
		
		return TagDTO.builder()
					.seq(this.seq)
					.tag(this.tag)
					.build();
	}
	
}






