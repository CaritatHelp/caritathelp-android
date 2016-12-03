package com.eip.red.caritathelp.Presenters.Organisation;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Shelter.SheltersJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Views.Organisation.Shelters.Shelters;
import com.eip.red.caritathelp.Views.Organisation.Shelters.SheltersView;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 03/12/2016.
 */

public class SheltersPresenter implements Shelters.Presenter {

    private SheltersView view;
    private User user;
    private int organisationId;

    public SheltersPresenter(SheltersView view, User user, int organisationId) {
        this.view = view;
        this.user = user;
        this.organisationId = organisationId;
    }

    @Override
    public void getShelters() {
        view.showProgress();

        Ion.with(view.getContext())
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_BY_ID + organisationId + Network.API_REQUEST_ORGANISATION_SHELTERS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<SheltersJson>(){})
                .setCallback(new FutureCallback<SheltersJson>() {
                    @Override
                    public void onCompleted(Exception error, SheltersJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                view.setDialog("Statut 400", result.getMessage());
                            else
                                view.updateRV(result.getResponse());
                        }
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }
}
