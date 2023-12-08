package com.evstation.charge.board.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.evstation.charge.board.dto.BoardResponseDto;
import com.evstation.charge.board.entity.Board;
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


	@Override
	public Page<BoardResponseDto> findList(Pageable pageable) {
		Page<Board> boards = boardRepo.findAll(pageable);
		List<BoardResponseDto> boardDTOs = new ArrayList<>();
		for(Board board : boards) {
			BoardResponseDto result = BoardResponseDto.builder()
													  .board(board)
													  .build();
			boardDTOs.add(result);
		}
		return new PageImpl<>(boardDTOs,pageable,boards.getTotalElements());
	}
	
	

}
