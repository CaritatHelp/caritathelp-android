package com.eip.red.caritathelp.Models.News;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pierr on 08/08/2016.
 */
public class News {

    static final public  String     TYPE_STATUS = "Status";

    static final public  String     GROUP_TYPE_VOLUNTEER = "Volunteer";
    static final public  String     GROUP_TYPE_EVENT = "Event";
    static final public  String     GROUP_TYPE_ORGANISATION = "Assoc";

    private int     id;
    private int     volunteer_id;
    private String  news_type;
    private String  content;
    private String  title;
    @SerializedName("private") private boolean rights;
    private int     group_id;
    private String  group_type;
    private String  group_name;
    private String  group_thumb_path;
    private String  created_at;
    private String  updated_at;
    private int     number_comments;

    private boolean as_group;
    private String  volunteer_name;
    private String  volunteer_thumb_path;

    public int getId() {
        return id;
    }

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public String getNews_type() {
        return news_type;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public boolean isRights() {
        return rights;
    }

    public int getGroup_id() {
        return group_id;
    }

    public String getGroup_type() {
        return group_type;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getGroup_thumb_path() {
        return group_thumb_path;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getNumber_comments() {
        return number_comments;
    }

    public boolean isAs_group() {
        return as_group;
    }

    public String getVolunteer_name() {
        return volunteer_name;
    }

    public String getVolunteer_thumb_path() {
        return volunteer_thumb_path;
    }
}
