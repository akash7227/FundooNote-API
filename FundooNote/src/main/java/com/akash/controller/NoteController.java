package com.akash.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akash.json.Response;
import com.akash.model.Note;
import com.akash.service.CollaboratorService;
import com.akash.service.NoteService;

@Controller
public class NoteController {
	@Autowired
	NoteService noteService;

	@Autowired
	CollaboratorService collaboratorService;
	private static Logger log = Logger.getLogger(NoteController.class);

	@RequestMapping(value = "addNote", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> addNote(@RequestBody Note note) {
		Response resp;

		log.warn("inside addnote");
		log.warn("in side addnote>>>" + note);

		noteService.addNote(note);

		resp = new Response();
		resp.setMsg("insert note successfully");
		resp.setStatus(1);

		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "selectNoteByUserId", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> selectNoteByUserId(@RequestBody Note note) {
		Response resp;

		log.warn("inside select note");
		log.warn(note);

		List<Note> newnote = noteService.selectById(note);

		for (Note foundNewNote : newnote) {
			log.warn("found new note");
			log.warn(foundNewNote);
		}

		resp = new Response();
		resp.setStatus(1);
		resp.setMsg("select note successfully");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "SelectNoteByNoteId", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> SelectNoteByNoteId(@RequestBody Note note) {
		Response resp;

		note = noteService.selectNoteByNoteId(note);
		log.warn("note from note id >>" + note);

		resp = new Response();
		resp.setStatus(1);
		resp.setMsg("successfully select note by Note id");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "SelectAllNote", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> SelectAllNote(Note note) {
		Response resp;
		log.info("in side SelectAllNote");
		List<Note> allnote = noteService.selectAllNote();
		log.warn("note from note id >>" + allnote);

		resp = new Response();
		resp.setStatus(1);
		resp.setMsg("successfully select note by Note id");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "updateNote", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> updateNote(@RequestBody Note note) {
		Response resp;
		log.warn("inside updateNote");

		noteService.update(note);

		resp = new Response();
		resp.setStatus(1);
		resp.setMsg("successfully update Note ");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "deleteForeverByNoteId", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> deleteByNoteId(@RequestBody Note note) {
		Response resp;
		log.warn("inside deleteForeverByNoteId");

		noteService.deleteForeverByNoteId(note);

		resp = new Response();
		resp.setStatus(1);
		resp.setMsg("successfully delete Note ");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "deleteNote", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> trash(@RequestBody Note note) {
		Response resp;
		noteService.trash(note);
		resp = new Response();
		resp.setStatus(1);
		resp.setMsg("note is successfully trash");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "restorefromtrash", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> restorefromtrash(@RequestBody Note note) {
		Response resp;
		noteService.trash(note);
		resp = new Response();
		resp.setStatus(1);
		resp.setMsg("note is successfully restore from trash");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "archive", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> archive(@RequestBody Note note) {
		Response resp;

		noteService.archive(note);
		resp = new Response();
		resp.setMsg("note is successfully archive");
		resp.setStatus(1);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "unarchive", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> unarchive(@RequestBody Note note) {
		Response resp;

		noteService.archive(note);
		resp = new Response();
		resp.setMsg("note is successfully unarchive");
		resp.setStatus(1);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "pinNote", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> pinNote(@RequestBody Note note) {
		Response resp;

		noteService.pin(note);
		resp = new Response();
		resp.setMsg("note is successfully pin");
		resp.setStatus(1);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "unpinNote", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> unPinNote(@RequestBody Note note) {
		Response resp;

		noteService.pin(note);
		resp = new Response();
		resp.setMsg("note is successfully unpin");
		resp.setStatus(1);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "shareNote", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> shareNote(@RequestBody Note note) {
		Response resp;
		System.out.println("in side share note" + note);
		collaboratorService.shareNote(note);

		resp = new Response();
		resp.setMsg("note is successfully shared");
		resp.setStatus(1);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}
}
