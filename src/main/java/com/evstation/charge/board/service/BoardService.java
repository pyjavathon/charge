package com.evstation.charge.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.evstation.charge.board.dto.BoardResponseDto;
import com.evstation.charge.board.entity.Board;

@Service
public interface BoardService {

	BoardResponseDto findById(Long boardId);

	Page<BoardResponseDto> findList(Pageable pageable);

}
