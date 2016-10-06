package com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations;

import android.content.Context;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.Organisation.Organisations;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 24/02/2016.
 */

public class MyOrganisationsInteractor {

    private Context     context;
    private User        mainUser;
    private int         userId;

    public MyOrganisationsInteractor(Context context, User mainUser, int userId) {
        this.context = context;
        this.mainUser = mainUser;
        this.userId = userId;
    }

    public void getMyOrganisations(ProgressBar progressBar, final IOnMyOrganisationsFinishedListener listener, final boolean isSwipeRefresh) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS + userId + Network.API_REQUEST_GET_MY_ORGANISATIONS)
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
                .progressBar(progressBar)
                .as(new TypeToken<Organisations>() {
                })
                .setCallback(new FutureCallback<Organisations>() {
                    @Override
                    public void onCompleted(Exception error, Organisations result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage(), isSwipeRefresh);
                            else
                                listener.onSuccess(getOrganisationsByProfile(result.getResponse(), "owner"), getOrganisationsByProfile(result.getResponse(), "member"), isSwipeRefresh);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet", isSwipeRefresh);
                    }
                });
    }

    private List<Organisation>  getOrganisationsByProfile(List<Organisation> organisations, String profile) {
        List<Organisation>  newList = new ArrayList<>();

        for (Organisation organisation : organisations) {
            if (organisation.getRights().equals(profile))
                newList.add(organisation);
        }

        return (newList);
    }

    public int getMainUserId() {
        return mainUser.getId();
    }

    public int getUserId() {
        return userId;
    }
}
