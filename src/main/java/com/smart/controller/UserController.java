package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.StatusMessage;

@Controller
public class UserController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bResult,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {
			if (!agreement) {
				System.out.println("you have not agreed to the terms and conditions");
				throw new Exception("you have not agreed to the terms and conditions");
			}

			if (bResult.hasErrors()) {
				System.out.println("Error" + bResult.toString());
				model.addAttribute("user", user);
				return "sign_up";
			}

			user.setUserRole("ROLE_USER");
			user.setUserEnabled(true);
			user.setUserImageUrl("default.png");
			user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

			model.addAttribute("user", user);
			User result = this.userRepository.save(user);
			session.setAttribute("message", new StatusMessage("Successfully Registered!!", "alert-success"));
			System.out.println(result);
			return "sign_up";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message",
					new StatusMessage("Something went wrong!!\nPlease try again." + e.getMessage(), "alert-danger"));
			return "sign_up";
		}

	}
}
