package com.collegeproject.collegeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignupController {
	@RequestMapping("/signup")
	public String getSignup() {
		return "signup";
	}

}
