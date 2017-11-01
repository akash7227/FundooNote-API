package com.akash.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.akash.dao.UserDao;
import com.akash.model.Note;

@Service("collaboratorService")
public class CollaboratorService {

	@Autowired
	private UserService userService;

	public void shareNote(Note note) {
		System.out.println("in side service ");

		List listofuser = note.getUser().getShare_user_id();
		int note_id = note.getId();
		int user_id = note.getUser().getUser_id();

		Iterator<Integer> ltr = listofuser.listIterator();

		while (ltr.hasNext()) {
			System.out.println("inside has next");
			int shareuser_id = ltr.next();
			boolean check = userService.getUserById(shareuser_id);
			System.out.println("check is " + check);
			if (check == true) {
				System.out.println("check :");
				userService.addShareNote(user_id, note_id, shareuser_id);
			}

		}

	}

}
