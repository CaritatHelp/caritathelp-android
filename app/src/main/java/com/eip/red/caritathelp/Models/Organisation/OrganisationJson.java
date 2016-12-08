package com.eip.red.caritathelp.Models.Organisation;

import com.eip.red.caritathelp.Models.Organisation.Organisation;

import java.util.ArrayList;

/**
 * Created by pierr on 28/03/2016.
 */

public class OrganisationJson {

    private int                      status;
    private String                   message;
    private Organisation             response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Organisation getResponse() {
        return response;
    }
}
