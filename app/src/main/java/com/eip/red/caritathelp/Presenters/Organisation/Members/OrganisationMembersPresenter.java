package com.eip.red.caritathelp.Presenters.Organisation.Members;

import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Views.Organisation.Members.OrganisationMembersView;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by pierr on 11/03/2016.
 */

public class OrganisationMembersPresenter implements IOrganisationMembersPresenter, IOnOrganisationMembersFinishedListener {

    private OrganisationMembersView         view;
    private OrganisationMembersInteractor   interactor;
    private User user;
    private int organisationId;
    private boolean owner;

    public OrganisationMembersPresenter(OrganisationMembersView view, User user, int organisationId, boolean owner) {
        this.view = view;
        this.user = user;
        this.owner = owner;
        this.organisationId = organisationId;
        interactor = new OrganisationMembersInteractor(view.getActivity().getApplicationContext(), user, organisationId);
    }

    @Override
    public void getMembers() {
        view.showProgress();
        interactor.getMembers(this);
    }

    @Override
    public boolean isOwner() {
        return owner;
    }

    @Override
    public void upgrade(int id, String rights, final int position) {
        String newRights;

        if (rights.equals(Organisation.ORGANISATION_ADMIN))
            newRights = Organisation.ORGANISATION_MEMBER;
        else
            newRights = Organisation.ORGANISATION_ADMIN;

        view.showProgress();
        Ion.with(view.getContext())
                .load("PUT", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_UPGRADE)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("volunteer_id", String.valueOf(id))
                .addQuery("assoc_id", String.valueOf(organisationId))
                .addQuery("rights", newRights)
                .as(new TypeToken<JsonObject>(){})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        view.hideProgress();
                        if (error == null)
                            view.upgrade(position);
                        else
                            view.setDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void kick(int id, final int position) {
        Ion.with(view.getContext())
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_MEMBERSHIP_KICK)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("volunteer_id", String.valueOf(id))
                .addQuery("assoc_id", String.valueOf(organisationId))
                .as(new TypeToken<JsonObject>(){})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        System.out.println(" RESULT : " + result);
                        if (error == null)
                            view.kick(position);
                        else
                            view.setDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccess(List<Member> members) {
        view.updateListView(members);
        view.hideProgress();
    }
}
