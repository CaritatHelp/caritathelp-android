package com.eip.red.caritathelp.Presenters.Organisation;

import android.content.Context;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Membership;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.Organisation.OrganisationJson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 11/03/2016.
 */

public class OrganisationInteractor {

    private Context         context;
    private String          token;
    private int             organisationId;

    public OrganisationInteractor(Context context, String token, int organisationId) {
        this.context = context;
        this.token = token;
        this.organisationId = organisationId;
    }

    public void getOrganisation(final IOnOrganisationFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_BY_ID + organisationId)
                .setJsonObjectBody(json)
                .as(new TypeToken<OrganisationJson>(){})
                .setCallback(new FutureCallback<OrganisationJson>() {
                    @Override
                    public void onCompleted(Exception error, OrganisationJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetOrganisation(result.getResponse());
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void leave(final IOnOrganisationFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);
        json.addProperty("assoc_id", organisationId);

        Ion.with(context)
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_LEAVE)
                .setJsonObjectBody(json)
                .as(new TypeToken<Membership>(){})
                .setCallback(new FutureCallback<Membership>() {
                    @Override
                    public void onCompleted(Exception error, Membership result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.getMessage());
                            else
                                listener.onSuccessLeave();
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void join(final IOnOrganisationFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);
        json.addProperty("assoc_id", organisationId);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_JOIN)
                .setJsonObjectBody(json)
                .as(new TypeToken<Membership>(){})
                .setCallback(new FutureCallback<Membership>() {
                    @Override
                    public void onCompleted(Exception error, Membership result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.getMessage());
                            else
                                listener.onSuccessJoin();
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void reply(final IOnOrganisationFinishedListener listener, final String acceptance) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);
//        json.addProperty("notif_id", notif_id);
        json.addProperty("acceptance", acceptance);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_REPLY_INVITE)
                .setJsonObjectBody(json)
                .as(new TypeToken<Membership>(){})
                .setCallback(new FutureCallback<Membership>() {
                    @Override
                    public void onCompleted(Exception error, Membership result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.getMessage());
                            else
                                listener.onSuccessReply(acceptance);
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void getNews(IOnOrganisationFinishedListener listener) {

    }

    public int getOrganisationId() {
        return (organisationId);
    }

}
