package com.evstation.charge.board.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

}
