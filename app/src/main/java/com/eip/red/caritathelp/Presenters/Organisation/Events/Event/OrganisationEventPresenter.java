package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;

import android.view.View;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests.OrganisationEventGuestsView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Informations.OrganisationEventInformationsView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.OrganisationEventManagementView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;

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
            case R.id.btn_join:
                break;
            case R.id.btn_quit:
                break;
            case R.id.btn_guests:
                Tools.replaceView(view, OrganisationEventGuestsView.newInstance(interactor.getEventId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_informations:
                Tools.replaceView(view, OrganisationEventInformationsView.newInstance(interactor.getEventId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_management:
                Tools.replaceView(view, OrganisationEventManagementView.newInstance(interactor.getEventId()), Animation.FADE_IN_OUT, false);
                break;
        }
    }

    @Override
    public void getEvent() {
        view.showProgress();
        interactor.getEvent(view.getProgressBar(), this);
    }

    @Override
    public void getNews() {
        view.showProgress();
        interactor.getNews(this);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccessGetEvent(String title, String rights) {
        // Set Button Visibility
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
            case "none":
                view.getJoinBtn().setVisibility(View.VISIBLE);
                break;
        }

        // Init ToolBar Title
        view.getActivity().setTitle(Tools.upperCaseFirstLetter(title));

        // Update Bundle Name Page argument
        view.getArguments().putString("page", title);

        // Set Progress Bar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessGetNews(List<News> newsList) {
        view.hideProgress();
    }
}
