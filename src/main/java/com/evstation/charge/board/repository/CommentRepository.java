package com.evstation.charge.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evstation.charge.board.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

}
