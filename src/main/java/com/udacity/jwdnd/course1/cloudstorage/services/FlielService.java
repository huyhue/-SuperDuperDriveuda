package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FlieMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Flies;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlielService {

	private FlieMapper file;

	public FlielService(FlieMapper file) {
		this.file = file;
	}

	public Integer inFile(Flies f) {
		return file.inFile(f);
	}

	public List<Flies> getFUser(Integer userId) {
		return file.getallF(userId);
	}

	public Integer deFile(String name) {
		return file.deFile(name);
	}

	public boolean isFileDuplicate(Integer id, String name) {
		List<String> liss = file.getFUser(id);
		return liss.contains(name);
	}

	public Flies getFileUser(Integer id, String name) {
		return file.getFfUser(id, name);
	}
}
