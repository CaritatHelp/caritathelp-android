package com.eip.red.caritathelp.Models.Organisation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 15/04/2016.
 */

public class Guests {

    public int          status;
    public String       message;
    public List<Guest>  response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Guest> getResponse() {
        return response;
    }
}
