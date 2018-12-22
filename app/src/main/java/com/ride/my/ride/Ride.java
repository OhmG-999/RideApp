package com.ride.my.ride;

import java.util.HashMap;

public class Ride {

    private String Driver_ID;
    private String Starting_Point;
    private String Finishing_Point;
    private String Date;
    private String Number_seat = "2";
    private String Total_fare;
    private double fare;

    public Ride(){

    }
    /*
    public Ride(String Driver_ID, String Starting_Point, String Finishing_Point, String Date, String Number_seat, String Total_fare, double fare) {
        this.setDriver_ID(Driver_ID);
        this.setStarting_Point(Starting_Point);
        this.setFinishing_Point(Finishing_Point);
        this.setDate(Date);
        this.setNumber_seat(Number_seat);
        this.setTotal_fare(Total_fare);
        this.setFare(fare);
    }*/

    protected void prepareRideStatement(String userid, String starting_p, String finishing_p, String selectD, String numbSeat){

        this.setDriver_ID(userid);
        this.setStarting_Point(starting_p);
        this.setFinishing_Point(finishing_p);
        this.setDate(selectD);
        this.setNumber_seat(numbSeat);
        this.setTotal_fare(this.calculateFare());
    }

    protected HashMap writeRideDatabase(){

        HashMap<String, String> rideDataMap = new HashMap<>();
        rideDataMap.put("Driver_ID", this.getDriver_ID());
        rideDataMap.put("Starting_Point", this.getStarting_Point());
        rideDataMap.put("Finishing_Point", this.getFinishing_Point());
        rideDataMap.put("Date", this.getDate());
        rideDataMap.put("Number_seat", this.getNumber_seat());
        rideDataMap.put("Total_fare", this.getTotal_fare());

        return rideDataMap;
    }

    protected String calculateFare(){

        this.setFare(5);
        double numb_Seat = Double.parseDouble(this.getNumber_seat());
        double result = (this.getFare() * numb_Seat);
        String string_Result = String.valueOf(result);
        return string_Result;
    }


    protected String getDriver_ID() {
        return Driver_ID;
    }

    protected void setDriver_ID(String driver_ID) {
        this.Driver_ID = driver_ID;
    }

    protected String getStarting_Point() {
        return Starting_Point;
    }

    protected void setStarting_Point(String starting_Point) {
        this.Starting_Point = starting_Point;
    }

    protected String getFinishing_Point() {
        return Finishing_Point;
    }

    protected void setFinishing_Point(String finishing_Point) {
        this.Finishing_Point = finishing_Point;
    }

    protected String getDate() {
        return Date;
    }

    protected void setDate(String date) {
        this.Date = date;
    }

    protected String getNumber_seat() {
        return Number_seat;
    }


    protected void setNumber_seat(String number_seat) {
        this.Number_seat = number_seat;
    }

    protected String getTotal_fare() {
        return Total_fare;
    }

    protected void setTotal_fare(String fare) {
        this.Total_fare = fare;
    }

    protected double getFare(){
        return fare;
    }

    protected void setFare(double fare){
        this.fare = fare;
    }
}
