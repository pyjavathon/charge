package com.evstation.charge.board.service;

import com.evstation.charge.board.dto.CommentRequestDto;

public interface CommentService {
	
	public Long addComment(Long id, CommentRequestDto dto);

}
