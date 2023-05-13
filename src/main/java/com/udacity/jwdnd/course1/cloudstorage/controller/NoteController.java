package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class NoteController {
	private NotesService cs;
	private UserService us;

	public NoteController(NotesService cs, UserService us) {
		this.cs = cs;
		this.us = us;
	}

	@PostMapping("home/note")
	public String notet(@ModelAttribute Notes notes, RedirectAttributes redirectAttributes,
			Authentication authentication) {
		String err = null;
		String ok = null;
		Integer id = null;
			Integer userId = us.getUser(authentication.getName()).getUserId();
			notes.setUserid(userId);
			id = notes.getNoteid();
			if (id == null) {
				Integer ra = cs.inNote(notes);
				if (ra < 0) {
					err = "error";
				} else {
					ok = "sucess";
					id = cs.getLaNo();
				}
			} else {
				Integer ru = cs.upNote(notes);
				if (ru < 0) {
					err = "error";
				} else {
					ok = "sucess";
				}
			}

			if (cs.deNote(id) > 0) {
				redirectAttributes.addAttribute("noteOk", true);
				redirectAttributes.addAttribute("noteMsg", "success");
			} else {
				redirectAttributes.addAttribute("noteNotOk", true);
				redirectAttributes.addAttribute("noteMsg", "fail");
			}
		return ("redirect:/home");
	}

	@GetMapping("/home/note/delete/{noteId}")
	public String deletej(@PathVariable("noteId") Integer id, RedirectAttributes redirectAttributes) {
		if (cs.deNote(id) > 0) {
			redirectAttributes.addAttribute("noteOk", true);
			redirectAttributes.addAttribute("noteOkMsg", "success");
		} else {
			redirectAttributes.addAttribute("noteNotOk", true);
			redirectAttributes.addAttribute("noteNotOkMsg", "fail");
		}
		return ("redirect:/home");
	}

}
