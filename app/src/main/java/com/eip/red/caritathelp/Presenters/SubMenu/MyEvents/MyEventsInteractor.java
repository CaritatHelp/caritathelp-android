package com.eip.red.caritathelp.Presenters.SubMenu.MyEvents;

import android.content.Context;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.Events;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.Organisation.Organisations;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */

public class MyEventsInteractor {

    private Context context;
    private User    mainUser;
    private int     userId;

    List<Organisation> myOrganisationsOwner;

    public MyEventsInteractor(Context context, User mainUser, int userId) {
        this.context = context;
        this.mainUser = mainUser;
        this.userId = userId;
    }

    public void getMyEvents(final IOnMyEventsFinishedListener listener, final boolean init, String range, final boolean swipeRefresh) {
        JsonObject json = new JsonObject();
        json.addProperty("range", range);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_MY_EVENTS + userId + Network.API_REQUEST_ORGANISATION_EVENTS)
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
                .setJsonObjectBody(json)
                .as(new TypeToken<Events>(){})
                .setCallback(new FutureCallback<Events>() {
                    @Override
                    public void onCompleted(Exception error, Events result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage(), swipeRefresh);
                            else {
                                if (init) {
                                    if (mainUser.getId() == userId)
                                        getMyOrganisations(listener, result.getResponse());
                                    else
                                        listener.onSuccessGetMyEventsInit(result.getResponse(), false);
                                }
                                else
                                    listener.onSuccessGetMyEvents(result.getResponse(), swipeRefresh);
                            }
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet", swipeRefresh);
                    }
                });
    }


    private List<Event> getEventsByProfile(List<Event> events, String profile) {
        List<Event>  newList = new ArrayList<>();

        for (Event event : events) {
            if (event.getRights().equals(profile))
                newList.add(event);
        }

        return (newList);
    }

    public void getMyOrganisations(final IOnMyEventsFinishedListener listener, final List<Event> events) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS + userId + Network.API_REQUEST_GET_MY_ORGANISATIONS)
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
                .as(new TypeToken<Organisations>() {})
                .setCallback(new FutureCallback<Organisations>() {
                    @Override
                    public void onCompleted(Exception error, Organisations result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage(), false);
                            else {
                                List<Organisation> myOrganisationsOwner = getOrganisationsByProfile(result.getResponse(), "owner");
                                listener.onSuccessGetMyEventsInit(events, myOrganisationsOwner.size() > 0);
                            }
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet", false);
                    }
                });
    }

    public void getMyOrganisations(final IOnMyEventsFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS + userId + Network.API_REQUEST_GET_MY_ORGANISATIONS)
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
                .as(new TypeToken<Organisations>() {
                })
                .setCallback(new FutureCallback<Organisations>() {
                    @Override
                    public void onCompleted(Exception error, Organisations result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage(), false);
                            else
                                listener.onSuccessGetMyOrganisations(getOrganisationsByProfile(result.getResponse(), "owner"));
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet", false);
                    }
                });
    }

    private List<Organisation>  getOrganisationsByProfile(List<Organisation> organisations, String profile) {
        List<Organisation>  newList = new ArrayList<>();

        for (Organisation organisation : organisations) {
            if (organisation.getRights().equals(profile))
                newList.add(organisation);
        }

        return newList;
    }

    public int getMainUserId() {
        return mainUser.getId();
    }

    public int getUserId() {
        return userId;
    }
}
