package com.evstation.charge.board.dto;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.evstation.charge.board.entity.Board;
import com.evstation.charge.board.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {

	Long commentId;
	String comment;
	@CreationTimestamp
	private Timestamp commentCreateTime;
	@UpdateTimestamp
	private Timestamp commentUpdateTime;
	private String commentWriter;
	private Long boardId;
	
	public CommentResponseDto(Comment com) {
		this.commentId = com.getCommentId();
		this.comment = com.getComment();
		this.commentWriter = com.getCommentWriter();
		this.commentCreateTime = com.getCommentCreateTime();
		this.commentUpdateTime = com.getCommentUpdateTime();
		this.boardId = com.getBoard().getBoardId();
				
	}
}
