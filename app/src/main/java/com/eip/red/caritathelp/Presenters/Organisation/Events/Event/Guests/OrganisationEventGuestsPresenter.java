package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Guest;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests.OrganisationEventGuestsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 15/04/2016.
 */

public class OrganisationEventGuestsPresenter implements IOrganisationEventGuestsPresenter, IOnOrganisationEventGuestsFinishedListener {

    private OrganisationEventGuestsView         view;
    private OrganisationEventGuestsInteractor   interactor;
    private boolean owner;

    public OrganisationEventGuestsPresenter(OrganisationEventGuestsView view, User user, int eventId, boolean owner) {
        this.view = view;
        this.owner = owner;
        interactor = new OrganisationEventGuestsInteractor(view.getContext(), user, eventId);
    }

    @Override
    public void getGuests() {
        view.showProgress();
        interactor.getGuests(this, view.getProgressBar());
    }

    @Override
    public void upgrade(int id, String rights, int rvPosition) {

    }

    @Override
    public void kick(int id, int rvPosition) {

    }

    @Override
    public void goToProfileView(int id) {
        Tools.replaceView(view, ProfileView.newInstance(id), Animation.FADE_IN_OUT, false);
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

    public boolean isOwner() {
        return owner;
    }

}
