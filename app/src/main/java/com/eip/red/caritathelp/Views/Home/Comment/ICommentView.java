package com.eip.red.caritathelp.Views.Home.Comment;


import com.eip.red.caritathelp.Models.News.comment.Comment;

import java.util.List;

/**
 * Created by pierr on 06/09/2016.
 */

public interface ICommentView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);

    void updateRecyclerViewData(List<Comment> comments);

    void addComment(Comment comment);
}
