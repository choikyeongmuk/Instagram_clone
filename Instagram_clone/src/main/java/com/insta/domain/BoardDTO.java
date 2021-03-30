package com.insta.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
	
	private int boardNo;
	private String userId;
	private String content;
	private String image;
	private Date postDate;
	
	public BoardDTO(String userId, String content, String image) {
		this.userId = userId;
		this.content = content;
		this.image = image;
	}
}
