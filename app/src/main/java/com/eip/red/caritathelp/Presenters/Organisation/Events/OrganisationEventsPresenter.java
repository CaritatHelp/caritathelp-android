package com.eip.red.caritathelp.Presenters.Organisation.Events;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.Organisation.Events.OrganisationEventsView;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */
public class OrganisationEventsPresenter implements IOrganisationEventsPresenter, IOnOrganisationEventsFinishedListener {

    private OrganisationEventsView          view;
    private OrganisationEventsInteractor    interactor;

    public OrganisationEventsPresenter(OrganisationEventsView view, User user, int organisationId) {
        this.view = view;
        interactor = new OrganisationEventsInteractor(view.getContext(), user, organisationId);
    }

    @Override
    public void getEvents() {
        view.showProgress();
        interactor.getEvents(this);
    }

    @Override
    public void navigateToEventView(Event event) {
        Tools.replaceView(view, OrganisationEventView.newInstance(event.getId(), event.getTitle()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccess(List<Event> events) {
        view.getAdapter().update(events);
        view.hideProgress();
    }
}
