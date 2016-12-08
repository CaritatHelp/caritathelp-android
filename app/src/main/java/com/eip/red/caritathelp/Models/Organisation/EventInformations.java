package com.eip.red.caritathelp.Models.Organisation;

/**
 * Created by pierr on 18/03/2016.
 */

public class EventInformations {

    public int      status;
    public String   message;
    public Event    response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Event getResponse() {
        return response;
    }
}
