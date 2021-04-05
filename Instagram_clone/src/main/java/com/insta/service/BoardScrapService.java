package com.insta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.domain.BoardDTO;
import com.insta.domain.BoardScrapDTO;
import com.insta.mapper.BoardScrapMapper;

@Service
public class BoardScrapService {
	
	@Autowired
	private BoardScrapMapper boardScrapMapper;
	
	public int insertScrap(String userId, int boardNo) {
		return boardScrapMapper.insertScrap(userId,boardNo);
	}
	
	public int deleteScrap(String userId, int boardNo) {
		return boardScrapMapper.deleteScrap(userId,boardNo);
	}
	
	public int isScrap(BoardScrapDTO scrap) {
		return boardScrapMapper.isScrap(scrap);
	}
	
	public List<BoardDTO> scrapList(String userId){
		return boardScrapMapper.scrapList(userId);
	}
}
