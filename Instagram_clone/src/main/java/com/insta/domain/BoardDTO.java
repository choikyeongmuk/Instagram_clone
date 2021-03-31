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
	private int likeCount;
	private int scrapCount;

	
	public BoardDTO(int boardNo, String userId, String content, String image) {
		super();
		this.boardNo = boardNo;
		this.userId = userId;
		this.content = content;
		this.image = image;
	}
	
}
