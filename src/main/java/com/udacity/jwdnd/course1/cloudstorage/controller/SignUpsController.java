package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignUpsController {
	private UserService us;

	public SignUpsController(UserService us) {
		this.us = us;
	}

	@GetMapping
	public String showPage(@ModelAttribute("User") User u, Model model) {
		return "signup";
	}

	@PostMapping
	public String signUp(@ModelAttribute("User") User u, Model model, RedirectAttributes redirectAttributes) {
		String err = null;
		if (!us.isOK(u.getUserName()))
			err = "error";
		if (err == null) {
			int ra = us.inUser(u);
			if (ra < 0) err = "error";
		}
		if (err == null) {
			redirectAttributes.addAttribute("isSuccess", true);
			redirectAttributes.addAttribute("signupMsg", "success");
			return "redirect:/login";
		} else {
			model.addAttribute("NOTOK", true);
			model.addAttribute("msg", err);
		}
		return "signup";
	}
}
