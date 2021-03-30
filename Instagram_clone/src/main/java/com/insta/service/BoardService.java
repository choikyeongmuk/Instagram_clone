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
}
