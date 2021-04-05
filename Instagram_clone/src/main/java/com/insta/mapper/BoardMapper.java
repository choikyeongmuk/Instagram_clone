package com.insta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.insta.domain.BoardCommentDTO;
import com.insta.domain.BoardDTO;

@Repository("BoardMapper")
public interface BoardMapper {
	
	public boolean write(BoardDTO board);
	public boolean edit(@Param("content") String content,@Param("image") String image, @Param("boardNo") int boardNo);
	public boolean delete(@Param("boardNo") int boardNo);
	
	public List<BoardDTO> list();
	public BoardDTO detail(int boardNo); //상세 페이지
	
	public boolean writeComment(BoardCommentDTO comment);
	public List<BoardCommentDTO> commentList(int boardNo);
	public List<BoardCommentDTO> commentListOne(int boardNo);
	
	public List<BoardDTO> myList(String userId);
	public int myListCount(String userId);
	public List<BoardDTO> searchBoardPosts(String keyword);
}
