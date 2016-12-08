package com.eip.red.caritathelp.Models.User;

/**
 * Created by pierr on 11/05/2016.
 */

public class UserJson {

    private int      status;
    private String   message;
    private User     response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getResponse() {
        return response;
    }
}
