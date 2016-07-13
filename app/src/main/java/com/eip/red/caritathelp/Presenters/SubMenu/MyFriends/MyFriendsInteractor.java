package com.eip.red.caritathelp.Presenters.SubMenu.MyFriends;

import android.content.Context;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Friends.FriendsJson;
import com.eip.red.caritathelp.Models.Friends.FriendInvitation;
import com.eip.red.caritathelp.Models.Friends.FriendsInvitationsJson;
import com.eip.red.caritathelp.Models.Friendship;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 19/04/2016.
 */

public class MyFriendsInteractor {

    private Context context;
    private User    mainUser;
    private int     userId;

    public MyFriendsInteractor(Context context, User mainUser, int userId) {
        this.context = context;
        this.mainUser = mainUser;
        this.userId = userId;
    }

    public void getMyFriends(final IOnMyFriendsFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_VOLUNTEER + userId + Network.API_REQUEST_FRIENDSHIP)
                .setJsonObjectBody(json)
                .as(new TypeToken<FriendsJson>(){})
                .setCallback(new FutureCallback<FriendsJson>() {
                    @Override
                    public void onCompleted(Exception error, FriendsJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetMyFriends(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void getInvitations(final String sent, final IOnMyFriendsFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());
        json.addProperty("sent", sent);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_FRIEND_REQUESTS)
                .setJsonObjectBody(json)
                .as(new TypeToken<FriendsInvitationsJson>(){})
                .setCallback(new FutureCallback<FriendsInvitationsJson>() {
                    @Override
                    public void onCompleted(Exception error, FriendsInvitationsJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetInvitations(result.getResponse(), sent);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void blockFriend() {

    }

    public void removeFriend(int unfriendId, ProgressBar progressBar, final IOnMyFriendsFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());
        json.addProperty("id", unfriendId);

        Ion.with(context)
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_REMOVE)
                .progressBar(progressBar)
                .setJsonObjectBody(json)
                .as(new TypeToken<Friendship>(){})
                .setCallback(new FutureCallback<Friendship>() {
                    @Override
                    public void onCompleted(Exception error, Friendship result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessRemoveFriend();
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void invitationReply(final FriendInvitation friendInvitation, final String acceptance, final IOnMyFriendsFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());
        json.addProperty("notif_id", String.valueOf(friendInvitation.getNotif_id()));
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
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessInvitationReply(friendInvitation, acceptance);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });

    }

    public int getMainUserId() {
        return mainUser.getId();
    }

    public int getUserId() {
        return userId;
    }

}
