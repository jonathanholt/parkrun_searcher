package com.jonathanholt.parkrun;

import java.io.IOException;
import javax.servlet.http.*;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


@SuppressWarnings("serial")
public class ParkRunSearchServlet extends HttpServlet {
	
	static private Comparator<Parkrun> descPageNumber;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        
		Comparator<Parkrun> descPageNumber = new Comparator<Parkrun>(){
	            @Override
	            public int compare(Parkrun p1, Parkrun p2){
	                // Java 7 has an Integer#compare function
	            	int result1 = (int)p1.getDoubleDistance();
	            	int result2 = (int)p2.getDoubleDistance();
	                return Integer.compare(result1, result2);
	                // For Java < 7, use 
	                // Integer.valueOf(n1).compareTo(n2);
	                // DO NOT subtract numbers to make a comparison such as n2 - n1.
	                // This can cause a negative overflow if the difference is larger 
	                // than Integer.MAX_VALUE (e.g., n1 = 2^31 and n2 = -2^31)
	            }
	        };
	    
		
		DataConnect dc = new DataConnect();
		ArrayList<Parkrun> Allparkruns = dc.getAll(); 
		String lat = req.getParameter("lat");
		double latdouble = Double.parseDouble(lat);
		//System.out.println(lat);
		String longi = req.getParameter("long");
		double longdouble = Double.parseDouble(longi);
		
		for(int i = 0; i < Allparkruns.size(); i++){
			double parkrunlatdouble = Double.parseDouble(Allparkruns.get(i).getLatitude());
			double parkrunlongdouble = Double.parseDouble(Allparkruns.get(i).getLongitude());
			double parkrundistance = distance(latdouble, longdouble, parkrunlatdouble, parkrunlongdouble, 'M');
			Allparkruns.get(i).setDoubleDistance(parkrundistance);
			//System.out.println("Distance = "+ parkrundistance);
			//PARSE ALL FLOATS, SEND TO DISTANCE FUNCTION
			// ADD TO NEW DISTANCES ARRAY OF ARRAYS
		}
		
		BeanComparator bc = new BeanComparator(Parkrun.class, "getDoubleDistance");
		Collections.sort(Allparkruns, bc);
		//for(int i = 0; i < Allparkruns.size(); i++){
		//	System.out.println(Allparkruns.get(i).getDoubleDistance());
		//}
		
		//Allparkruns.sort(Comparator.comparing(Parkrun::getDoubleDistance));
		
		//List<Parkrun> closest = Allparkruns.subList(0, 5);

		
		String json = new Gson().toJson(Allparkruns);
		   
		   resp.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		   resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		   resp.getWriter().print(json);       // Write response body.
		/**
		// need to turn an array into json. The array should contain the parkrun name, distance, address and link
		   Parkrun parkrun = new Parkrun("test parkrun", "test address", "test url", "5 miles", "53.4106", "2.1575");
		   Parkrun parkrun2 = new Parkrun("test parkrun 2", "test address 2", "test url 2", "10 miles", "53.5409", "2.1114");
		   
		   String imageUrl = "https://i.stack.imgur.com/MOKW8.png";
		   parkrun.setImage(imageUrl);
		   parkrun2.setImage(imageUrl);
		   
		   String name = req.getParameter("name"); // abc
		   //String address = req.getParameter("address"); // cde
		   
		   //System.out.println(name);
		   
		   
		   Parkrun[] parkruns = new Parkrun[2];
		   
		   parkruns[0] = parkrun;
		   parkruns[1] = parkrun2;
		   
		   String json = new Gson().toJson(parkruns);
		   
		   resp.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		   resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		   resp.getWriter().print(json);       // Write response body.
	**/
	}
	
	public void doGetUserInput(){
		
	}
	
	public void doLoadResultsPage(){
		
	}
	
    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
          dist = dist * 1.609344;
        } else if (unit == 'N') {
          dist = dist * 0.8684;
          }
        return (dist);
      }

      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts decimal degrees to radians             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }

      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts radians to decimal degrees             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }
}
