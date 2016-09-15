package com.eip.red.caritathelp.Models.News.comment;

/**
 * Created by pierr on 06/09/2016.
 */
public class CommentJson {

    private int             status;
    private String          message;
    private Comment         response;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Comment getResponse() {
        return response;
    }
}
