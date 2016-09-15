package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;

import android.content.Context;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.NewsListJson;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.EventInformations;
import com.eip.red.caritathelp.Models.Organisation.Events;
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
    private String  token;
    private Event   event;
    private int     eventId;

    public OrganisationEventInteractor(Context context, String token, int eventId) {
        this.context = context;
        this.token = token;
        this.eventId = eventId;
    }

    public void getData(final IOnOrganisationEventFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENTS_INFORMATIONS + eventId)
                .setJsonObjectBody(json)
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
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT+ eventId + Network.API_REQUEST_ORGANISATION_NEWS)
                .setJsonObjectBody(json)
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
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT+ eventId + Network.API_REQUEST_ORGANISATION_NEWS)
                .setJsonObjectBody(json)
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

    public Event getEvent() {
        return event;
    }
}
