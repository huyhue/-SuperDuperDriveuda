package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
	private CredentialService cs;
	private UserService us;
	private AuthenticationService auth;

	@Autowired
	public CredentialController(CredentialService cs, UserService us,
			AuthenticationService auth) {
		this.cs = cs;
		this.us = us;
		this.auth = auth;
	}

	@PostMapping("home/credentials")
	public String createCredential(@ModelAttribute Credentials credential, RedirectAttributes redirectAttributes) {
		String err = null;
		String user1 = auth.getUserName();
		int id = credential.getCredentialId();
		if (user1 == null)
			err = "error";
		if (err == null) {
			User user = us.getUser(user1);
			if (user != null) credential.setUserId(user.getUserId());
			else err = "error";
		}
		if (err == null) {
			if (id == 0) {
				cs.encryptPassword(credential);
				int ra = cs.crCre(credential);
				if (ra < 0) {
					err = "error";
				} else {
					id = cs.getLastCre();
				}
			} else {
				cs.updateCreKey(credential);
				cs.encryptPassword(credential);
				int ru = cs.upCre(credential);
				if (ru < 0) err = "error";
			}
		}
		if (err == null) {
			redirectAttributes.addAttribute("TR", true);
			redirectAttributes.addAttribute("METR", "Success");
		} else {
			redirectAttributes.addAttribute("FA", true);
			redirectAttributes.addAttribute("FATR", "Fail");
		}

		return ("redirect:/home");
	}

	@GetMapping("/home/credentials/delete/{credentialId}")
	public String deCrel(@PathVariable("credentialId") Integer id,
			RedirectAttributes redirectAttributes) {
		int rd = cs.deCrel(id);
		if (rd > 0) {
			redirectAttributes.addAttribute("TR", true);
			redirectAttributes.addAttribute("METR", "Success");
		} else {
			redirectAttributes.addAttribute("FA", true);
			redirectAttributes.addAttribute("FATR", "Fail");
		}
		return ("redirect:/home");
	}
}