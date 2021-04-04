package com.insta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.domain.BoardCommentDTO;
import com.insta.domain.BoardDTO;
import com.insta.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	public boolean write(BoardDTO board) {
		return boardMapper.write(board);
	}
	
	public List<BoardDTO> list(){
		return boardMapper.list();
	}
	
	public BoardDTO detail(int boardNo) {
		return boardMapper.detail(boardNo);
	}
	
	public boolean writeComment(BoardCommentDTO comment) {
		return boardMapper.writeComment(comment);
	}
	
	public List<BoardCommentDTO> commentList(int boardNo){
		return boardMapper.commentList(boardNo);
	}
	
	public List<BoardCommentDTO> commentListOne(int boardNo){
		return boardMapper.commentListOne(boardNo);
	}
	
	public List<BoardDTO> myList(String userId){
		return boardMapper.myList(userId);
	}
	
	public int myListCount(String userId) {
		return boardMapper.myListCount(userId);
	}
	
	public List<BoardDTO> searchBoardPosts(String keyword) {
		return boardMapper.searchBoardPosts(keyword);
	}

}
