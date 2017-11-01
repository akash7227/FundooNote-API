package com.akash.dao;

import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.mappers.NoteMapper;
import com.akash.model.Note;

@Service("noteDao")
public class NoteDaoImp implements NoteDao {
	@Autowired
	NoteMapper noteMapper;
	private static Logger log = Logger.getLogger(NoteDaoImp.class);

	public void addNote(Note note) {
		// TODO Auto-generated method stub
		noteMapper.insertnote(note);

	}

	public List<Note> selectById(Note note) {
		// TODO Auto-generated method stub
		return noteMapper.selectById(note);

	}

	public void update(Note note) {
		// TODO Auto-generated method stub
		noteMapper.update(note);

	}

	public Note selectNoteByNoteId(Note note) {
		// TODO Auto-generated method stub
		return noteMapper.selectNoteByNoteId(note);
	}

	public int deleteForeverByNoteId(Note note) {
		int check = noteMapper.deleteForeverByNoteId(note);
		if (check == 1) {
			return 1;
		}
		return 0;

	}

	public void trash(Note note) {
		// TODO Auto-generated method stub
		noteMapper.trash(note);
	}

	public void archive(Note note) {
		// TODO Auto-generated method stub
		noteMapper.archive(note);
	}

	public void pin(Note note) {
		// TODO Auto-generated method stub
		noteMapper.pin(note);

	}

	public List<Note> selectAllNote() {
		// TODO Auto-generated method stub
		System.out.println("in note imp");
		List<Note> note = noteMapper.selectAllNote();
		return note;

	}

}
