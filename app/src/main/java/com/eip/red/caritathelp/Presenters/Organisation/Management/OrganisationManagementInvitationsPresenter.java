package com.eip.red.caritathelp.Presenters.Organisation.Management;

import android.support.v7.app.AlertDialog;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Members;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Management.members.OrganisationManagementInvitations;
import com.eip.red.caritathelp.Views.Organisation.Management.members.OrganisationManagementInvitationsView;
import com.eip.red.caritathelp.Views.Organisation.Management.members.invite.InviteView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 12/01/2017.
 */

public class OrganisationManagementInvitationsPresenter implements OrganisationManagementInvitations.Presenter {

    private OrganisationManagementInvitationsView view;
    private User user;
    private int organisationId;

    public OrganisationManagementInvitationsPresenter(OrganisationManagementInvitationsView view, User user, int organisationId) {
        this.view = view;
        this.user = user;
        this.organisationId = organisationId;
    }

    @Override
    public void getInvitationsSent() {
        view.showProgress();
        Ion.with(view.getContext())
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_INVITED)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("assoc_id", String.valueOf(organisationId))
                .as(new TypeToken<Members>(){})
                .setCallback(new FutureCallback<Members>() {
                    @Override
                    public void onCompleted(Exception error, Members result) {
                        view.hideProgress();
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                view.setDialog("Statut 400", result.getMessage());
                            else
                                view.updateInvitSent(result.getResponse());
                        }
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void getInvitationsWaiting() {
        view.showProgress();
        Ion.with(view.getContext())
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_WAITING)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("assoc_id", String.valueOf(organisationId))
                .as(new TypeToken<Members>(){})
                .setCallback(new FutureCallback<Members>() {
                    @Override
                    public void onCompleted(Exception error, Members result) {
                        view.hideProgress();
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                view.setDialog("Statut 400", result.getMessage());
                            else
                                view.updateInvitWaiting(result.getResponse());
                        }
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void join(int id, final boolean acceptance, final int rvPosition) {
        view.showProgress();
        Ion.with(view.getContext())
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_REPLY_MEMBER)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("notif_id", String.valueOf(id))
                .addQuery("acceptance", String.valueOf(acceptance))
                .as(new TypeToken<JsonObject>(){})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        System.out.println("RESULT : " + result);
                        view.hideProgress();
                        if (error == null) {
                            String msg;
                            if (acceptance)
                                msg = "Invitation acceptée";
                            else
                                msg = "Invitation refusée";

                            new AlertDialog.Builder(view.getContext())
                                    .setMessage(msg)
                                    .show();

                            view.updateInvitWaiting(rvPosition);
                        }
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void uninvite(int id, final int rvPosition) {
        view.showProgress();
        Ion.with(view.getContext())
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_UNINVITE)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("assoc_id", String.valueOf(organisationId))
                .addQuery("volunteer_id", String.valueOf(id))
                .as(new TypeToken<JsonObject>(){})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        System.out.println("RESULT : " + result);
                        view.hideProgress();
                        if (error == null) {
                            new AlertDialog.Builder(view.getContext())
                                    .setMessage("Invitation annulée")
                                    .show();

                            view.updateInvitSent(rvPosition);
                        }
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void goToSendInvitationView() {
        Tools.replaceView(view, InviteView.newInstance(organisationId), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToProfileView(int id) {
        Tools.replaceView(view, ProfileView.newInstance(id), Animation.FADE_IN_OUT, false);
    }
}
