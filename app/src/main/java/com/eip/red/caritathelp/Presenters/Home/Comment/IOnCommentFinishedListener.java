package com.eip.red.caritathelp.Presenters.Home.Comment;

import com.eip.red.caritathelp.Models.News.comment.Comment;

import java.util.List;

/**
 * Created by pierr on 06/09/2016.
 */

public interface IOnCommentFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessGetComments(List<Comment> comments);

    void onSuccessSendComment(Comment comment);
}
