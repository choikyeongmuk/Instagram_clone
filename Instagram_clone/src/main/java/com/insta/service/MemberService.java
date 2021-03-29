package com.insta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.domain.UserDTO;
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
}
