package com.eip.red.caritathelp.Presenters.Notifications;

import android.content.Context;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Friendship;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Notifications.Notification;
import com.eip.red.caritathelp.Models.Notifications.NotificationsJson;
import com.eip.red.caritathelp.Models.User.User;
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
        JsonObject json = new JsonObject();

        json.addProperty("token", user.getToken());

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_NOTIFICATIONS)
                .progressBar(progressBar)
                .setJsonObjectBody(json)
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

        json.addProperty("token", user.getToken());
        json.addProperty("notif_id", String.valueOf(notification.getId()));
        json.addProperty("acceptance", acceptance);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_REPLY)
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
                                listener.onSuccessFriendshipReply(notification, acceptance);
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });

    }
}
