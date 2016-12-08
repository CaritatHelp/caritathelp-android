package com.eip.red.caritathelp.Models.Friends;

import java.util.List;

/**
 * Created by pierr on 01/07/2016.
 */

public class FriendsInvitationsJson {

    private int                 status;
    private String              message;
    private List<FriendInvitation>    response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<FriendInvitation> getResponse() {
        return response;
    }
}
