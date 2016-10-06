package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Guest;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests.OrganisationEventGuestsView;

import java.util.List;

/**
 * Created by pierr on 15/04/2016.
 */

public class OrganisationEventGuestsPresenter implements IOrganisationEventGuestsPresenter, IOnOrganisationEventGuestsFinishedListener {

    private OrganisationEventGuestsView         view;
    private OrganisationEventGuestsInteractor   interactor;

    public OrganisationEventGuestsPresenter(OrganisationEventGuestsView view, User user, int eventId) {
        this.view = view;

        interactor = new OrganisationEventGuestsInteractor(view.getContext(), user, eventId);
    }

    @Override
    public void getGuests() {
        view.showProgress();
        interactor.getGuests(this, view.getProgressBar());
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccess(List<Guest> guests) {
        view.updateRecyclerView(guests);
        view.hideProgress();
    }
}
