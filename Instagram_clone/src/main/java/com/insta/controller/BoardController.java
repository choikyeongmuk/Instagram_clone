package com.insta.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.insta.domain.BoardCommentDTO;
import com.insta.domain.BoardDTO;
import com.insta.domain.BoardLikeDTO;
import com.insta.domain.UserDTO;
import com.insta.service.BoardLikeService;
import com.insta.service.BoardService;
import com.insta.service.MemberService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardLikeService boardLikeService;
	
	@Autowired
	private MemberService memberService;
	
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
		
		List<BoardCommentDTO> commentList = new ArrayList<BoardCommentDTO>();
		for(int i=0; i<boardList.size(); i++) {
			commentList = boardService.commentListOne(boardList.get(i).getBoardNo());
		}
		
		for(int i=0; i<boardList.size();i++) {
			for(int j=i+1; j<commentList.size();j++) {
				if(commentList.get(i).getBoardNo() == commentList.get(j).getBoardNo())
					commentList.remove(i);
			}
		}
		
		model.addAttribute("commentList",commentList);
		
		
		List<UserDTO> userInfo= memberService.userAllInfo();
		model.addAttribute("userList",userInfo);
		
		model.addAttribute("userInfo", userInfo);
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
        model.addAttribute("heart",boardlike);
        
        BoardLikeDTO likeCount  = new BoardLikeDTO();
        likeCount.setBoardNo(boardNo);
        int boardlikeAll = boardLikeService.getLikeCount(likeCount);
        
        model.addAttribute("likeCount", boardlikeAll);
        
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

       //System.out.println(heart);

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

        //System.out.println(heart);

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
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(@RequestParam(value="keyword") String keyword, Model model) {
	    List<UserDTO> userDtoList = memberService.searchPosts(keyword);
	    List<BoardDTO> boardList = boardService.searchBoardPosts(keyword);
	    
	    model.addAttribute("boardList", boardList);
	    model.addAttribute("userList", userDtoList);

	    return "index";
	}
}
