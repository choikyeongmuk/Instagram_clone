package com.insta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String userEmail;
	private String userImage;
	
	public UserDTO(String userId, String userImage) {
		this.userId = userId;
		this.userImage = userImage;
	}
	
}
