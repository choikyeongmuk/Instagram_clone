package com.insta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.domain.BoardLikeDTO;
import com.insta.mapper.BoardLikeMapper;

@Service
public class BoardLikeService {
   
   @Autowired
   private BoardLikeMapper boardLikeMapper;

   public int insertLike(BoardLikeDTO params) {
      boardLikeMapper.insertLike(params);
      return boardLikeMapper.updateLike(params);
   }
   public int deleteLike(BoardLikeDTO params) {
      boardLikeMapper.deleteLike(params);
      return boardLikeMapper.updateLike(params);
   }
   public int updateLike(BoardLikeDTO params) {
      return boardLikeMapper.updateLike(params);
   }
   
   //전체 수 가져오기
   public int getLikeCount(BoardLikeDTO params) {
      return boardLikeMapper.getLikeCount(params);
   }
   public int getMyLike(BoardLikeDTO params) {
      return boardLikeMapper.getMyLike(params);
   }
   public List<BoardLikeDTO> selectLikeList(BoardLikeDTO params){
      return boardLikeMapper.selectLikeList(params);
   }
   
}