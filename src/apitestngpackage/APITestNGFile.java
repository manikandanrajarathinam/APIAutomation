package apitestngpackage;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Parameters;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APITestNGFile {
	//Declaration of variables
	private JSONObject obj;
	private String s_responseCode;
	private boolean b_responseCode;
	private JSONArray arr;
	private String parameter;
	private static String entireResponse;
	private static String contentValidation;
	SoftAssert softAssert = new SoftAssert();
	int i;

	@Test
	public String apiTesting(String entireResponse) {
		//To get the response
		entireResponse=URLConnection.getRequestURL("https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false","GET",entireResponse);
		return entireResponse;
	}

	@Parameters({ "name","check_parameter"})
	@Test(priority=1)
	public void Testcase1(String name,String p_value) throws IOException {
		try {
			entireResponse=apiTesting(entireResponse);
			System.out.println();
			System.out.println("Execution of Acceptance Criteria 1 : " );
			//JSON represent two structured types: objects and arrays
			//JSONObject to fetch the string value
			obj = new JSONObject(entireResponse);
			s_responseCode = obj.getString(name);
			if(s_responseCode.equalsIgnoreCase(p_value))
			{
				System.out.println("Name = "+  "\"" +s_responseCode +"\"" );
				//Assert doesn't throw exception when it fails and would continue with the next step
				softAssert.assertEquals(s_responseCode, p_value);
			}
			else
			{
				System.out.println("Name = "+  "\"" +s_responseCode +"\"" );
				softAssert.assertFalse(true);
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid parameter " + e + "hence test case failed");
		}
	}

	@Parameters({"boolean_check","boolean_verify"})
	@Test(priority=2)
	public void Testcase2(String booleancheck,boolean verify) {
		try {
			System.out.println();
			System.out.println("Execution of Acceptance Criteria 2 : " );
			//JSON represent two structured types: objects and arrays
			//JSONObject to fetch the boolean value
			b_responseCode = obj.getBoolean(booleancheck); 
			if(b_responseCode==verify)
			{
				System.out.println("CanRelist = "+ b_responseCode);
				softAssert.assertTrue(b_responseCode == true);
			}
			else
			{
				System.out.println("CanRelist = "+ b_responseCode);
				softAssert.assertFalse(true);
			}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid parameter " + e + "hence test case failed");
		}

	}

	@Parameters({ "name", "element", "element_value","element_desc", "text_validation"})
	@Test(priority=3)
	public void Testcase3(String name,String element,String value,String desc,String textvalidation) {
		try {
			//JSON represent two structured types: objects and arrays
			//JSONArray to fetch the string value in an Array
			arr = obj.getJSONArray(element);
			System.out.println();
			System.out.println("Execution of Acceptance Criteria 3 : " );
			for (i = 0; i < arr.length(); i++) {
				parameter = arr.getJSONObject(i).getString(name);
				if(parameter.equalsIgnoreCase(value))
				{	
					System.out.println("The Promotions element with Name  = "+  "\"" +value +"\"" +" ");
					contentValidation = arr.getJSONObject(i).getString(desc);
					if(contentValidation.contains(textvalidation))
					{
						softAssert.assertEquals(true, true, "Validation Successful");
						System.out.print(" has a " + desc + " that contains the text " + "\"" + textvalidation+"\"" );
						System.out.println();
						System.out.println("\"" +contentValidation +"\"");
						//break;
					}
					else {
						softAssert.assertEquals(false, true, "Validation Unsuccessful");
						//break;
					}
				}	
			}
			softAssert.assertAll();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid parameter " + e + "hence test case failed");
		}
	}
}
