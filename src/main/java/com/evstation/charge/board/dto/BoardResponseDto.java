package com.evstation.charge.board.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.evstation.charge.board.entity.Board;
import com.evstation.charge.board.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Getter
public class BoardResponseDto {

	private Long boardId;
	
	private String boardType;
	
	private String boardNm;
	
	private String boardWriter;
	
	private String boardContent;
	
	private Timestamp boardCreateTime;
	
	private Timestamp boardUpdateTime;
	
	private List<CommentResponseDto> comments;
	@Builder
	public BoardResponseDto(Board board) {
		this.boardId = board.getBoardId();
		this.boardType = board.getBoardType();
		this.boardNm = board.getBoardNm();
		this.boardContent = board.getBoardContent();
		this.boardWriter = board.getBoardWriter();
		this.boardCreateTime = board.getBoardCreateTime();
		this.boardUpdateTime = board.getBoardUpdateTime();
		this.comments = board.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
	}


}
