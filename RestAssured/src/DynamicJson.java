import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.ReusableMethods;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().header("Content-Type", "application/json")
		.body(payload.AddBook(isbn, aisle))
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
	
	@DataProvider(name="BooksData")
	public Object[] getData()
	{
		return new Object[][] {{"namita","1990"}, {"minesh","1977"}, {"sahil","1993"}};
	}
	

}
