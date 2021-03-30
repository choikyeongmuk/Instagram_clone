package com.insta.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.insta.domain.BoardDTO;

@Repository("BoardMapper")
public interface BoardMapper {
	
	public boolean write(BoardDTO board);
	
	public List<BoardDTO> list(BoardDTO board);
}
