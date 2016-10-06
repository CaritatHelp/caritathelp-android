package com.eip.red.caritathelp.Presenters.SubMenu.Invitations;

import android.view.View;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Friends.FriendInvitation;
import com.eip.red.caritathelp.Models.User.EventInvitation;
import com.eip.red.caritathelp.Models.User.OrganisationInvitation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.SubMenu.Invitations.InvitationsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 04/07/2016.
 */

public class InvitationsPresenter implements IInvitationsPresenter, IOnInvitationsFinishedListener {

    private InvitationsView         view;
    private InvitationsInteractor   interactor;

    public InvitationsPresenter(InvitationsView view, User user) {
        this.view = view;
        interactor = new InvitationsInteractor(view.getContext(), user);
    }

    @Override
    public void getFriendsInvitations(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();

        interactor.getFriendsInvitations(this);
    }

    @Override
    public void getOrganisationsInvitations(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();

        interactor.getOrganisationsInvitations(this);
    }

    @Override
    public void getEventsInvitations(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();

        interactor.getEventsInvitations(this);
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.friends:
                view.showProgress();

                // Set RecyclerView Visibility
                view.getFriendsInvitationsRV().setVisibility(View.VISIBLE);
                view.getOrganisationsInvitationsRV().setVisibility(View.GONE);
                view.getEventsInvitationsRV().setVisibility(View.GONE);

                // Set TextStyle Tabs
                view.setTabsTypeface(InvitationsView.TAB_FRIENDS);

                // Update Data
                interactor.getFriendsInvitations(this);
                break;
            case R.id.organisations:
                view.showProgress();

                // Set RecyclerView Visibility
                view.getFriendsInvitationsRV().setVisibility(View.GONE);
                view.getOrganisationsInvitationsRV().setVisibility(View.VISIBLE);
                view.getEventsInvitationsRV().setVisibility(View.GONE);

                // Set TextStyle Tabs
                view.setTabsTypeface(InvitationsView.TAB_ORGANISATIONS);

                // Update Data
                interactor.getOrganisationsInvitations(this);
                break;
            case R.id.events:
                view.showProgress();

                // Set RecyclerView Visibility
                view.getFriendsInvitationsRV().setVisibility(View.GONE);
                view.getOrganisationsInvitationsRV().setVisibility(View.GONE);
                view.getEventsInvitationsRV().setVisibility(View.VISIBLE);

                // Set TextStyle Tabs
                view.setTabsTypeface(InvitationsView.TAB_EVENTS);

                // Update Data
                interactor.getEventsInvitations(this);
                break;
        }
    }

    @Override
    public void onClick(int viewId, FriendInvitation friendInvitation) {
        switch (viewId) {
            case R.id.image:
                // Go To User Profile
                Tools.replaceView(view, ProfileView.newInstance(friendInvitation.getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.name:
                // Go To User Profile
                Tools.replaceView(view, ProfileView.newInstance(friendInvitation.getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_confirm:
                view.showProgress();
                interactor.friendInvitationReply(friendInvitation, "true", this);
                break;
            case R.id.btn_delete:
                view.showProgress();
                interactor.friendInvitationReply(friendInvitation, "false", this);
                break;
        }
    }

    @Override
    public void onClick(int viewId, OrganisationInvitation organisationInvitation) {
        switch (viewId) {
            case R.id.image:
                // Go To Organisation Profile
                Tools.replaceView(view, OrganisationView.newInstance(organisationInvitation.getId(), organisationInvitation.getName()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.name:
                // Go To Organisation Profile
                Tools.replaceView(view, OrganisationView.newInstance(organisationInvitation.getId(), organisationInvitation.getName()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.city:
                // Go To Organisation Profile
                Tools.replaceView(view, OrganisationView.newInstance(organisationInvitation.getId(), organisationInvitation.getName()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.nb_friends_members:
                // Go To Organisation Profile
                Tools.replaceView(view, OrganisationView.newInstance(organisationInvitation.getId(), organisationInvitation.getName()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_confirm:
                view.showProgress();
                interactor.organisationInvitationReply(organisationInvitation, "true", this);
                break;
            case R.id.btn_delete:
                view.showProgress();
                interactor.organisationInvitationReply(organisationInvitation, "false", this);
                break;
        }
    }

    @Override
    public void onClick(int viewId, EventInvitation eventInvitation) {
        switch (viewId) {
            case R.id.image:
                // Go To Event Profile
                Tools.replaceView(view, OrganisationEventView.newInstance(eventInvitation.getId(), eventInvitation.getTitle()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.title:
                // Go To Event Profile
                Tools.replaceView(view, OrganisationEventView.newInstance(eventInvitation.getId(), eventInvitation.getTitle()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.place:
                // Go To Event Profile
                Tools.replaceView(view, OrganisationEventView.newInstance(eventInvitation.getId(), eventInvitation.getTitle()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.nb_friends_members:
                // Go To Event Profile
                Tools.replaceView(view, OrganisationEventView.newInstance(eventInvitation.getId(), eventInvitation.getTitle()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_confirm:
                view.showProgress();
                interactor.eventsInvitationReply(eventInvitation, "true", this);
                break;
            case R.id.btn_delete:
                view.showProgress();
                interactor.eventsInvitationReply(eventInvitation, "false", this);
                break;
        }
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.getSwipeRefreshLayout().setRefreshing(false);
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetFriendsInvitations(List<FriendInvitation> friendInvitations) {
        view.getFriendsInvitationsRVA().update(friendInvitations);
        view.hideProgress();
        view.getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void onSuccessGetOrganisationsInvitations(List<OrganisationInvitation> organisationInvitations) {
        view.getOrganisationsInvitationsRVA().update(organisationInvitations);
        view.hideProgress();
        view.getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void onSuccessGetEventsInvitations(List<EventInvitation> eventInvitations) {
        view.getEventsInvitationsRVA().update(eventInvitations);
        view.hideProgress();
        view.getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void onSuccessFriendsInvitationReply(FriendInvitation friendInvitation, String acceptance) {
        // Set Result Invitation Msg
        if (acceptance.equals("true"))
            friendInvitation.setResult("Invitation acceptée");
        else
            friendInvitation.setResult("Invitation rejetée");

        // Update RecyclerView
        view.getFriendsInvitationsRVA().notifyDataSetChanged();

        // Set Progress Bar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessOrganisationsInvitationReply(OrganisationInvitation organisationInvitation, String acceptance) {
        // Set Result Invitation Msg
        if (acceptance.equals("true"))
            organisationInvitation.setResult("Invitation acceptée");
        else
            organisationInvitation.setResult("Invitation rejetée");

        // Update RecyclerView
        view.getOrganisationsInvitationsRVA().notifyDataSetChanged();

        // Set Progress Bar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessEventsInvitationReply(EventInvitation eventInvitation, String acceptance) {
        // Set Result Invitation Msg
        if (acceptance.equals("true"))
            eventInvitation.setResult("Invitation acceptée");
        else
            eventInvitation.setResult("Invitation rejetée");

        // Update RecyclerView
        view.getEventsInvitationsRVA().notifyDataSetChanged();

        // Set Progress Bar Visibility
        view.hideProgress();
    }
}
