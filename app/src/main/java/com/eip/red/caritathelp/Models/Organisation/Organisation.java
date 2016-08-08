package com.eip.red.caritathelp.Models.Organisation;

import com.eip.red.caritathelp.Models.Profile.MainPicture;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by pierr on 24/02/2016.
 */

public class Organisation implements Serializable {

    public static final String ORGANISATION_OWNER = "owner";
    public static final String ORGANISATION_ADMIN = "admin";
    public static final String ORGANISATION_MEMBER = "member";
    public static final String ORGANISATION_NONE = "none";
    public static final String ORGANISATION_INVITED = "invited";
    public static final String ORGANISATION_INVITED_CONFIRM = "invited confirm";
    public static final String ORGANISATION_INVITED_REMOVE = "invited remove";
    public static final String ORGANISATION_WAITING = "waiting";

    private int     id;
    private String  name;
    private String  description;
    private String  birthday;
    private String  city;
    private String  latitude;
    private String  longitude;
    private String  created_at;
    private String  updated_at;
    private String  rights;
    private String  thumb_path;
    private String  nb_friends_members;

    private MainPicture picture = null;

//    public Organisation(JsonObject result) {
//        id = initInt(result, "id");
//        name = initStr(result, "name");
//        description = initStr(result, "description");
//        birthday = initStr(result, "birthday");
//        city = initStr(result, "city");
//        latitude = initStr(result, "latitude");
//        longitude = initStr(result, "longitude");
//        rights = initStr(result, "rights");
//    }
//
//    private int initInt(JsonObject jsonObject, String memberName) {
//        JsonElement jsonElement = jsonObject.get(memberName);
//
//        if (!jsonElement.isJsonNull())
//            return (jsonElement.getAsInt());
//        return (-1);
//    }
//
//    private String initStr(JsonObject jsonObject, String memberName) {
//        JsonElement jsonElement = jsonObject.get(memberName);
//
//        if (!jsonElement.isJsonNull())
//            return (jsonElement.getAsString());
//        return (null);
//    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getRights() {
        return rights;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public String getNb_friends_members() {
        return nb_friends_members;
    }

    public MainPicture getPicture() {
        return picture;
    }

    public void setThumb_path(String thumb_path) {
        this.thumb_path = thumb_path;
    }

    public void setPicture(MainPicture picture) {
        this.picture = picture;
    }
}
