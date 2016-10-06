package com.eip.red.caritathelp.Presenters.Search;

import android.content.Context;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Friendship;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Search.Search;
import com.eip.red.caritathelp.Models.Search.SearchJson;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 21/04/2016.
 */

public class MySearchInteractor {

    private Context context;
    private User    user;

    public MySearchInteractor(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void search(String query, ProgressBar progressBar, final IOnMySearchFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("research", query);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_SEARCH )
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .progressBar(progressBar)
                .setJsonObjectBody(json)
                .as(new TypeToken<SearchJson>(){})
                .setCallback(new FutureCallback<SearchJson>() {
                    @Override
                    public void onCompleted(Exception error, SearchJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessSearch(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void addFriend(final Search search, final IOnMySearchFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("volunteer_id", String.valueOf(search.getId()));

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_ADD)
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
                                listener.onSuccessAdd(search);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public int getUserId() {
        return (user.getId());
    }
}
