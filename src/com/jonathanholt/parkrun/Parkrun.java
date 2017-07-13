package com.jonathanholt.parkrun;

public class Parkrun {
	
	String parkrunName;
	String parkrunAddress;
	String parkrunURL;
	String parkrunDistance;
	String latitude;
	String longitude;
	String markerUrl;
	double doubleDistance;
	
	
    public Parkrun(String parkrunName2, String parkrunAddress2, String parkrunUrl2, String parkrunDistance2, String longitude2, String latitude2)
    {
         this.parkrunName = parkrunName2;
         this.parkrunAddress = parkrunAddress2;
         this.parkrunURL = parkrunUrl2;
         this.parkrunDistance = parkrunDistance2;
         this.longitude = longitude2;
         this.latitude = latitude2;
    }
    
    public String getLatitude(){
    	return this.latitude;
    }
    
    public float getDistanceFloat(){
    	return Float.parseFloat(this.parkrunDistance);
    }
    
    public void setImage(String imageUrl){
    	this.markerUrl = imageUrl;
    }
    
    public String getLongitude(){
    	return this.longitude;
    }
    
    public String getName(){
    	return this.parkrunName;
    }
    
    public String getAddress(){
    	return this.parkrunAddress;
    }
    
    public String getUrl(){
    	return this.parkrunURL;
    }
    
    public String getDistance(){
    	return this.parkrunDistance;
    }
    
    public double getDoubleDistance(){
    	return this.doubleDistance;
    }
    
    public void setDoubleDistance(double doubledistance){
    	this.doubleDistance = doubledistance;
    }
    
    public double compareTo(Parkrun compareRun) {

		double compareQuantity = ((Parkrun) compareRun).getDoubleDistance();

		//ascending order
		return this.doubleDistance - compareQuantity;

		//descending order
		//return compareQuantity - this.quantity;

	}
    
}
