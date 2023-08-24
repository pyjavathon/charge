package com.evstation.charge.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.evstation.charge.board.entity.Board;
import com.evstation.charge.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardRepository boardRepo;

	
	@GetMapping("/add")
	public String addForm(Model m) {
		m.addAttribute("board", new Board());
		return "board/board-add";
	}
	
	@PostMapping("/add")
	public String addBoard(@ModelAttribute Board reqboard, RedirectAttributes redirectAttributes) {
		Board saveFb = boardRepo.save(reqboard);
		redirectAttributes.addAttribute("boardId", saveFb.getBoardId());
		return "redirect:/board/{boardId}";
	}
	
	@GetMapping("/{boardId}")
	public String detail(@PathVariable Long boardId,Model m) {
		Board board = boardRepo.findByBoardId(boardId);
		m.addAttribute("board", board);
		return "board/board-detail";
	}
	
	@GetMapping("/list")
	public String list(Model m) {
		List<Board> boards = boardRepo.findAll();
		m.addAttribute("boards", boards);
		return "board/board-list";
	}
	
	@GetMapping("/{boardId}/edit")
	public String editForm(@PathVariable Long boardId, Model m) {
		Board board = boardRepo.findByBoardId(boardId);
		m.addAttribute("board", board);
		return "board/board-edit";
	}
	
	@PostMapping("/{boardId}/edit")
	public String editBoard(@PathVariable Long boardId, @ModelAttribute Board reqBoard) {
		boardRepo.save(reqBoard);
		return "redirect:/board/{boardId}";
	}
	
	
}
