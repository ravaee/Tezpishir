package com.git.ravaee.tezpishir.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("fullName")
    private String nickName;

    @SerializedName("image")
    private String image;

    @SerializedName("email")
    private String email;

    @SerializedName("token")
    private String token;

    @SerializedName("password")
    private String password;

    @SerializedName("experience")
    private String experience;

    @SerializedName("areaOfService")
    private String areaOfService;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExperience() { return experience; }

    public void setExperience(String experience) { this.experience = experience; }

    public String getAreaOfService() { return areaOfService; }

    public void setAreaOfService(String areaOfService) { this.areaOfService = areaOfService; }
}
