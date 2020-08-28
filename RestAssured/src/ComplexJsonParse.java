import org.testng.Assert;

import Files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath json = new JsonPath(payload.CoursePrice());

		
		// Print number of courses returned by API
		int count = json.getInt("courses.size()");
		System.out.println(count);
		
		// Print purchase Amount
		int purchaseAmount = json.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//Print Title of the First course
		//Can use either get or getString method to pull out the string
		String firstCourseTitle = json.getString("courses[0].title");
		System.out.println(firstCourseTitle);
		
		//Print All Course Titles and their respective Prices
		for (int i = 0; i < count; i++)
		{
			String courseTitles = json.get("courses["+i+"].title");
			int price = json.getInt("courses["+i+"].price");
			System.out.println("Course is " + courseTitles + "," +" Price is " + price);
		}
		
		
	//Print number of copies sold by RPA Course
	
	for(int i = 0; i < count; i++)
	{
		String courseTitles = json.get("courses["+i+"].title");
	
		
		if(courseTitles.equalsIgnoreCase("RPA"))
		{
			int copies = json.get("courses["+i+"].copies");
			System.out.println("Number of copies sold for the RPA course are " + copies);
			break; //This will break the loop once the condition is fulfilled
		}
	}
	
	//Verify if the sum of all course prices matches with purchase price
	int sum = 0;
	for(int i = 0; i < count; i++)
	{
		
		int price = json.get("courses["+i+"].price");
		int copies = json.get("courses["+i+"].copies");
		int amount = price * copies;
		System.out.println("Amount is " + amount);
		sum = sum + amount;
	}
	
	System.out.println("Total of the courses is: " + sum);
	int totalAmount = json.get("dashboard.purchaseAmount");
	Assert.assertEquals(sum, totalAmount);
	
	}
	

}
