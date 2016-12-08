package com.eip.red.caritathelp.Models.Picture;

/**
 * Created by pierr on 08/12/2016.
 */

public class PictureJson {
    public int          status;
    public String       message;
    public Picture      response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Picture getResponse() {
        return response;
    }
}
