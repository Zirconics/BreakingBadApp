package com.example.breakingbadapp;

import java.io.Serializable;

public class Character implements Serializable {

    //Attributes
    private String name;
    private String nickName;
    private String imgUrl;
    private String status;
    private String birthday;
    private String occupation;
    private String appearance;

    //Constructor
    public Character(String name, String nickName, String status, String imgUrl, String birthday, String occupation, String appearance) {
        this.name = name;
        this.nickName = nickName;
        this.status = status;
        this.imgUrl = imgUrl;
        this.birthday = birthday;
        this.occupation = occupation;
        this.appearance = appearance;
    }

    //All getters
    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getStatus() {
        return status;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getAppearance() {
        return appearance;
    }
}
