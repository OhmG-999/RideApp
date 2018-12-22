package com.ride.my.ride;

import java.util.HashMap;

public class User {

    private String Email;
    private String pwd;
    private String Name;
    private String Picture;

    public User(){

    }
    /*
    public User(String email, String name, String image){

        setEmail(email);
        setName(name);
        setPicture(image);
    }*/

    protected void prepareStatement(String name, String email, String pwd, String img){

        this.setName(name);
        this.setEmail(email);
        this.setPwd(pwd);
        this.setPicture(img);
    }

    protected HashMap writeNewUserDatabase(){

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("Name", this.getName());
        dataMap.put("Email", this.getEmail());
        dataMap.put("Picture", this.getPicture());

        return dataMap;

    }

    public String getEmail() {
        return Email;
    }

    private void setEmail(String email) {
        this.Email = email;
    }

    public String getPwd() {
        return pwd;
    }

    private void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return Name;
    }

    private void setName(String name) {
        this.Name = name;
    }

    public String getPicture() {
        return Picture;
    }

    private void setPicture(String picture) {
        this.Picture = picture;
    }
}
