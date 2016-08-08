package com.eip.red.caritathelp.Models.News;

/**
 * Created by pierr on 08/08/2016.
 */
public class News {

    static final public  String     TYPE_VOLUNTEER = "New::Volunteer::Status";
    static final public  String     TYPE_ORGANISATION = "New::Assoc::Status";
    static final public  String     TYPE_EVENT = "New::Event::Status";

    private int     id;
    private int     assoc_id;
    private int     event_id;
    private int     volunteer_id;
    private int     friend_id;
    private String  title;
    private String  content;
    private String  created_at;
    private String  updated_at;
    private String  news_type;
    private String  event_name;
    private String  event_thumb_path;
    private String  assoc_name;
    private String  assoc_thumb_path;
    private String  volunteer_name;
    private String  volunteer_thumb_path;

    public int getId() {
        return id;
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

    public int getFriend_id() {
        return friend_id;
    }

    public String getTitle() {
        return title;
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

    public String getNews_type() {
        return news_type;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_thumb_path() {
        return event_thumb_path;
    }

    public String getAssoc_name() {
        return assoc_name;
    }

    public String getAssoc_thumb_path() {
        return assoc_thumb_path;
    }

    public String getVolunteer_name() {
        return volunteer_name;
    }

    public String getVolunteer_thumb_path() {
        return volunteer_thumb_path;
    }
}
