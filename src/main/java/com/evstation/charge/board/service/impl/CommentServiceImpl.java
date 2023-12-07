package com.evstation.charge.board.service.impl;

import org.springframework.stereotype.Service;

import com.evstation.charge.board.dto.CommentRequestDto;
import com.evstation.charge.board.entity.Board;
import com.evstation.charge.board.entity.Comment;
import com.evstation.charge.board.repository.BoardRepository;
import com.evstation.charge.board.repository.CommentRepository;
import com.evstation.charge.board.service.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	private final BoardRepository boardRepo;
	private final CommentRepository comRepo;
	@Override
	public Long addComment(Long id, CommentRequestDto dto) {
		Board board = boardRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다."+ id));
		
		dto.setBoard(board);
		
		Comment com = dto.toEntity();
		comRepo.save(com);
		return dto.getCommentId();
	}
	@Override
	public Long delComment(Long commentId) {
		
		comRepo.deleteById(commentId);
		return commentId;
	}
	


}
