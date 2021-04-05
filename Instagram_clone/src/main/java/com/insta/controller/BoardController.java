package com.insta.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.basic.BasicMenuBarUI;

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
import com.insta.domain.BoardScrapDTO;
import com.insta.domain.UserDTO;
import com.insta.service.BoardLikeService;
import com.insta.service.BoardScrapService;
import com.insta.service.BoardService;
import com.insta.service.MemberService;
import com.insta.util.DeduplicationUtils;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardLikeService boardLikeService;
	
	@Autowired
	private BoardScrapService boardScrapService;
	
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
		
		List<BoardCommentDTO> commentListOne = new ArrayList<BoardCommentDTO>();
		for(int i=0; i<boardList.size(); i++) {
			commentListOne = boardService.commentListOne(boardList.get(i).getBoardNo());
		}
		
		List<BoardCommentDTO> commentListOnes = DeduplicationUtils.deduplication(commentListOne, BoardCommentDTO:: getBoardNo);
		for(int i=0; i<commentListOnes.size();i++) {
			System.out.println(commentListOnes.get(i).getComComment());
		}
		
		model.addAttribute("commentList",commentListOnes);
		
		List<UserDTO> userInfo= memberService.userAllInfo();
		model.addAttribute("userList",userInfo);
		
		model.addAttribute("userInfo", userInfo);
		return "index";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(@RequestParam(value = "boardNo", required = false) int boardNo, 
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
        
        BoardScrapDTO scrap = new BoardScrapDTO();
        scrap.setBoardNo(boardNo);
        scrap.setUserId(userId);
        int isScrap = boardScrapService.isScrap(scrap);
        model.addAttribute("isScrap", isScrap);
        
		return "detail";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "boardNo", required = false) int boardNo, Model model) {
		BoardDTO board = boardService.detail(boardNo);
		model.addAttribute("board", board);
		
		return "post-edit";
	}

	
	@RequestMapping(value = "/edit", method = {RequestMethod.POST, RequestMethod.PATCH})
	public String edit(HttpServletRequest req,@RequestParam("file") MultipartFile file,
						@RequestParam(value = "boardNo", required = false) int boardNo,
						@RequestParam("content")String content,
						Model model
						) throws IllegalStateException, IOException {
		
		String PATH = req.getServletContext().getRealPath("/upload/");
		if (!file.getOriginalFilename().isEmpty()) {
			file.transferTo(new File(PATH + file.getOriginalFilename()));
		}
		boardService.edit(content,file.getOriginalFilename(),boardNo);
		
		return "redirect:detail?boardNo="+boardNo;
	}
	
	@RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String delete(@RequestParam(value = "boardNo", required = false) int boardNo) {
		
		boardService.delete(boardNo);
		
		return "redirect:index";
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
    
    @ResponseBody
    @RequestMapping(value = "/scrap", method = {RequestMethod.POST,RequestMethod.GET}, produces = "application/json")
    public int scrap(HttpServletRequest req,@RequestParam("boardNo")int boardNo) {
    	String userId = (String) req.getSession().getAttribute("userId");
    	
    	BoardScrapDTO scrap = new BoardScrapDTO();
    	scrap.setBoardNo(boardNo);
    	scrap.setUserId(userId);
    	
    	int isScrap = boardScrapService.isScrap(scrap);
    	
        if(isScrap >= 1) {
        	boardScrapService.deleteScrap(userId,boardNo);
        	isScrap=0;
        } else {
        	boardScrapService.insertScrap(userId,boardNo);
        	isScrap=1;
        }
    	
    	return isScrap;
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
