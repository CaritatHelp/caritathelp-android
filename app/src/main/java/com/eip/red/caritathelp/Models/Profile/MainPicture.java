package com.eip.red.caritathelp.Models.Profile;

import java.io.Serializable;

/**
 * Created by pierr on 14/06/2016.
 */

public class MainPicture implements Serializable {

    private int         id;
    private int         file_size;
    private PicturePath picture_path;
    private int         assoc_id;
    private int         event_id;
    private int         volunteer_id;
    private boolean     is_main;
    private String      created_at;
    private String      updated_at;

    public int getId() {
        return id;
    }

    public int getFile_size() {
        return file_size;
    }

    public PicturePath getPicture_path() {
        return picture_path;
    }

    public int getAssoc_id() {
        return assoc_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public boolean is_main() {
        return is_main;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
