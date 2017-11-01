import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import io.restassured.RestAssured;

public class TestGmailLoginController {

	private static Logger log = Logger.getLogger(TestGmailLoginController.class);

	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/FundooNote";

		log.warn("setUp()");

	}

	@Test
	@Ignore
	public void GmailLogin() {
		log.warn(" InsertNullValue() ");
		RestAssured.given().post("GmailLogin");

	}

}
