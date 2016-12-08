package com.eip.red.caritathelp.Models.News.comment;

/**
 * Created by pierr on 06/09/2016.
 */

public class Comment {

    private int     id;
    private int     new_id;
    private int     volunteer_id;
    private String  content;
    private String  created_at;
    private String  updated_at;
    private String  thumb_path;
    private String  firstname;
    private String  lastname;

    public int getId() {
        return id;
    }

    public int getNew_id() {
        return new_id;
    }

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
