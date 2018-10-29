package com.ride.my.ride;

import java.util.HashMap;
import android.text.TextUtils;

public class User {

    private String email;
    private String pwd;
    private String name;
    private String image;

    Ride myRide = new Ride();

    public User(){

    }

    public User(String email, String name, String image){

        setEmail(email);
        setName(name);
        setImage(image);
    }

    public User(String email, String name, String image,
                String userid, String starting_p, String finishing_p,
                String selectD, String numbSeat){

        setEmail(email);
        setName(name);
        setImage(image);
        myRide.setRideDriverID(userid);
        myRide.setStartingPoint(starting_p);
        myRide.setFinishingPoint(finishing_p);
        myRide.setSelectedDate(selectD);
        myRide.setNumberSeat(numbSeat);
    }

    protected void prepareStatement(String name, String email, String pwd, String img){

        this.setName(name);
        this.setEmail(email);
        this.setPwd(pwd);
        this.setImage(img);
    }

    protected HashMap writeNewUserDatabase(){

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("Name", this.getName());
        dataMap.put("Email", this.getEmail());
        dataMap.put("Picture", this.getImage());

        return dataMap;

    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    private void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    private void setImage(String image) {
        this.image = image;
    }
}
