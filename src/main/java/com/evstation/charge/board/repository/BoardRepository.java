package com.evstation.charge.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evstation.charge.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{

	Board findByBoardId(Long boardId);
}
