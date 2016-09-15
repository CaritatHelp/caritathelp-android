package com.eip.red.caritathelp.Presenters.Home.Comment;

/**
 * Created by pierr on 06/09/2016.
 */

public interface ICommentPresenter {

    void getComments(boolean isSwipeRefresh);

    void sendComment(String comment);

    void goToProfile(int volunteerId);
}
