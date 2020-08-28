package testing;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.ReusableMethods;
import Files.payload;

public class basics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println("Hello World");

		// Add a new record POST API
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		RestAssured.useRelaxedHTTPSValidation();
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response()
				.asString();

		System.out.println(response);
		JsonPath json = new JsonPath(response);
		String placeId = json.getString("place_id");
		System.out.println(placeId);

		String newAddress = "Summer Walk, Africa";

		// Update a record PUT API
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("maps/api/place/update/json").then().assertThat().statusCode(200).log().all()
				.body("msg", equalTo("Address successfully updated"));

		// Get the response GET API

		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();


          JsonPath jsonGetResponse = ReusableMethods.rawToJson(getPlaceResponse);
		  String actualAddress =jsonGetResponse.getString("address");
		  System.out.println(actualAddress);
		  
		  
		  //TestNG Assertions
		  Assert.assertEquals(actualAddress, newAddress);
		 
	    

	}

}
