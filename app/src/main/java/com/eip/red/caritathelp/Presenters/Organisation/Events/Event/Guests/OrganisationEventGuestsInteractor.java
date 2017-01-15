package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Guests;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 15/04/2016.
 */

public class OrganisationEventGuestsInteractor {

    private Context context;
    private User    user;
    private int     eventId;

    public OrganisationEventGuestsInteractor(Context context, User user, int eventId) {
        this.context = context;
        this.user = user;
        this.eventId = eventId;
    }

    public void getGuests(final IOnOrganisationEventGuestsFinishedListener listener, ProgressBar progressBar) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT + eventId + Network.API_REQUEST_ORGANISATION_EVENTS_GUESTS)
                .progressBar(progressBar)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<Guests>(){})
                .setCallback(new FutureCallback<Guests>() {
                    @Override
                    public void onCompleted(Exception error, Guests result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccess(result.getResponse());
                        }
                        else {
                            new AlertDialog.Builder(context).setMessage(error.toString()).show();
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                        }
                    }
                });
    }

    public User getUser() {
        return user;
    }

    public int getEventId() {
        return eventId;
    }
}
