import org.testng.annotations.Test;

import Files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

@Test
public void sumValidation()
{
	JsonPath json = new JsonPath(payload.CoursePrice());
			int count = json.getInt("courses.size()");
	for(int i = 0; i < count; i++)
	{
		int price = json.get("courses["+i+"].price");
		int copies = json.get("courses["+i+"].copies");
		int amount = price * copies;
		System.out.println("Amount is " + amount);
	}
}
}

