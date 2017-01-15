package com.eip.red.caritathelp.Models.Organisation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pierr on 15/04/2016.
 */

public class Guest {

    private int     id;
    private String  mail;
    private String  firstname;
    private String  lastname;
    private String  rights;
    private String  thumb_path;
    @SerializedName("notif_id")
    private int notifId;

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public int getNotifId() {
        return notifId;
    }
}