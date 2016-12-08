package com.eip.red.caritathelp.Models.Friends;

import java.util.List;

/**
 * Created by pierr on 23/04/2016.
 */

public class FriendsJson {

    private int             status;
    private String          message;
    private List<Friend>    response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Friend> getResponse() {
        return response;
    }
}
