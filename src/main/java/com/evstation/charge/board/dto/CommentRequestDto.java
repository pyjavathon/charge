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
public class CommentRequestDto {
	
	private Long commentId;
	private String comment;
	@CreationTimestamp
	private Timestamp commentCreateTime;
	@UpdateTimestamp
	private Timestamp commentUpdateTime;
	private String commentWriter;
	private Board board;

	/* Dto -> entity */
	public Comment toEntity() {
		Comment comments = Comment.builder()
				.commentId(commentId)
				.comment(comment)
				.commentCreateTime(commentCreateTime)
				.commentUpdateTime(commentUpdateTime)
				.commentWriter(commentWriter)
				.board(board)
				.build();
		return comments;
	}
}
