package com.example.msk.onlinebotique.Pojo;

/**
 * Created by DELL on 5/14/2017.
 */

public class User {

    private String Name;
    private String Email;
    private String Country;
    private String City;
    private String Category;
    private String Profile_Pic;
    private String User_Id;
    private String User_Pass;
    private String User_UId;
    private String isShopOpened;


    public User() {
    }


    public String getIsShopOpened() {
        return isShopOpened;
    }

    public void setIsShopOpened(String isShopOpened) {
        this.isShopOpened = isShopOpened;
    }

    public User(String name, String email, String country, String city,
                String category, String profile_Pic, String user_Id,
                String user_Pass, String user_UId , String IsShopOpened) {
        Name = name;
        Email = email;
        Country = country;
        City = city;
        Category = category;
        Profile_Pic = profile_Pic;
        User_Id = user_Id;
        User_Pass = user_Pass;
        User_UId = user_UId;
        isShopOpened = IsShopOpened;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getProfile_Pic() {
        return Profile_Pic;
    }

    public void setProfile_Pic(String profile_Pic) {
        Profile_Pic = profile_Pic;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getUser_Pass() {
        return User_Pass;
    }

    public void setUser_Pass(String user_Pass) {
        User_Pass = user_Pass;
    }

    public String getUser_UId() {
        return User_UId;
    }

    public void setUser_UId(String user_UId) {
        User_UId = user_UId;
    }
}
