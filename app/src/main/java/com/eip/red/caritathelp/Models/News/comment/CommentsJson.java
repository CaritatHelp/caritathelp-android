package com.eip.red.caritathelp.Models.News.comment;

import java.util.List;

/**
 * Created by pierr on 07/09/2016.
 */

public class CommentsJson {

    private int             status;
    private String          message;
    private List<Comment>   response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Comment> getResponse() {
        return response;
    }
}
