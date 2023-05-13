package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Flies;
import com.udacity.jwdnd.course1.cloudstorage.services.FlielService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;

@Controller
public class FlieController {
	private UserService us;
	private FlielService fs;

	public FlieController(UserService us, FlielService fs) {
		this.us = us;
		this.fs = fs;
	}

	@PostMapping("/home/fileupload")
	public String uploadg(@RequestParam("fileUpload") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		String err = null;
		final String ok = "success";
		byte[] fb = null;
		String fle = null;

		try {
			int id = us.getUser(authentication.getName()).getUserId();
			fle = multipartFile.getOriginalFilename();
			String contentType = multipartFile.getContentType();
			Long size = multipartFile.getSize();
			if (fle.length() == 0) {
				err = "error";
				throw (new Exception("Fail"));
			}
			if ((multipartFile.getSize() > 5242880)) {
				throw new MaxUploadSizeExceededException(multipartFile.getSize());
			}
			if (fs.isFileDuplicate(id, fle)) {
				err = "error";
				throw (new Exception("Duplicated"));
			}

			InputStream fis = multipartFile.getInputStream();
			fb = new byte[fis.available()];
			fis.read(fb);
			fis.close();

			Flies flies = new Flies(null, fle, contentType, size.toString(), id, fb);
			fs.inFile(flies);
		} catch (MaxUploadSizeExceededException a) {
			a.printStackTrace();
		} catch (Exception a) {
			a.printStackTrace();
		}

		if (err == null) {
			redirectAttributes.addAttribute("opok", true);
			redirectAttributes.addAttribute("opmsg", ok + ": " + fle);
		} else {
			redirectAttributes.addAttribute("opnotok", true);
			redirectAttributes.addAttribute("opmsg", err + ": " + fle);
		}
		return ("redirect:/home");
	}

	@GetMapping("/home/file/delete/{filename}")
	public String deleteo(@PathVariable("filename") String fileName, Authentication authentication,
			RedirectAttributes redirectAttributes) {

		if (fs.deFile(fileName) < 0) {
			redirectAttributes.addAttribute("OK", true);
			redirectAttributes.addAttribute("OKme","sucess");
		} else {
			redirectAttributes.addAttribute("NOK", true);
			redirectAttributes.addAttribute("NOKme", "Fail");
		}
		return ("redirect:/home");
	}

	@GetMapping("home/files/download/{filename}")
	public ResponseEntity downl(@PathVariable("filename") String fileName, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		int id = us.getUser(authentication.getName()).getUserId();
		Flies of = null;
		try {
			of = fs.getFileUser(id, fileName);
		} catch (Exception a) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(of.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(of.getFileData());
	}
}
