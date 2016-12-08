package com.eip.red.caritathelp.Models.User;

import java.util.List;

/**
 * Created by pierr on 04/07/2016.
 */

public class EventsInvitationsJson {

    private int                     status;
    private String                  message;
    private List<EventInvitation>   response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<EventInvitation> getResponse() {
        return response;
    }
}
