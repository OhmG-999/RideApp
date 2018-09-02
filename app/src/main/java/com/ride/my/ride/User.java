package com.ride.my.ride;

import java.util.HashMap;
import android.text.TextUtils;

public class User {

    private String email;
    private String pwd;
    private String name;
    private String image;

    public User(){

    }

    public User(String email, String pwd, String name, String image){

        setEmail(email);
        setPwd(pwd);
        setName(name);
        setImage(image);
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
