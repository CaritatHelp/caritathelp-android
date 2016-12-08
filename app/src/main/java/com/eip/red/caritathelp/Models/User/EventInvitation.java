package com.eip.red.caritathelp.Models.User;

/**
 * Created by pierr on 04/07/2016.
 */

public class EventInvitation {

    private int     id;
    private String  title;
    private String  place;
    private String  begin;
    private String  end;
    private int     assoc_id;
    private String  thumb_path;
    private int     nb_guest;
    private int     nb_friends_members;
    private int     notif_id;

    private String result = null;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPlace() {
        return place;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public int getAssoc_id() {
        return assoc_id;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public int getNb_guest() {
        return nb_guest;
    }

    public int getNb_friends_members() {
        return nb_friends_members;
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
