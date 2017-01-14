package com.eip.red.caritathelp.Presenters.Organisation.Management;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Search.SearchJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.User.UsersJson;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Management.members.invite.Invite;
import com.eip.red.caritathelp.Views.Organisation.Management.members.invite.InviteView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by pierr on 14/01/2017.
 */

public class InvitePresenter implements Invite.Presenter {

    private InviteView view;
    private User user;
    private int organisationId;

    public InvitePresenter(InviteView view, User user, int organisationId) {
        this.view = view;
        this.user = user;
        this.organisationId = organisationId;
    }

    @Override
    public void getVolunteers() {
        view.showProgress();
        Ion.with(view.getContext())
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_BY_ID + organisationId + Network.API_REQUEST_ORGANISATION_INVITABLE_VOLUNTEERS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("id", String.valueOf(organisationId))
                .as(new TypeToken<UsersJson>(){})
                .setCallback(new FutureCallback<UsersJson>() {
                    @Override
                    public void onCompleted(Exception error, UsersJson result) {
                        view.hideProgress();
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                view.setDialog("Statut 400", result.getMessage());
                            else
                                view.update(result.getResponse());
                        }
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void send() {
        List<User> volunteers = view.getAdapter().getVolunteers();

        if (volunteers.size() > 0) {
            for (User volunteer : volunteers) {
                if (volunteer.isCheck()) {
                    view.showProgress();
                    Ion.with(view.getContext())
                            .load("POST", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_INVITE)
                            .setHeader("access-token", user.getToken())
                            .setHeader("client", user.getClient())
                            .setHeader("uid", user.getUid())
                            .addQuery("volunteer_id", String.valueOf(volunteer.getId()))
                            .addQuery("assoc_id", String.valueOf(organisationId))
                            .as(new TypeToken<JsonObject>(){})
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception error, JsonObject result) {
                                    view.hideProgress();
                                    if (error != null)
                                        view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                                }
                            });
                }
            }
            new AlertDialog.Builder(view.getContext())
                    .setMessage("Invitations envoyées")
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            dialogInterface.dismiss();
                            view.getActivity().onBackPressed();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void goToProfileView(int id) {
        Tools.replaceView(view, ProfileView.newInstance(id), Animation.FADE_IN_OUT, false);
    }
}
