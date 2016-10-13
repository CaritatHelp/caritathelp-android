package com.eip.red.caritathelp.Models.mailbox;

import java.util.List;

/**
 * Created by pierr on 13/10/2016.
 */

public class ChatroomsJson {

    private int             status;
    private String          message;
    private List<Chatroom> response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Chatroom> getResponse() {
        return response;
    }

}
