package com.evstation.charge.board.service;

import java.util.List;

import com.evstation.charge.board.dto.CommentRequestDto;
import com.evstation.charge.board.dto.CommentResponseDto;
import com.evstation.charge.board.entity.Comment;

public interface CommentService {
	
	public Long addComment(Long id, CommentRequestDto dto);

	public Long delComment(Long commentId);



}
