package com.evstation.charge.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.evstation.charge.board.dto.CommentRequestDto;
import com.evstation.charge.board.dto.CommentResponseDto;
import com.evstation.charge.board.entity.Comment;
import com.evstation.charge.board.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService comSer;

	@PostMapping("/{boardId}")
	public ResponseEntity<?> addComment(@PathVariable Long boardId, @RequestBody CommentRequestDto dto){
		return ResponseEntity.ok(comSer.addComment(boardId, dto));
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> delComment(@PathVariable Long commentId) {
		return ResponseEntity.ok(comSer.delComment(commentId));
	}
	
	/*
	 * @PutMapping("/{commentId}") public ResponseEntity<?>
	 * modifyComment(@PathVariable Long boardId, @RequestBody CommentRequestDto
	 * dto){ return ResponseEntity.ok(comSer.modifyComment(commentId)); }
	 */
}
