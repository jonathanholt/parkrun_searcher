package com.jonathanholt.parkrun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SuppressWarnings("serial")
public class ParkRunAddScriptServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		   // need to turn an array into json. The array should contain the parkrun name, distance, address and link
		   
		//Parkrun parkrun = new Parkrun("test parkrun", "test address", "test url", "5 miles", "53.4106", "2.1575");
		//Parkrun parkrun2 = new Parkrun("test parkrun 2", "test address 2", "test url 2", "10 miles", "53.5409", "2.1114");
		   
		 //  String imageUrl = "https://i.stack.imgur.com/MOKW8.png";
		 //  parkrun.setImage(imageUrl);
		 //  parkrun2.setImage(imageUrl);
		   
		  // String name = req.getParameter("name"); // abc
		   //String address = req.getParameter("address"); // cde
		   
		   //System.out.println(name);
		   
		   
		   //Parkrun[] parkruns = new Parkrun[2];
		   
		 //  parkruns[0] = parkrun;
		  // parkruns[1] = parkrun2;
		   
		   //String json = new Gson().toJson(parkruns);
		
		URL url = this.getServletContext().getResource("/tester.csv");
		System.out.println(url);
		InputStreamReader isr = new InputStreamReader(url.openStream());
		BufferedReader reader = new BufferedReader(isr);
		String line = null;  
		DataConnect dc = new DataConnect();
		while ((line = reader.readLine()) != null)  
		{  
			List<String> elephantList = Arrays.asList(line.split(","));
			if(elephantList.size() > 2){
			System.out.println(elephantList.get(0));
			System.out.println(elephantList.get(1));
			System.out.println(elephantList.get(2));
			System.out.println(elephantList.get(3));
			//Parkrun toAdd = new Parkrun(elephantList.get(0), "null", elephantList.get(1), "null", elephantList.get(2), elephantList.get(3));
			dc.doAdd(elephantList.get(2), "null", "null", elephantList.get(3), elephantList.get(4));	    
			System.out.println(elephantList.get(0)+" "+elephantList.get(1)+" "+elephantList.get(2)+" "+elephantList.get(3)+" "+elephantList.get(4));
			}
			} 
		   
		   resp.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		   resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		  // resp.getWriter().print(json);       // Write response body.
	}
	
	public void doGetUserInput(){
		
	}
	
	public void doLoadResultsPage(){
		
	}
}
