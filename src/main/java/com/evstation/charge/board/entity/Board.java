package com.evstation.charge.board.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;
	
	//free,qna 등등
	private String boardType;
	
	private String boardNm;
	
	private String boardWriter;
	
	private String boardContent;
	
	@CreationTimestamp
	private Timestamp boardCreateTime;
	
	@UpdateTimestamp
	private Timestamp boardUpdateTime;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "board", cascade = CascadeType.REMOVE)
	private List<Comment> comments;
	

}
