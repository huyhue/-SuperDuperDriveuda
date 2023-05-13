package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Flies;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomessController {
	private CredentialService cs;
	private UserService us;
	private NotesService ns;	
	private FlielService fs;
	private EncryptionService encrypt;

	public HomessController(CredentialService cs, UserService us, NotesService ns,
			FlielService fs, EncryptionService encrypt) {
		this.cs = cs;
		this.us = us;
		this.ns = ns;
		this.fs = fs;
		this.encrypt = encrypt;
	}

	@GetMapping
	public String getHome(Model model, Authentication authentication) {
		Integer id = us.getUser(authentication.getName()).getUserId();
		List<Credentials> cList = cs.getCreUser(id);
		List<Notes> nlist = ns.getNoU(id);
		List<Flies> fList = fs.getFUser(id);
		model.addAttribute("encryptionService", encrypt);
		model.addAttribute("credentials", cList);
		model.addAttribute("noteslist", nlist);
		model.addAttribute("fileList", fList);
		return "home";
	}
}
