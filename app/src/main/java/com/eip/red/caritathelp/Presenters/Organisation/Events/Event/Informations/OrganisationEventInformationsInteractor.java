package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Informations;

import android.content.Context;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.EventInformations;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventInformationsInteractor {

    private Context context;
    private User    user;
    private int     eventId;

    public OrganisationEventInformationsInteractor(Context context, User user, int eventId) {
        this.context = context;
        this.user = user;
        this.eventId = eventId;
    }

    public void getEventInformations(final IOnOrganisationEventInformationsFinishedListener listener) {
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
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.getMessage());
                            else
                                listener.onSuccess(result.getResponse());
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

}
