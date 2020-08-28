import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.Assert;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class Jira {

	
	public static void main(String[] args) 
	{
		RestAssured.baseURI = "http://localhost:8080/";
		int id = 10005;
		
		SessionFilter session = new SessionFilter();
		
		String response = given().header("Content-Type", "application/json").body("{ \"username\": \"namikc24\", \"password\": \"Mumtaz69\" }")
		.log().all().filter(session)
		.when().post("/rest/auth/1/session")
		.then().extract().response().asString();
		String expectedMessage = "Hi, how are you";
		String addCommentResponse = given().pathParam("id", id).log().all()
		.header("Content-Type", "application/json")
		.body("{\r\n" + 
				"    \"body\": \""+expectedMessage+"\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"        }\r\n" + 
				"        }").filter(session)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id");
		
		
		//Add Attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id", id)
		.header("Content-Type", "multipart/form-data")
		.multiPart("file", new File("jira.txt"))
		.when()
		.post("rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//Get Issue
		String issueDetails = given().filter(session)
				.queryParam("fields", "comments")
				.pathParam("id", id).log().all()
		.when().get("rest/api/2/issue/{id}")
		.then().log().all().extract().response().asString();
		System.out.println("Issue Details are as Follows:" + issueDetails);	
		JsonPath json = new JsonPath(issueDetails);
		int commentsCount = json.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commentsCount; i++) 
		{
			
			String commentIdIssue = json.get("fields.comment.comments["+i+"].id").toString();
			if (commentIdIssue.equalsIgnoreCase(commentId))
			{
				String message = js.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message,expectedMessage);
			}
		}
	}
}
