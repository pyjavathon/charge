package com.evstation.charge.board.controller;

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
	public String addBoard(@ModelAttribute Board fb, RedirectAttributes redirectAttributes) {
		Board saveFb = boardRepo.save(fb);
		redirectAttributes.addAttribute("boardId", saveFb.getBoardId());
		return "redirect:/board/{boardId}";
	}
	
	@GetMapping("/{boardId}")
	public String detail(@PathVariable long boardId,Model m) {
		Board board = boardRepo.findById(boardId);
		m.addAttribute("board", board);
		return "board/board-detail";
	}
}
