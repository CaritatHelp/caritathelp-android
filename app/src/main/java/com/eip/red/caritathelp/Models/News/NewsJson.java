package com.eip.red.caritathelp.Models.News;

import java.util.List;

/**
 * Created by pierr on 08/08/2016.
 */
public class NewsJson {

    private int         status;
    private String      message;
    private List<News>  response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<News> getResponse() {
        return response;
    }
}
