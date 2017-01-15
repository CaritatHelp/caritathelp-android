package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management;

import android.support.v7.app.AlertDialog;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Guests;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.invitations.Invitations;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.invitations.InvitationsView;
import com.eip.red.caritathelp.Views.Organisation.Management.members.invite.InviteView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 15/01/2017.
 */

public class InvitationsPresenter implements Invitations.Presenter {

    private InvitationsView view;
    private User user;
    private int eventId;

    public InvitationsPresenter(InvitationsView view, User user, int eventId) {
        this.view = view;
        this.user = user;
        this.eventId = eventId;
    }

    @Override
    public void getInvitationsSent() {
        view.showProgress();
        Ion.with(view.getContext())
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_GUESTS_INVITED)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("event_id", String.valueOf(eventId))
                .as(new TypeToken<Guests>(){})
                .setCallback(new FutureCallback<Guests>() {
                    @Override
                    public void onCompleted(Exception error, Guests result) {
                        view.hideProgress();
                        if (error == null) {
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
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_GUESTS_WAITING)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("event_id", String.valueOf(eventId))
                .as(new TypeToken<Guests>(){})
                .setCallback(new FutureCallback<Guests>() {
                    @Override
                    public void onCompleted(Exception error, Guests result) {
                        view.hideProgress();
                        if (error == null) {
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
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_GUESTS_REPLY)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("notif_id", String.valueOf(id))
                .addQuery("acceptance", String.valueOf(acceptance))
                .as(new TypeToken<JsonObject>(){})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
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
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_GUESTS_UNINVITE)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("event_id", String.valueOf(eventId))
                .addQuery("volunteer_id", String.valueOf(id))
                .as(new TypeToken<JsonObject>(){})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
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
        Tools.replaceView(view, InviteView.newInstance(eventId, false), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToProfileView(int id) {
        Tools.replaceView(view, ProfileView.newInstance(id), Animation.FADE_IN_OUT, false);
    }
}
