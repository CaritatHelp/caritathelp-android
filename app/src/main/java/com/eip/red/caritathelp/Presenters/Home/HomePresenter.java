package com.eip.red.caritathelp.Presenters.Home;

import android.widget.ImageView;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Home.Comment.CommentView;
import com.eip.red.caritathelp.Views.Home.HomeView;
import com.eip.red.caritathelp.Views.Home.Post.PostView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 11/01/2016.
 */

public class HomePresenter implements IHomePresenter, IOnHomeFinishedListener {

    private HomeView        view;
    private HomeInteractor  interactor;

    public HomePresenter(HomeView view, User user) {
        this.view = view;
        interactor = new HomeInteractor(view.getContext(), user);
    }

    @Override
    public void initUserImage(ImageView imageView) {
        Network.loadImage(view.getContext(), imageView, Network.API_LOCATION + interactor.getUser().getThumb_path(), R.drawable.profile_example);
    }

    @Override
    public void getNews(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();
        interactor.getNews(this);
    }

    @Override
    public void goToProfileView() {
        Tools.replaceView(view, ProfileView.newInstance(interactor.getUser().getId()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToPostView() {
        Tools.replaceView(view, PostView.newInstance(News.GROUP_TYPE_VOLUNTEER, interactor.getUser().getId(), null, null), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToProfileView(News news) {
        if (news.isAs_group())
            Tools.replaceView(view, OrganisationView.newInstance(news.getGroup_id(),news.getGroup_name()), Animation.FADE_IN_OUT, false);
        else
            Tools.replaceView(view, ProfileView.newInstance(news.getVolunteer_id()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToCommentView(News news) {
        Tools.replaceView(view, CommentView.newInstance(news.getId()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetNews(List<News> newsList) {
        view.updateRecyclerViewData(newsList);
        view.hideProgress();
    }
}
