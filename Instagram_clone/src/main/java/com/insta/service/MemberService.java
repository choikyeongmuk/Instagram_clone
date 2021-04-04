package com.insta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.domain.UserDTO;
import com.insta.domain.UserFollowDTO;
import com.insta.mapper.BoardMapper;
import com.insta.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	public boolean signUp(UserDTO user) {
		return memberMapper.signUp(user);
	};
	
	public boolean idCheck(String userId) {
		return memberMapper.idCheck(userId);
	}
	
	public String login(String userId, String userPwd) {
		return memberMapper.login(userId, userPwd);
	}
	
	public UserDTO userInfo(String userId){
	//public List<UserDTO> userInfo(String userId){
		return memberMapper.userInfo(userId);
	}
	
	public List<UserDTO> userAllInfo(){
		return memberMapper.userAllInfo();
	}
	
	public boolean profileImage(String userId, String userImage) {
		return memberMapper.profileImage(userId, userImage);
	}
	
	public int follow(String userId, String otherId) {
		return memberMapper.follow(userId, otherId);
	}
	
	public int unFollow(String userId, String otherId) {
		return memberMapper.unFollow(userId, otherId);
	}

	public String isFollow(String userId, String otherId) {
		return memberMapper.isFollow(userId, otherId);
	}
	
	public int followerCount(String userId) {
		return memberMapper.followerCount(userId);
	}
	
	public int followCount(String userId) {
		return memberMapper.followCount(userId);
	}
	
	
	public List<UserDTO> searchPosts(String keyword) {
		return memberMapper.searchPosts(keyword);
	}
}
