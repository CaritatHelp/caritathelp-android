package com.eip.red.caritathelp.Models.Organisation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pierr on 11/03/2016.
 */

public class Member {

    private int     id;
    private String  mail;
    private String  firstname;
    private String  lastname;
    private String  thumb_path;
    @SerializedName("notif_id")
    private int notifId;
    private String  rights;

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

    public String getThumb_path() {
        return thumb_path;
    }

    public int getNotifId() {
        return notifId;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }
}
