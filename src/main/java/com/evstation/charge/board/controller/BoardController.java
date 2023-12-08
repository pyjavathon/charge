package com.evstation.charge.board.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.evstation.charge.board.dto.BoardResponseDto;
import com.evstation.charge.board.dto.CommentResponseDto;
import com.evstation.charge.board.entity.Board;
import com.evstation.charge.board.repository.BoardRepository;
import com.evstation.charge.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardRepository boardRepo;
	private final BoardService boardSer;

	
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
		BoardResponseDto dto = boardSer.findById(boardId);
		List<CommentResponseDto> comments = dto.getComments();
		if(comments != null && !comments.isEmpty()) m.addAttribute("comments", comments);
		m.addAttribute("board", dto);
		return "board/board-detail";
	}
	
	@GetMapping("/list")
	public String list(Model m,@PageableDefault(page = 0, size = 10, sort = "boardId",direction = Sort.Direction.DESC)Pageable pageable) {
		
		m.addAttribute("boardList", boardSer.findList(pageable));
		return "board/board-list";
	}
	
	@GetMapping("/{boardId}/edit")
	public String editForm(@PathVariable Long boardId, Model m) {
		Optional<Board> board = boardRepo.findById(boardId);
		m.addAttribute("board", board);
		return "board/board-edit";
	}
	
	@PostMapping("/{boardId}/edit")
	public String editBoard(@PathVariable Long boardId, @ModelAttribute Board reqBoard) {
		boardRepo.save(reqBoard);
		return "redirect:/board/{boardId}";
	}
	
	
	
}
