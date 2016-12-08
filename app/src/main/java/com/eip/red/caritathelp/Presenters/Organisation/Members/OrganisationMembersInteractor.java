package com.eip.red.caritathelp.Presenters.Organisation.Members;

import android.content.Context;

import com.eip.red.caritathelp.Models.Organisation.Members;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 11/03/2016.
 */

public class OrganisationMembersInteractor {

    private Context context;
    private User    user;
    private int     organisationId;

    public OrganisationMembersInteractor(Context context, User user, int organisationId) {
        this.context = context;
        this.user = user;
        this.organisationId = organisationId;
    }

    public void getMembers(final IOnOrganisationMembersFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION + "/" + organisationId + Network.API_REQUEST_ORGANISATION_MEMBERS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<Members>(){})
                .setCallback(new FutureCallback<Members>() {
                    @Override
                    public void onCompleted(Exception error, Members result) {
                        if (error == null) {
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
