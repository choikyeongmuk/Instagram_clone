package com.insta.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.insta.domain.BoardDTO;
import com.insta.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(BoardDTO board,HttpServletRequest req,@RequestParam("file") MultipartFile file,
						@RequestParam("content")String content
						) throws IllegalStateException, IOException {
		String PATH = req.getServletContext().getRealPath("/upload");
		if (!file.getOriginalFilename().isEmpty()) {
			file.transferTo(new File(PATH + file.getOriginalFilename()));
		}
		String userId= (String) req.getSession().getAttribute("userId");
		System.out.println(userId);
		boardService.write(new BoardDTO(userId,content,file.getOriginalFilename()));
		return "index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String list(@ModelAttribute("board") BoardDTO board, Model model) {
		List<BoardDTO> boardList = boardService.list(board);
		model.addAttribute("boardList", boardList);
		
		return "index";
	}
}
