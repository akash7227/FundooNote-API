package com.akash.dao;

import java.util.List;

import com.akash.model.Note;

public interface NoteDao {
	void addNote(Note note);

	List<Note> selectById(Note note);

	void update(Note note);

	Note selectNoteByNoteId(Note note);

	int deleteForeverByNoteId(Note note);

	void trash(Note note);

	void archive(Note note);

	void pin(Note note);

	List<Note> selectAllNote();

}
