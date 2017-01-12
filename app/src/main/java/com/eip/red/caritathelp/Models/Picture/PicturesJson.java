package com.eip.red.caritathelp.Models.Picture;

import java.util.List;

/**
 * Created by pierr on 08/12/2016.
 */

public class PicturesJson {
    public int          status;
    public String       message;
    public List<Picture> response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Picture> getResponse() {
        return response;
    }
}
