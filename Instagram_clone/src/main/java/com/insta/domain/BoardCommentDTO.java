package com.insta.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCommentDTO {

	private int comNo;
	private int boardNo;
	private int replyNo;
	private String userId;
	private String comComment;
	private Date comPostdate;
	
	public BoardCommentDTO(int boardNo, int replyNo, String userId, String comComment) {
		this.boardNo = boardNo;
		this.replyNo = replyNo;
		this.userId = userId;
		this.comComment = comComment;
	}
}
