import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.akash.model.Login;
import com.akash.model.Note;
import com.akash.model.User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestUserController {
	private static User signup1, signup2, signup3, user;
	private static Login login1, login2, login3;
	private static Note search;
	private static Logger log = Logger.getLogger(TestUserController.class);

	@BeforeClass
	public static void SetUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/FundooNote";
		log.warn(" SetUp() ");

		signup1 = new User();
		signup1.setUser_name("kumar");
		signup1.setUser_email("akashkumarsingh57.nitmz@gmail.com");
		signup1.setUser_address("bridgelabz");
		signup1.setUser_password("12345678");

		signup2 = new User();
		signup2.setUser_name(" ");
		signup2.setUser_email(" ");
		signup2.setUser_address(" ");
		signup2.setUser_password(" ");

		signup3 = new User();
		signup3.setUser_name("test3");
		signup3.setUser_email("test");
		signup3.setUser_address("testAddress");
		signup3.setUser_password(" ");

		login1 = new Login();
		login1.setUser_email(" ");
		login1.setUser_password(" ");

		login2 = new Login();
		login2.setUser_email("s@gmail.com");
		login2.setUser_password("wrongtest");

		login3 = new Login();
		login3.setUser_email("akashkumarsingh57@gmail.com");
		login3.setUser_password("12345678");

		search = new Note();
		user = new User();
		user.setUser_id(138);
		search.setUser(user);
		search.setTitle("opration");
		search.setDescription("opration");

	}

	@Test
	@Ignore
	public void InsertUserWithoutAnyError() {
		log.warn("InsertUserWithoutAnyError()");
		System.out.println("hskjshndfjksdf");
		Response resp = RestAssured.given().contentType("application/json").body(signup1).when().post("saveUser");
		log.warn(resp.asString());
		resp.then().statusCode(200);

	}

	@Test
	@Ignore
	public void InsertUserWithNullError() {
		log.warn("InsertUserWithNullError()");
		Response resp = RestAssured.given().body(signup2).contentType("application/json").when().post("saveUser");
		System.out.println("sdkfhjsldfsd" + resp.asString());
		resp.then().statusCode(400);

	}

	@Test
	@Ignore
	public void InsertUserWithIncorrectEmail() {
		log.warn("InsertUserWithIncorrectEmail()");
		Response resp = RestAssured.given().body(signup3).contentType("application/json").when().post("saveUser");
		log.warn(resp.asString());
		resp.then().statusCode(400);

	}

	@Test
	@Ignore
	public void InsertNullValue() { // SaveLogin
		log.warn(" InsertNullValue() ");
		Response resp = RestAssured.given().contentType("application/json").body(login1).when().post("UserLogin");
		log.warn("new resp is " + resp.asString());
		resp.then().statusCode(400);
	}

	@Test
	@Ignore
	public void InsertWrongData() {
		log.warn(" InsertWrongData()");
		Response resp = RestAssured.given().contentType("application/json").body(login2).when().post("UserLogin");
		log.warn("wrong data resp is " + resp.asString());
		resp.then().statusCode(200);
	}

	@Test
	@Ignore
	public void InsertCorrectData() {
		log.warn("InsertCorrectData()");
		Response resp = RestAssured.given().contentType("application/json").body(login3).when().post("UserLogin");
		String token = resp.getHeader("jwt");
		log.warn("token is >>" + token);
		log.warn("response is :" + resp.asString());
		resp.then().statusCode(200);

	}

	@Test
	@Ignore
	public void searchNote() {
		log.warn("searchNote()");
		Response resp = RestAssured.given().contentType("application/json").body(search).when().post("searchNote");
		log.warn("response is :" + resp.asString());
		resp.then().statusCode(200);

	}

}
