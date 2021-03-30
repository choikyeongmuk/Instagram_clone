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
	private String comComent;
	private Date comPostdate;
	
}
