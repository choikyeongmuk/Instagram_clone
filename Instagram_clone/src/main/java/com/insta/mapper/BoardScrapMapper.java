package com.insta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.insta.domain.BoardDTO;
import com.insta.domain.BoardScrapDTO;

@Repository("BoardScrapMapper")
public interface BoardScrapMapper {
	
	public int insertScrap(@Param("userId") String userId, @Param("boardNo")int boardNo);
	public int deleteScrap(@Param("userId")String userId, @Param("boardNo")int boardNo);
	
	public int isScrap(BoardScrapDTO scrap);
	public List<BoardDTO> scrapList(@Param("userId") String userId);
}
