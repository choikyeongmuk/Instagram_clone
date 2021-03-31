package com.insta.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.insta.domain.BoardCommentDTO;
import com.insta.domain.BoardDTO;
import com.insta.domain.BoardLikeDTO;
import com.insta.service.BoardLikeService;
import com.insta.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardLikeService boardLikeService;
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(HttpServletRequest req,@RequestParam("file") MultipartFile file,
						@RequestParam("content")String content
						) throws IllegalStateException, IOException {
		String PATH = req.getServletContext().getRealPath("/upload/");
		if (!file.getOriginalFilename().isEmpty()) {
			file.transferTo(new File(PATH + file.getOriginalFilename()));
		}
		String userId= (String) req.getSession().getAttribute("userId");
		boardService.write(new BoardDTO(0,userId,content,file.getOriginalFilename()));
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String list(Model model) {
		List<BoardDTO> boardList = boardService.list();
		model.addAttribute("boardList", boardList);
		
		return "index";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(@ModelAttribute("board")BoardDTO boards, @RequestParam(value = "boardNo", required = false) int boardNo, 
						Model model, HttpServletRequest req) {
		BoardDTO board = boardService.detail(boardNo);
		model.addAttribute("board", board);
		
		List<BoardCommentDTO> commentList = boardService.commentList(boardNo);
		model.addAttribute("commentList",commentList);
		
		String userId = (String)req.getSession().getAttribute("userId");
		BoardLikeDTO boardLikeDTO = new BoardLikeDTO();
	      
        boardLikeDTO.setBoardNo(boardNo);
        boardLikeDTO.setUserId(userId);

        int boardlike = boardLikeService.getMyLike(boardLikeDTO);
        System.out.println(boardlike);

        model.addAttribute("heart",boardlike);
        
		return "detail";
	}


    @ResponseBody
    @RequestMapping(value = "/heart", method = RequestMethod.POST, produces = "application/json")
    public int heart(HttpServletRequest req,@RequestParam("boardNo")int boardNo) throws Exception {

       int heart = Integer.parseInt(req.getParameter("heart"));
       //int boardNo = Integer.parseInt(req.getParameter("boardNo"));
       String userid = (String)req.getSession().getAttribute("userId");

       BoardLikeDTO boardLikeDTO = new BoardLikeDTO();

       boardLikeDTO.setBoardNo(boardNo);
       boardLikeDTO.setUserId(userid);

       System.out.println(heart);

       if(heart >= 1) {
          boardLikeService.deleteLike(boardLikeDTO);
          heart=0;
       } else {
          boardLikeService.insertLike(boardLikeDTO);
          heart=1;
       }

      return heart;
   }
    @ResponseBody
    @RequestMapping(value = "/heartAction", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public String heartAction(HttpServletRequest req,@RequestParam("boardNo")int boardNo) throws Exception {

        int heart = Integer.parseInt(req.getParameter("heart"));
        //int boardNo = Integer.parseInt(req.getParameter("boardNo"));
        String userid = (String)req.getSession().getAttribute("userId");

        BoardLikeDTO boardLikeDTO = new BoardLikeDTO();

        boardLikeDTO.setBoardNo(boardNo);
        boardLikeDTO.setUserId(userid);

        System.out.println(heart);

        if(heart >= 1) {
           boardLikeService.deleteLike(boardLikeDTO);
           heart=0;
        } else {
           boardLikeService.insertLike(boardLikeDTO);
           heart=1;
        }

       return "redirect:index";
    }
	
	@RequestMapping(value = "/writeComment", method = RequestMethod.POST)
	@ResponseBody
	public String writeComment(HttpServletRequest req,@RequestParam("boardNo")int boardNo,
								@RequestParam("replyNo")int replyNo,
								@RequestParam("comComment")String comComment) {
		
		String userId= (String) req.getSession().getAttribute("userId");
		boardService.writeComment(new BoardCommentDTO(boardNo,replyNo,userId,comComment));
		
		return "redirect:detail?boardNo="+boardNo;
	}
}
