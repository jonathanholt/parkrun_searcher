package com.jonathanholt.parkrun;

import java.io.IOException;
import javax.servlet.http.*;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


@SuppressWarnings("serial")
public class ParkRunAllServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			DataConnect dc = new DataConnect();
		   List<Parkrun> parkruns = dc.getAll();
		   
		   String json = new Gson().toJson(parkruns);
		   resp.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		   resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		   resp.getWriter().print(json);       // Write response body.
	}
}
