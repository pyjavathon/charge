package com.evstation.charge.board.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.evstation.charge.board.dto.BoardResponseDto;
import com.evstation.charge.board.dto.CommentResponseDto;
import com.evstation.charge.board.entity.Board;
import com.evstation.charge.board.entity.Comment;
import com.evstation.charge.board.repository.BoardRepository;
import com.evstation.charge.board.repository.CommentRepository;
import com.evstation.charge.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepo;
	private final CommentRepository commentRepo;
	
	
	@Override
	public BoardResponseDto findById(Long boardId) {
		Board board = boardRepo.findById(boardId).orElseThrow(()->new EntityNotFoundException("없음"));
		BoardResponseDto dto = new BoardResponseDto(board);
		return dto;
	}
	
	

}
