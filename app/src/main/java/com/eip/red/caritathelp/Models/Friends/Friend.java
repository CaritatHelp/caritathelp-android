package com.eip.red.caritathelp.Models.Friends;

/**
 * Created by pierr on 23/04/2016.
 */

public class Friend {

    private int     id;
    private String  mail;
    private String  firstname;
    private String  lastname;
    private String  thumb_path;
    private String  nb_common_friends;

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

    public String getNb_common_friends() {
        return nb_common_friends;
    }
}
