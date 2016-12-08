package com.eip.red.caritathelp.Models.Profile;

import java.io.Serializable;

/**
 * Created by pierr on 14/06/2016.
 */

public class PicturePath implements Serializable {

    private String  url;
    private Thumb   thumb;

    public String getUrl() {
        return url;
    }

    public Thumb getThumb() {
        return thumb;
    }
}
