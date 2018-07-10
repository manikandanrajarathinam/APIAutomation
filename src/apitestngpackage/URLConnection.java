package apitestngpackage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class URLConnection {
	//Declaration of variables
	private static Scanner scan;
	private static URL url;

	public static String getRequestURL(String APIURL,String RequestMethod,String entireResponse) {
		try {
			url = new URL(APIURL);

			HttpURLConnection conn;

			conn = (HttpURLConnection) url.openConnection();

			//Fetch the parameters of API using GET method
			conn.setRequestMethod(RequestMethod);
			conn.setRequestProperty("Accept", "application/json");

			Thread.sleep(1000);

			//connection response code
			if (conn.getResponseCode() == 200) {
				System.out.println("Status : OK ");
				System.out.println("Request is successful");
				System.out.println();
			}else if(conn.getResponseCode() == 301) {
				System.out.println("Specify new permanent URL");
				throw new RuntimeException("HTTP error code : "
						+ conn.getResponseCode());
			}
			else if(conn.getResponseCode() == 400) {
				System.out.println("Bad Request");
				throw new RuntimeException("HTTP error code : "
						+ conn.getResponseCode());
			}
			else if(conn.getResponseCode() == 401) {
				System.out.println("Unauthorized");
				throw new RuntimeException("HTTP error code : "
						+ conn.getResponseCode());
			}
			else
			{
				System.out.println("Request is unsuccessful");
				throw new RuntimeException("HTTP error code : "
						+ conn.getResponseCode());
			}
			//using Scanner class fetching the parameters of API
			scan = new Scanner(url.openStream());
			entireResponse = new String();
			while (scan.hasNext())
			{
				entireResponse += scan.nextLine();
			}
			//closing the scanner class
			scan.close();
			//disconnecting the HTTPURL connection
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("URL you are providing is invalid" + e);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return the entire data
		return entireResponse;
	}
}
