package com.insta.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.insta.domain.UserDTO;
import com.insta.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	private PrintWriter out;
	
	
	@RequestMapping(value = "/signup" , method = RequestMethod.GET)
	public String signUp() {
		return "/signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(UserDTO user,HttpServletResponse response) throws IOException {
		try {
			memberService.signUp(user);
			return "/login";
		}catch(Exception e) {
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();
			out.println("<script>alert('중복된 아이디입니다.'); location.href = '/signup'</script>");
		}
		return "signup";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String userId,String userPwd,HttpServletRequest req,HttpServletResponse response) throws IOException {
		try {
			if(!memberService.login(userId, userPwd).equals("")) {
				HttpSession session = req.getSession();
				session.setAttribute("userId",userId);
				return "redirect:/index";
			}
		}catch(Exception e) {
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();
			out.println("<script>alert('아이디와 비밀번호가 일치하지 않습니다.'); location.href = '/login'</script>");
		}
		return "/login";
	}
	
	/*
	//아이디 중복확인 (gson - ClassNotFoundException ...)
	@RequestMapping(value = "/idCheck.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String idcheck(HttpServletRequest req) {
		
		JsonObject obj = new JsonObject();
		String userID = req.getParameter("userId");
		String msg;
		
		boolean flag = memberService.idCheck(userID);
		System.out.println(flag);
		
		
		if(flag) { 
			obj.addProperty("flag", "0");
			obj.addProperty("msg", "사용중인 아이디입니다.");
		}
		else {
			obj.addProperty("flag", "1");
			obj.addProperty("msg", "멋진 아이디네요!");
		}		
		return obj.toString();
	}
	*/
}
