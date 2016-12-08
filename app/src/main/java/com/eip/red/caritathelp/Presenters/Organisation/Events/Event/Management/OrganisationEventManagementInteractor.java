package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.EventInformations;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Management.EventCreation.IOnOrganisationEventCreationFinishedListener;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.HashMap;

/**
 * Created by pierr on 14/04/2016.
 */

public class OrganisationEventManagementInteractor {

    static final private String     ERROR_MANDATORY = "Ce champ est obligatoire";

    private Context context;
    private User    user;
    private int     eventId;

    public OrganisationEventManagementInteractor(Context context, User user, int eventId) {
        this.context = context;
        this.user = user;
        this.eventId = eventId;
    }

    public void getEvent(ProgressBar progressBar, final IOnOrganisationEventManagementFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENTS_INFORMATIONS + eventId)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .progressBar(progressBar)
                .as(new TypeToken<EventInformations>(){})
                .setCallback(new FutureCallback<EventInformations>() {
                    @Override
                    public void onCompleted(Exception error, EventInformations result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetEvent(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void saveEventModifications(final IOnOrganisationEventManagementFinishedListener listener, ProgressBar progressBar, HashMap<String, String> data) {
        JsonObject json = new JsonObject();
        json.addProperty("title", data.get("title"));
        json.addProperty("description", data.get("description"));
        json.addProperty("place", data.get("location"));
        json.addProperty("begin", data.get("date begin"));
        json.addProperty("end", data.get("date end"));

        Ion.with(context)
                .load("PUT", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT_MANAGEMENT + eventId)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .progressBar(progressBar)
                .setJsonObjectBody(json)
                .as(new TypeToken<EventInformations>(){})
                .setCallback(new FutureCallback<EventInformations>() {
                    @Override
                    public void onCompleted(Exception error, EventInformations result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessSaveEventModifications(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

}
