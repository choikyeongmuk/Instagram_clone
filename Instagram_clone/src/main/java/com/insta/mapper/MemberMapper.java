package com.insta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.insta.domain.UserDTO;

@Repository("MemberMapper")
public interface MemberMapper {
	
	public boolean signUp(UserDTO user);
	public boolean idCheck(String userId);
	
	public String login(@Param("userId")String userId, @Param("userPwd")String userPwd);
	
	public UserDTO userInfo(String userId);
	public List<UserDTO> userAllInfo();
	public boolean profileImage(@Param("userId")String userId,@Param("userImage") String userImage);
	
	public int follow(@Param("userId")String userId, @Param("otherId")String otherId);
	public int unFollow(@Param("userId")String userId, @Param("otherId")String otherId);
	public String isFollow(@Param("userId")String userId, @Param("otherId")String otherId);
	public int followerCount(String userId);
	public int followCount(String userId);
	
	
	public List<UserDTO> searchPosts(String keyword);
	
}
 