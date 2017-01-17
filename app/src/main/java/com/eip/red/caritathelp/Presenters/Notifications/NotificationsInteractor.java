package com.eip.red.caritathelp.Presenters.Notifications;

import android.content.Context;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Friendship;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Notifications.Notification;
import com.eip.red.caritathelp.Models.Notifications.NotificationsJson;
import com.eip.red.caritathelp.Models.User.EventInvitation;
import com.eip.red.caritathelp.Models.User.OrganisationInvitation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.Invitations.IOnInvitationsFinishedListener;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 21/04/2016.
 */

public class NotificationsInteractor {

    private Context context;
    private User    user;

    public NotificationsInteractor(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void getNotifications(ProgressBar progressBar, final IOnNotificationsFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_NOTIFICATIONS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<NotificationsJson>(){})
                .setCallback(new FutureCallback<NotificationsJson>() {
                    @Override
                    public void onCompleted(Exception error, NotificationsJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetNotifications(result.getResponse());
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void friendshipReply(final Notification notification, final String acceptance, final IOnNotificationsFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("notif_id", String.valueOf(notification.getId()));
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
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.getMessage());
                            else
                                listener.onSuccess(notification, acceptance);
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void organisationInvitationReply(final Notification notification, final String acceptance, final IOnNotificationsFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("notif_id", notification.getId());
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
                                listener.onDialogError("Statut 400", result.get("message").toString());
                            else
                                listener.onSuccess(notification, acceptance);
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
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

    public void emergencyReply(final Notification notification, final boolean acceptance, final IOnNotificationsFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("acceptance", acceptance);

        Ion.with(context)
                .load("PUT", Network.API_LOCATION + Network.API_REQUEST_NOTIFICATIONS + "/" + notification.getId() + Network.API_REQUEST_NOTIFICATIONS_REPLY_EMERGENCY)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        System.out.println("RESULT : " + result);
                        if (error == null) {
                            if (result.get("status").getAsInt() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.get("message").toString());
                            else {
                                if (acceptance)
                                    listener.onSuccess(notification, "true");
                                else
                                    listener.onSuccess(notification, "false");
                            }
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }


}
