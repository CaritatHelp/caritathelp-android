package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;

import android.view.View;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Home.Comment.CommentView;
import com.eip.red.caritathelp.Views.Home.Post.PostView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests.OrganisationEventGuestsView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Informations.OrganisationEventInformationsView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.OrganisationEventManagementView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventPresenter implements IOrganisationEventPresenter, IOnOrganisationEventFinishedListener {

    private OrganisationEventView           view;
    private OrganisationEventInteractor     interactor;

    public OrganisationEventPresenter(OrganisationEventView view, String token, int eventId) {
        this.view = view;

        // Init Interactor
        interactor = new OrganisationEventInteractor(view.getContext(), token, eventId);
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.btn_post:
                Event   event = interactor.getEvent();
                Tools.replaceView(view, PostView.newInstance(News.GROUP_TYPE_EVENT, event.getId(), event.getTitle(), event.getRights()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_join:
                break;
            case R.id.btn_quit:
                break;
            case R.id.btn_guests:
                Tools.replaceView(view, OrganisationEventGuestsView.newInstance(interactor.getEvent().getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_informations:
                Tools.replaceView(view, OrganisationEventInformationsView.newInstance(interactor.getEvent().getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_management:
                Tools.replaceView(view, OrganisationEventManagementView.newInstance(interactor.getEvent().getId()), Animation.FADE_IN_OUT, false);
                break;
        }
    }

    @Override
    public void getData() {
        view.showProgress();
        interactor.getData(this);
    }

    @Override
    public void getNews(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();
        interactor.getNews(this);
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
    public void onSuccessGetData(Event event, List<News> newsList) {
        String  rights = event.getRights();

        if (rights != null) {
            switch (rights) {
                case "admin":
                    view.getManagementBtn().setVisibility(View.VISIBLE);
                    break;
                case "host":
                    view.getManagementBtn().setVisibility(View.VISIBLE);
                    break;
                case "member":
                    view.getQuitBtn().setVisibility(View.VISIBLE);
                    break;
            }
        }
        else
            view.getJoinBtn().setVisibility(View.VISIBLE);

        view.updateRV(newsList);
        view.getActivity().setTitle(Tools.upperCaseFirstLetter(event.getTitle()));
        view.getArguments().putString("page", event.getTitle());
        view.hideProgress();
    }

    @Override
    public void onSuccessGetNews(List<News> newsList) {
        view.updateRV(newsList);
        view.hideProgress();
    }
}
