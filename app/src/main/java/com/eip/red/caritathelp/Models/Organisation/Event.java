package com.eip.red.caritathelp.Models.Organisation;

/**
 * Created by pierr on 17/03/2016.
 */

public class Event {

    private int     id;
    private String  title;
    private String  description;
    private String  place;
    private String  begin;
    private String  end;
    private String  assoc_id;
    private String  rights;
    private String  thumb_path;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public String getBegin() {
        return begin;
    }

    public String getAssoc_id() {
        return assoc_id;
    }

    public String getEnd() {
        return end;
    }

    public String getRights() {
        return rights;
    }

    public String getThumb_path() {
        return thumb_path;
    }
}
