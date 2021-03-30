package com.insta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutController {
	
	@GetMapping("/")
	public String main() {
		return "login";
	}
	
	@GetMapping("/header")
	public String header() {
		return "layout/header";
	}
	
	@GetMapping("/navigator")
	public String navigator() {
		return "layout/navigator";
	}
	
	@GetMapping("/footer")
	public String footer() {
		return "layout/footer";
	}
	
}
