package com.insta.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.insta.domain.BoardLikeDTO;

@Repository("BoardLikeMapper")
public interface BoardLikeMapper {
   
   int insertLike(BoardLikeDTO params);
   int deleteLike(BoardLikeDTO params);
   int updateLike(BoardLikeDTO params);
   
   //전체 수 가져오기
   int getLikeCount(BoardLikeDTO params);
   int getMyLike(BoardLikeDTO params);
   List<BoardLikeDTO> selectLikeList(BoardLikeDTO params);
}