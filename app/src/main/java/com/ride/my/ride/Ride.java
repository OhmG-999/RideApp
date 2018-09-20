package com.ride.my.ride;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Ride {

    private String rideDriverID;
    private String startingPoint;
    private String finishingPoint;
    private String selectedDate;
    private String numberSeat = "2";
    private String totalFare;
    private double fare;

    public Ride(){

    }

    protected void prepareRideStatement(String userid, String starting_p, String finishing_p, String selectD, String numbSeat){

        this.setRideDriverID(userid);
        this.setStartingPoint(starting_p);
        this.setFinishingPoint(finishing_p);
        this.setSelectedDate(selectD);
        this.setNumberSeat(numbSeat);
        this.setTotalFare(this.calculateFare());
    }

    protected HashMap writeRideDatabase(){

        HashMap<String, String> rideDataMap = new HashMap<>();
        rideDataMap.put("Driver_ID", this.getRideDriverID());
        rideDataMap.put("Starting_Point", this.getStartingPoint());
        rideDataMap.put("Finishing_Point", this.getFinishingPoint());
        rideDataMap.put("Date", this.getSelectedDate());
        rideDataMap.put("Number_seat", this.getNumberSeat());
        rideDataMap.put("Total_fare", this.getTotalFare());

        return rideDataMap;
    }

    protected String calculateFare(){

        this.setFare(5);
        double numb_Seat = Double.parseDouble(this.getNumberSeat());
        double result = (this.getFare() * numb_Seat);
        String string_Result = String.valueOf(result);
        return string_Result;
    }


    protected String getRideDriverID() {
        return rideDriverID;
    }

    protected void setRideDriverID(String rideDriverID) {
        this.rideDriverID = rideDriverID;
    }

    protected String getStartingPoint() {
        return startingPoint;
    }

    protected void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    protected String getFinishingPoint() {
        return finishingPoint;
    }

    protected void setFinishingPoint(String finishingPoint) {
        this.finishingPoint = finishingPoint;
    }

    protected String getSelectedDate() {
        return selectedDate;
    }

    protected void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    protected String getNumberSeat() {
        return numberSeat;
    }

    protected void setNumberSeat(String numberSeat) {
        this.numberSeat = numberSeat;
    }

    protected String getTotalFare() {
        return totalFare;
    }

    protected void setTotalFare(String fare) {
        this.totalFare = fare;
    }

    protected double getFare(){
        return fare;
    }

    protected void setFare(double fare){
        this.fare = fare;
    }
}
