package com.evstation.charge.board.service;

import org.springframework.stereotype.Service;

import com.evstation.charge.board.dto.BoardResponseDto;

@Service
public interface BoardService {

	BoardResponseDto findById(Long boardId);

}
