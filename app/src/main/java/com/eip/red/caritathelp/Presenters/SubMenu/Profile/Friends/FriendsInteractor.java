package com.eip.red.caritathelp.Presenters.SubMenu.Profile.Friends;

import android.content.Context;

import com.eip.red.caritathelp.Models.Friends.FriendsJson;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 13/07/2016.
 */

public class FriendsInteractor {

    private Context context;
    private int     profileId;
    private User    user;

    public FriendsInteractor(Context context, int profileId, User user) {
        this.context = context;
        this.profileId = profileId;
        this.user = user;
    }

    public void getFriends(final IOnFriendsFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_VOLUNTEER + profileId + Network.API_REQUEST_FRIENDSHIP)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<FriendsJson>(){})
                .setCallback(new FutureCallback<FriendsJson>() {
                    @Override
                    public void onCompleted(Exception error, FriendsJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetFriends(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }
}
