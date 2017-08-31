package com.jonathanholt.parkrun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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
		
		URL url = this.getServletContext().getResource("/parkruns.csv");
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
			//System.out.println(elephantList.get(1));
			
			URL url2 = new URL(elephantList.get(1));
			Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		    String query = url2.getQuery();
		    String[] pairs = query.split("&");
		    for (String pair : pairs) {
		        int idx = pair.indexOf("=");
		        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		    }
		    String ll = query_pairs.get("ll"); 
		    String[] llll = ll.split(",");
		    String latitude = llll[0];
		    String longitude = llll[1];
		    System.out.println("Lat = "+latitude+" Long = "+longitude);
		    	
		    //System.out.println(ll);
		    
			//System.out.println(elephantList.get(2));
			//System.out.println(elephantList.get(3));
			Parkrun toAdd = new Parkrun(elephantList.get(0), "null", "null", "null", latitude, longitude);
			//dc.doAdd(elephantList.get(0), "null", "null", latitude, longitude);	    
			//System.out.println(elephantList.get(0)+" "+elephantList.get(1)+" "+elephantList.get(2)+" "+elephantList.get(3)+" "+elephantList.get(4));
			}
			} 
		   
		   //resp.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		   //resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		  // resp.getWriter().print(json);       // Write response body.
	}
	
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	public void doGetUserInput(){
		
	}
	
	public void doLoadResultsPage(){
		
	}
}
