package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.User.UsersJson;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.emergency.EmergencyInvitations;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.emergency.EmergencyInvitationsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 16/01/2017.
 */

public class EmergencyInvitationsPresenter implements EmergencyInvitations.Presenter {

    private EmergencyInvitationsView view;
    private User user;
    private int eventId;

    public EmergencyInvitationsPresenter(EmergencyInvitationsView view, User user, int eventId) {
        this.view = view;
        this.user = user;
        this.eventId = eventId;
    }


    @Override
    public void getVolunteers() {
        Ion.with(view.getContext())
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT + eventId + Network.API_REQUEST_ORGANISATION_EVENT_EMERGENCY_INVITATIONS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("id", String.valueOf(eventId))
                .as(new TypeToken<UsersJson>(){})
                .setCallback(new FutureCallback<UsersJson>() {
                    @Override
                    public void onCompleted(Exception error, UsersJson result) {
                        if (error == null) {
                            if (result.getStatus() != Network.API_STATUS_ERROR)
                                view.update(result.getResponse());
                        }
                        else
                            view.setDialog("Problème de connection", view.getString(R.string.connection_problem));
                    }
                });
    }

    @Override
    public void goToProfileView(int id) {
        Tools.replaceView(view, ProfileView.newInstance(id), Animation.FADE_IN_OUT, false);
    }
}
