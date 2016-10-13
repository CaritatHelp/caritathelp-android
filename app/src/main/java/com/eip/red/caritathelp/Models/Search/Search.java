package com.eip.red.caritathelp.Models.Search;

/**
 * Created by pierr on 29/06/2016.
 */

public class Search {

    public static final String  RESULT_TYPE_VOLUNTEER = "volunteer";
    public static final String  RESULT_TYPE_ASSOC = "assoc";
    public static final String  RESULT_TYPE_EVENT = "event";

    public static final String  RIGHTS_VOLUNTEER_NO_FRIEND = "0";
    public static final String  RIGHTS_VOLUNTEER_FRIEND = "1";
    public static final String  RIGHTS_ASSOC_OWNER = "owner";
    public static final String  RIGHTS_ASSOC_ADMIN = "admin";
    public static final String  RIGHTS_ASSOC_MEMBER = "member";
    public static final String  RIGHTS_ASSOC_NOT_MEMBER = null;
    public static final String  RIGHTS_EVENT_HOST = "host";
    public static final String  RIGHTS_EVENT_ADMIN = "admin";
    public static final String  RIGHTS_EVENT_MEMBER = "member";
    public static final String  RIGHTS_EVENT_NOT_MEMBER = null;

    private Integer id;
    private String  thumb_path;
    private String  name;
    private String  rights;
    private String  result_type;

    private String  result;
    private Boolean added;

    public Search() {
        result = null;
        added = false;
    }

    public Integer getId() {
        return id;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public String getName() {
        return name;
    }

    public String getRights() {
        return rights;
    }

    public String getResult_type() {
        return result_type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean isAdded() {
        return added;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }
}
