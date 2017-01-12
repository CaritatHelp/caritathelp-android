package com.eip.red.caritathelp.Models.Organisation;

import com.eip.red.caritathelp.Models.User.User;

import java.util.List;

/**
 * Created by pierr on 03/11/2016.
 */

public class EmergencyJson {

    private int status;
    private String message;
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
