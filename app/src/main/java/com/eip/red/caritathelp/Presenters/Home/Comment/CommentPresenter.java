package com.eip.red.caritathelp.Presenters.Home.Comment;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.News.comment.Comment;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Home.Comment.CommentView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 06/09/2016.
 */

public class CommentPresenter implements ICommentPresenter, IOnCommentFinishedListener {

    private CommentView         view;
    private CommentInteractor   interactor;

    public CommentPresenter(CommentView view, String token, int newsId) {
        this.view = view;
        interactor = new CommentInteractor(view.getContext(), token, newsId);
    }

    @Override
    public void getComments(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();
        interactor.getComments(this);
    }

    @Override
    public void sendComment(String comment) {
        view.showProgress();
        interactor.sendComment(this, comment);
    }

    @Override
    public void goToProfile(int volunteerId) {
        Tools.replaceView(view, ProfileView.newInstance(volunteerId), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetComments(List<Comment> comments) {
        view.updateRecyclerViewData(comments);
        view.hideProgress();
    }

    @Override
    public void onSuccessSendComment(Comment comment) {
        view.addComment(comment);
        view.hideProgress();
    }
}
