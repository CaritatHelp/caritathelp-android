package com.eip.red.caritathelp.Models.User;

import java.util.List;

/**
 * Created by pierr on 14/01/2017.
 */

public class UsersJson {
    private int      status;
    private String   message;
    private List<User> response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getResponse() {
        return response;
    }

}
