package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Guest;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests.OrganisationEventGuestsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

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
    public void upgrade(int id, String rights, final int rvPosition) {
        User user = interactor.getUser();
        int eventId = interactor.getEventId();
        String newRights;

        if (rights.equals(Organisation.ORGANISATION_ADMIN))
            newRights = Organisation.ORGANISATION_MEMBER;
        else
            newRights = Organisation.ORGANISATION_ADMIN;

        view.showProgress();
        Ion.with(view.getContext())
                .load("PUT", Network.API_LOCATION + Network.API_REQUEST_GUESTS_UPGRADE)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("volunteer_id", String.valueOf(id))
                .addQuery("event_id", String.valueOf(eventId))
                .addQuery("rights", newRights)
                .as(new TypeToken<JsonObject>(){})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        view.hideProgress();
                        if (error == null)
                            view.upgrade(rvPosition);
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void kick(int id, final int rvPosition) {
        User user = interactor.getUser();
        int eventId = interactor.getEventId();
        Ion.with(view.getContext())
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_GUESTS_KICK)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("volunteer_id", String.valueOf(id))
                .addQuery("event_id", String.valueOf(eventId))
                .as(new TypeToken<JsonObject>(){})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        if (error == null)
                            view.kick(rvPosition);
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
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
