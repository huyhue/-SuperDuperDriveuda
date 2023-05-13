package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
	private NotesMapper no;

	public NotesService(NotesMapper no) {
		this.no = no;
	}

	public Integer inNote(Notes notes) {
		return no.inNote(notes);
	}

	public List<Notes> getNoU(Integer userId) {
		return no.getNoU(userId);
	}

	public Integer upNote(Notes notes) {
		return no.upNote(notes);
	}

	public Integer deNote(Integer noteid) {
		return no.deNote(noteid);
	}

	public Integer getLaNo() {
		return no.getLaNo();
	}

}
