import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.akash.model.Note;
import com.akash.model.User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestNoteController {
	private static Note note1, note2, note3, note4, note5, note6, restore, archive, unarchive, pin, unpin, search,
			deleteFromElastic, sharenote;
	private static Logger log = Logger.getLogger(TestNoteController.class);

	@BeforeClass
	public static void SetUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/FundooNote";

		note1 = new Note();
		User user = new User();
		user.setUser_id(138);
		note1.setUser(user);
		note1.setTitle("demo test");
		note1.setDescription("Crud opration for note application test");
		note1.setDate("31/10/2017");

		note2 = new Note();
		User user2 = new User();
		user2.setUser_id(177);
		note2.setUser(user2);

		note3 = new Note();
		note3.setId(177);

		note4 = new Note();
		note4.setId(18);
		note4.setTitle("Demo check");
		note4.setDescription("Crud Updated opration for note application");
		note4.setDate("9/10/2017");

		note5 = new Note();
		note5.setId(177);

		note6 = new Note();
		note6.setId(4);
		note6.setTrash(true);

		restore = new Note();
		restore.setId(4);
		restore.setTrash(false);

		archive = new Note();
		archive.setId(4);
		archive.setArchive(true);

		unarchive = new Note();
		unarchive.setId(4);
		unarchive.setArchive(false);

		pin = new Note();
		pin.setId(4);
		pin.setPin(true);

		unpin = new Note();
		unpin.setId(4);
		unpin.setPin(false);

		search = new Note();
		User searchuser = new User();
		searchuser.setUser_id(138);
		search.setUser(searchuser);

		deleteFromElastic = new Note();
		deleteFromElastic.setId(59);

		sharenote = new Note();
		sharenote.setId(66);
		User user_info = new User();
		user_info.setUser_id(120);
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(177);
		arrayList.add(138);
		user_info.setShare_user_id(arrayList);
		sharenote.setUser(user_info);

	}

	@Test
	@Ignore
	public void insertNoteWithoutError() {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNzciLCJpYXQiOjIwMCwic3ViIjoiSnd0VG9rZW4iLCJpc3MiOiJGdW5kb29ub3RlIiwiZXhwIjoxNTA5Mzc1Nzg5fQ.udPXi6gOYiUeIsnmxgQRD8bbyflX2KvUgctZwZQEoC4";
		Response resp = RestAssured.given().header("jwtToken", jwt).contentType("application/json").body(note1).when()
				.post("addNote");
		log.warn("check note resp >>" + resp.asString());
		resp.then().statusCode(200);

	}

	@Test
	public void selectNoteByUserId() {
		Response resp = RestAssured.given().contentType("application/json").body(note2).when()
				.post("selectNoteByUserId");
		log.warn("check select resp>>" + resp.asString());
		resp.then().statusCode(200);
	}

	@Test
	@Ignore
	public void SelectNoteByNoteId() {
		Response response = RestAssured.given().contentType("application/json").body(note3).when()
				.post("SelectNoteByNoteId");
		log.warn("selectNoteByUserId responce :" + response.asString());
		response.then().statusCode(200);
	}

	@Test
	@Ignore
	public void SelectAllNote() {
		RestAssured.given().contentType("application/json").when().body(deleteFromElastic)
				.post("deleteDataFromElastic");
	}

	@Test
	@Ignore
	public void update() {
		Response response = RestAssured.given().contentType("application/json").body(note4).when().post("updateNote");
		log.warn("update responce :" + response.asString());
		response.then().statusCode(200);
	}

	@Test
	@Ignore
	public void deleteForeverByNoteId() {
		Response response = RestAssured.given().contentType("application/json").body(note5).when()
				.post("deleteForeverByNoteId");
		log.warn("deleteForeverByNoteId responce :" + response.asString());
		response.then().statusCode(200);
	}

	@Test
	@Ignore
	public void testAsync() {
		log.warn(">>>");

		RestAssured.given().contentType("application/json").when().post("forgot");

	}

	@Test
	@Ignore
	public void deleteNote() {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDciLCJpYXQiOjIwMCwic3ViIjoiSnd0VG9rZW4iLCJpc3MiOiJGdW5kb29ub3RlIiwiZXhwIjoxNTA3Nzg1MjY5fQ.bMrHh65uJJLG_vMh-Lv5emwaH0_cQ5I1E3pBsuxJ1AY";
		log.warn("in side trash()");
		Response response = RestAssured.given().header("jwtToken", jwt).contentType("application/json").body(note6)
				.when().post("deleteNote");
		log.warn("trashNote >>" + response.asString());
		response.then().statusCode(200);
	}

	@Test
	@Ignore
	public void restorefromTrash() {
		log.warn("inside restore from trash");
		Response response = RestAssured.given().contentType("application/json").body(restore).when()
				.post("restorefromtrash");
		log.warn("restore from trash >>" + response.asString());
		response.then().statusCode(200);

	}

	@Test
	@Ignore
	public void archive() {
		log.warn("inside archive()");
		Response response = RestAssured.given().contentType("application/json").body(archive).when().post("archive");
		log.warn("archive details >>" + response.asString());
		response.then().statusCode(200);
	}

	@Test
	@Ignore
	public void unarchive() {
		log.warn("inside unarchive()");
		Response response = RestAssured.given().contentType("application/json").body(unarchive).when()
				.post("unarchive");
		log.warn("unarchive details >>" + response.asString());
		response.then().statusCode(200);
	}

	@Test
	@Ignore
	public void pinNote() {
		log.warn("inside pinNote()");
		Response response = RestAssured.given().contentType("application/json").body(pin).when().post("pinNote");
		log.warn("pinNote details >>" + response.asString());
		response.then().statusCode(200);
	}

	@Test
	@Ignore
	public void unpinNote() {
		log.warn("inside unpinNote()");
		Response response = RestAssured.given().contentType("application/json").body(unarchive).when()
				.post("unpinNote");
		log.warn("unpinNote details >>" + response.asString());
		response.then().statusCode(200);
	}

	@Test
	@Ignore
	public void shareNote() {
		log.warn("inside shareNote()");
		Response response = RestAssured.given().contentType("application/json").body(sharenote).when()
				.post("shareNote");
		log.warn("shareNote details :" + response.asString());
		response.then().statusCode(200);
	}

}
