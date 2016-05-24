package com.hospitalsearch.util;

/**
 * 
 * @author Oleksandr Mukonin
 * 
 * Entity to get bounds (of Google Map), store and transfer to controller method that gets marks from database
 *
 */

public class Bounds {	
	
	public Bounds(double southWestLat, double southWestLon, double northEastLat, double northEastLon) {
		this.northEastLat = northEastLat;
		this.northEastLon = northEastLon;
		this.southWestLat = southWestLat;
		this.southWestLon = southWestLon;
	}
	
	public Bounds() {

	}
	
	private double southWestLat;
	private double southWestLon;
	private double northEastLat;
	private double northEastLon;
	
	public double getSouthWestLat() {
		return southWestLat;
	}
	public void setSouthWestLat(double southWestLat) {
		this.southWestLat = southWestLat;
	}
	public double getSouthWestLon() {
		return southWestLon;
	}
	public void setSouthWestLon(double southWestLon) {
		this.southWestLon = southWestLon;
	}
	public double getNorthEastLat() {
		return northEastLat;
	}
	public void setNorthEastLat(double northEastLat) {
		this.northEastLat = northEastLat;
	}
	public double getNorthEastLon() {
		return northEastLon;
	}
	public void setNorthEastLon(double northEastLon) {
		this.northEastLon = northEastLon;
	}	

}
