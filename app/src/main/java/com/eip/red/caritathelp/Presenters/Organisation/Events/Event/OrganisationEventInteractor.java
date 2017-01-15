package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;

import android.content.Context;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.NewsListJson;
import com.eip.red.caritathelp.Models.Organisation.EmergencyJson;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.EventInformations;
import com.eip.red.caritathelp.Models.Organisation.Events;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Events.IOnOrganisationEventsFinishedListener;
import com.eip.red.caritathelp.R;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventInteractor {

    private Context context;
    private User    user;
    private Event   event;
    private int     eventId;

    public OrganisationEventInteractor(Context context, User user, int eventId) {
        this.context = context;
        this.user = user;
        this.eventId = eventId;
    }

    public void getData(final IOnOrganisationEventFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENTS_INFORMATIONS + eventId)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<EventInformations>(){})
                .setCallback(new FutureCallback<EventInformations>() {
                    @Override
                    public void onCompleted(Exception error, EventInformations result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else {
                                event = result.getResponse();
                                getNews(listener, result.getResponse());
                            }
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void getNews(final IOnOrganisationEventFinishedListener listener, final Event event) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT+ eventId + Network.API_REQUEST_ORGANISATION_NEWS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<NewsListJson>(){})
                .setCallback(new FutureCallback<NewsListJson>() {
                    @Override
                    public void onCompleted(Exception error, NewsListJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetData(event, result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }

    public void getNews(final IOnOrganisationEventFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT+ eventId + Network.API_REQUEST_ORGANISATION_NEWS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<NewsListJson>(){})
                .setCallback(new FutureCallback<NewsListJson>() {
                    @Override
                    public void onCompleted(Exception error, NewsListJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetNews(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }

    public void raiseEmergency(final IOnOrganisationEventFinishedListener listener, String volunteers, String zone) {
        JsonObject json = new JsonObject();
        json.addProperty("number_volunteers", Integer.valueOf(volunteers));
        json.addProperty("zone", Integer.valueOf(zone.replaceAll("km", "").trim()));

        Ion.with(context)
                .load("POST", Network.API_LOCATION_2 + Network.API_REQUEST_ORGANISATION_EVENT + eventId + Network.API_REQUEST_ORGANISATION_EVENT_EMERGENCY + "?number_volunteers=17&zone=50")
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
//                .addQuery("number_volunteers", volunteers)
//                .addQuery("zone", zone.replaceAll("km", "").trim())
//                .setJsonObjectBody(json)
                .as(new TypeToken<EmergencyJson>(){})
                .setCallback(new FutureCallback<EmergencyJson>() {
                    @Override
                    public void onCompleted(Exception error, EmergencyJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                        } else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }

    public Event getEvent() {
        return event;
    }

    public boolean isHost() {
        return event.getRights().contains(Organisation.ORGANISATION_HOST) || event.getRights().contains(Organisation.ORGANISATION_ADMIN);
    }
}
