package com.eip.red.caritathelp.Models.News;

import java.util.List;

/**
 * Created by pierr on 08/08/2016.
 */
public class NewsJson {

    private int     status;
    private String  message;
    private News    response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public News getResponse() {
        return response;
    }
}
