package com.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smart.entities.User;

@Controller
public class HomeController {

	@GetMapping("/")
	public String homeView(Model model) {

		model.addAttribute("title", "Home-smart contact manager");
		return "home";
	}

	@GetMapping("/about")
	public String aboutUsPage(Model model) {

		model.addAttribute("title", "About us-smart contact manager");
		return "aboutUs";
	}

	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("title", "Sign Up-smart contact manager");
		model.addAttribute("user", new User());
		return "sign_up";
	}

	@GetMapping("/signIn")
	public String userLogin(Model model) {

		model.addAttribute("title", "LogIn-smart contact manager");
		return "login";
	}

	@GetMapping("/login-fail")
	public String userLoginFail(Model model) {

		model.addAttribute("title", "Error-smart contact manager");
		return "login_fail";
	}

}
