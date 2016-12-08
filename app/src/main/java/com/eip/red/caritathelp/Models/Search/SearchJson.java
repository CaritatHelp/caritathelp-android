package com.eip.red.caritathelp.Models.Search;

import java.util.List;

/**
 * Created by pierr on 29/06/2016.
 */

public class SearchJson {

    private int             status;
    private String          message;
    private List<Search>  response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Search> getResponse() {
        return response;
    }
}
