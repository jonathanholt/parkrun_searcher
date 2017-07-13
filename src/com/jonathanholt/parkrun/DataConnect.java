package com.jonathanholt.parkrun;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * 
 * @author jholt_000 DataConnect object connects to the Datastore and queries
 *         the datastore based on it's configured variables
 */
public class DataConnect{

	// Three variables used when constructing a query
	private DatastoreService datastore;
	
	/**
	 * Default constructor; inits a connection to the Datastore
	 */
	public DataConnect() {
		// TODO Auto-generated constructor stub
		// The variables are set when the DataConnect object is created
		this.datastore = DatastoreServiceFactory
				.getDatastoreService();
	}
	
	/**
	 * Adds a Movie entity to the datastore
	 * @param title; user input
	 * @param director; user input
	 * @param price; user input
	 * @param year; user input
	 * @param type; user input
	 */
	public void doAdd(String parkrunname, String parkrunAddress2, String parkrunUrl2, String parkrunDistance2, String parkrunDistance3){
		Entity e = new Entity("Parkrun");
		e.setProperty("name", parkrunname);
		e.setProperty("address", parkrunAddress2);
		e.setProperty("url", parkrunUrl2);
		e.setProperty("latitude", parkrunDistance2);
		e.setProperty("longitude", parkrunDistance3);
		// Add the entity to the datastore
		datastore.put(e);
	}
	
	/**
	 * Constructs a query to get all entities in the Movie database
	 * @return Query; the completed Query, ready to be executed
	 */
	public ArrayList<Parkrun> getAll(){
		// Use class Query to assemble a query based on the purpose variable
			Query q = new Query("Parkrun");
			PreparedQuery pq = datastore.prepare(q);
			ArrayList<Parkrun> myarray = new ArrayList<Parkrun>();
			for (Entity result : pq.asIterable()) {
				Parkrun parkrunname = new Parkrun((String) result.getProperty("name"), "", "", "", (String) result.getProperty("longitude"), (String) result.getProperty("latitude"));
				myarray.add(parkrunname);
			}
				return myarray;
	}
}