package com.akash.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.dao.NoteDao;
import com.akash.model.Note;

@Service("noteService")
public class NoteService {
	@Autowired
	NoteDao noteDao;

	@Autowired
	ElasticSearchService elasticSearchService;

	@Transactional
	public void addNote(Note note) {
		noteDao.addNote(note);

	}

	public List<Note> selectById(Note note) {
		// TODO Auto-generated method stub
		return noteDao.selectById(note);
	}

	public Note selectNoteByNoteId(Note note) {
		// TODO Auto-generated method stub
		return noteDao.selectNoteByNoteId(note);
	}

	public void update(Note note) {
		// TODO Auto-generated method stub
		noteDao.update(note);

	}

	@Transactional
	public void deleteForeverByNoteId(Note note) {

		int check = noteDao.deleteForeverByNoteId(note);
		if (check == 1) {
			try {
				elasticSearchService.deleteDataFromElastic(note);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void trash(Note note) {
		// TODO Auto-generated method stub
		noteDao.trash(note);

	}

	public void archive(Note note) {
		// TODO Auto-generated method stub
		noteDao.archive(note);

	}

	public void pin(Note note) {
		// TODO Auto-generated method stub
		noteDao.pin(note);

	}

	public List<Note> selectAllNote() {
		System.out.println("check");
		List<Note> note = noteDao.selectAllNote();

		return note;
	}
	
	

}
