package com.eip.red.caritathelp.Presenters.SubMenu.Profile.Friends;

import android.content.Context;

import com.eip.red.caritathelp.Models.Friends.FriendsJson;
import com.eip.red.caritathelp.Models.Network;
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
    private String  token;

    public FriendsInteractor(Context context, int profileId, String token) {
        this.context = context;
        this.profileId = profileId;
        this.token = token;
    }

    public void getFriends(final IOnFriendsFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_VOLUNTEER + profileId + Network.API_REQUEST_FRIENDSHIP)
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
                                listener.onSuccessGetFriends(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }
}
