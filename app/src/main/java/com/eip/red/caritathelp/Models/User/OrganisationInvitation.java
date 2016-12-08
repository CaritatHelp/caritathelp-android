package com.eip.red.caritathelp.Models.User;

/**
 * Created by pierr on 04/07/2016.
 */

public class OrganisationInvitation {

    private int     id;
    private String  name;
    private String  city;
    private int     nb_friends_members;
    private int     nb_members;
    private String  thumb_path;
    private int     notif_id;

    private String result = null;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getNb_friends_members() {
        return nb_friends_members;
    }

    public int getNb_members() {
        return nb_members;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public int getNotif_id() {
        return notif_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
