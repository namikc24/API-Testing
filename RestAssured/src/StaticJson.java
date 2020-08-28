import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {
	
	@Test
	public void addBook() throws IOException
	{
		RestAssured.baseURI = "http://216.10.245.166";
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().header("Content-Type", "application/json")
		.body(GenerateStringFromResource("C:\\Users\\ChandaranN\\Desktop\\Addbookdetails.json"))
		//.body(payload.AddPlace())
		.when().post("/Library/Addbook.php")
		//.when().post("maps/api/place/add/json")
		.then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
		JsonPath json = new JsonPath(response);
		//String scope = json.getString("scope");
		String id = json.getString("ID");
		//System.out.println("Scope is " + scope);
		System.out.println("ID is " + id);
}
	
public static String GenerateStringFromResource(String path) throws IOException {
	return new String(Files.readAllBytes(Paths.get(path)));
}
	
}

