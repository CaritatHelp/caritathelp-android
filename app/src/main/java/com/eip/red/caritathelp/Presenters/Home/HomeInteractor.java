package com.eip.red.caritathelp.Presenters.Home;

import android.content.Context;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.NewsJson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 08/08/2016.
 */

public class HomeInteractor {

    private Context context;
    private String  token;

    public HomeInteractor(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public void getNews(final IOnHomeFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_NEWS)
                .setJsonObjectBody(json)
                .as(new TypeToken<NewsJson>(){})
                .setCallback(new FutureCallback<NewsJson>() {
                    @Override
                    public void onCompleted(Exception error, NewsJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetNews(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });

    }
}
