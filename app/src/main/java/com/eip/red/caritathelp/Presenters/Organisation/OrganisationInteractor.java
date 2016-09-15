package com.eip.red.caritathelp.Presenters.Organisation;

import android.content.Context;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.NewsListJson;
import com.eip.red.caritathelp.Models.Organisation.Membership;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.Organisation.OrganisationJson;
import com.eip.red.caritathelp.R;
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
    private Organisation    organisation;
    private int             organisationId;

    public OrganisationInteractor(Context context, String token, int organisationId) {
        this.context = context;
        this.token = token;
        organisation = null;
        this.organisationId = organisationId;
    }

    public void getData(final IOnOrganisationFinishedListener listener) {
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
                                listener.onDialog("Statut 400", result.getMessage());
                            else {
                                organisation = result.getResponse();
                                System.out.println("*****************************************Organisation ID : " + organisation.getId());
                                getNews(listener, result.getResponse());
                            }
//                                listener.onSuccessGetOrganisation(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void getNews(final IOnOrganisationFinishedListener listener, final Organisation organisation) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_BY_ID + organisationId + Network.API_REQUEST_ORGANISATION_NEWS)
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
                                listener.onSuccessGetData(organisation, result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }

    public void getNews(final IOnOrganisationFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_BY_ID + organisationId + Network.API_REQUEST_ORGANISATION_NEWS)
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

//    public void getNews(final IOnOrganisationFinishedListener listener, final Organisation organisation) {
//        JsonObject json = new JsonObject();
//
//        json.addProperty("token", token);
//
//        Ion.with(context)
//                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_BY_ID + organisationId + Network.API_REQUEST_ORGANISATION_NEWS)
//                .setJsonObjectBody(json)
//                .as(new TypeToken<OrganisationNewsJson>(){})
//                .setCallback(new FutureCallback<OrganisationNewsJson>() {
//                    @Override
//                    public void onCompleted(Exception error, OrganisationNewsJson result) {
//                        if (error == null) {
//                            // Status == 400 == error
//                            if (result.getStatus() == Network.API_STATUS_ERROR)
//                                listener.onDialogError("Statut 400", result.getMessage());
//                            else
//                                listener.onSuccessGetData(organisation, result.getResponse());
//                        }
//                        else
//                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
//                    }
//                });
//    }

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
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessLeave();
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
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
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessJoin();
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
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
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessReply(acceptance);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public Organisation getOrganisation() {
        return organisation;
    }
}
