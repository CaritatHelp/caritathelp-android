package com.eip.red.caritathelp.Models.News;

import java.util.List;

/**
 * Created by pierr on 07/09/2016.
 */

public class NewsListJson {

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
