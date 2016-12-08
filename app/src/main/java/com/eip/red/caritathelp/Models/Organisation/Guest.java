package com.eip.red.caritathelp.Models.Organisation;

/**
 * Created by pierr on 15/04/2016.
 */

public class Guest {

    private int     id;
    private String  mail;
    private String  firstname;
    private String  lastname;
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

    public String getRights() {
        return rights;
    }
}