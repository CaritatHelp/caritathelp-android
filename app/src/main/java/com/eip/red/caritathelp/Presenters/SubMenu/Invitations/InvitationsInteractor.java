package com.eip.red.caritathelp.Presenters.SubMenu.Invitations;

import android.content.Context;

import com.eip.red.caritathelp.Models.Friends.FriendInvitation;
import com.eip.red.caritathelp.Models.Friends.FriendsInvitationsJson;
import com.eip.red.caritathelp.Models.Friendship;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.EventInvitation;
import com.eip.red.caritathelp.Models.User.EventsInvitationsJson;
import com.eip.red.caritathelp.Models.User.OrganisationInvitation;
import com.eip.red.caritathelp.Models.User.OrganisationsInvitationsJson;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 04/07/2016.
 */

public class InvitationsInteractor {

    private Context context;
    private User    user;

    public InvitationsInteractor(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void getFriendsInvitations(final IOnInvitationsFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("sent", "default");

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_FRIEND_REQUESTS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .as(new TypeToken<FriendsInvitationsJson>(){})
                .setCallback(new FutureCallback<FriendsInvitationsJson>() {
                    @Override
                    public void onCompleted(Exception error, FriendsInvitationsJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetFriendsInvitations(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void getOrganisationsInvitations(final IOnInvitationsFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATIONS_INVITED)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<OrganisationsInvitationsJson>(){})
                .setCallback(new FutureCallback<OrganisationsInvitationsJson>() {
                    @Override
                    public void onCompleted(Exception error, OrganisationsInvitationsJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetOrganisationsInvitations(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void getEventsInvitations(final IOnInvitationsFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_EVENTS_INVITED)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<EventsInvitationsJson>(){})
                .setCallback(new FutureCallback<EventsInvitationsJson>() {
                    @Override
                    public void onCompleted(Exception error, EventsInvitationsJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetEventsInvitations(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void friendInvitationReply(final FriendInvitation friendInvitation, final String acceptance, final IOnInvitationsFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("notif_id", String.valueOf(friendInvitation.getNotif_id()));
        json.addProperty("acceptance", acceptance);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_REPLY)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .as(new TypeToken<Friendship>(){})
                .setCallback(new FutureCallback<Friendship>() {
                    @Override
                    public void onCompleted(Exception error, Friendship result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessFriendsInvitationReply(friendInvitation, acceptance);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });

    }

    public void organisationInvitationReply(final OrganisationInvitation organisationInvitation, final String acceptance, final IOnInvitationsFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("notif_id", organisationInvitation.getNotif_id());
        json.addProperty("acceptance", acceptance);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_REPLY_INVITE)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        if (error == null) {
                            if (result.get("status").getAsInt() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.get("message").toString());
                            else
                                listener.onSuccessOrganisationsInvitationReply(organisationInvitation, acceptance);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void eventsInvitationReply(final EventInvitation eventInvitation, final String acceptance, final IOnInvitationsFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("notif_id", eventInvitation.getNotif_id());
        json.addProperty("acceptance", acceptance);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_GUESTS_REPLY_INVITE)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.get("status").getAsInt() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.get("message").toString());
                            else
                                listener.onSuccessEventsInvitationReply(eventInvitation, acceptance);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

}
