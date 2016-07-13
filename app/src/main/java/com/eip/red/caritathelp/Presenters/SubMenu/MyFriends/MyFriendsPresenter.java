package com.eip.red.caritathelp.Presenters.SubMenu.MyFriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Friends.Friend;
import com.eip.red.caritathelp.Models.Friends.FriendInvitation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.SubMenu.MyFriends.MyFriendsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 19/04/2016.
 */

public class MyFriendsPresenter implements IMyFriendsPresenter, IOnMyFriendsFinishedListener {

    private MyFriendsView view;
    private MyFriendsInteractor interactor;

    public MyFriendsPresenter(MyFriendsView view, User mainUser, int userId) {
        this.view = view;
        interactor = new MyFriendsInteractor(view.getContext(), mainUser, userId);
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.my_friends:
                view.showProgress();

                // Set RecyclerView Visibility
                view.getFriendsRV().setVisibility(View.VISIBLE);
                view.getInvitationsRV().setVisibility(View.GONE);
                view.getSentRV().setVisibility(View.GONE);

                // Set TextStyle Tabs
                view.setTabsTypeface(MyFriendsView.TAB_FRIEND);

                // Update Data
                interactor.getMyFriends(this);
                break;
            case R.id.invitations:
                view.showProgress();

                // Set RecyclerView Visibility
                view.getFriendsRV().setVisibility(View.GONE);
                view.getInvitationsRV().setVisibility(View.VISIBLE);
                view.getSentRV().setVisibility(View.GONE);

                // Set TextStyle Tabs
                view.setTabsTypeface(MyFriendsView.TAB_INVITATIONS);

                // Update Data
                interactor.getInvitations("default", this);
                break;
            case R.id.sent:
                view.showProgress();

                // Set RecyclerView Visibility
                view.getFriendsRV().setVisibility(View.GONE);
                view.getInvitationsRV().setVisibility(View.GONE);
                view.getSentRV().setVisibility(View.VISIBLE);

                // Set TextStyle Tabs
                view.setTabsTypeface(MyFriendsView.TAB_SENT);

                // Update Data
                interactor.getInvitations("true", this);
                break;
            case R.id.btn_add:
                // Go to Search View in order to search new friends
                ((MainActivity) view.getActivity()).getMySearchView().show();
                break;
        }
    }

    @Override
    public void onClick(int viewId, final Friend friend) {
        switch (viewId) {
            case R.id.image:
                // Go To User Profile
                Tools.replaceView(view, ProfileView.newInstance(friend.getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.name:
                // Go To User Profile
                Tools.replaceView(view, ProfileView.newInstance(friend.getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.nb_common_friends:
                // Go To User Profile
                Tools.replaceView(view, ProfileView.newInstance(friend.getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_block:
                break;
            case R.id.btn_remove:
                final IOnMyFriendsFinishedListener listener = this;
                String  name = friend.getFirstname() + " " + friend.getLastname();

                // Display RemoveFriendDialog
                new AlertDialog.Builder(view.getContext())
                        .setCancelable(true)
                        .setTitle("Supprimer un ami")
                        .setMessage("Êtes-vous sûr de vouloir supprimer " + name + " de votre liste d'amis ?")
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view.showProgress();
                                interactor.removeFriend(friend.getId(), view.getProgressBar(), listener);
                            }
                        })
                        .show();
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
                interactor.invitationReply(friendInvitation, "true", this);
                break;
            case R.id.btn_delete:
                view.showProgress();
                interactor.invitationReply(friendInvitation, "false", this);
                break;
        }
    }

    @Override
    public void getMyFriends(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();

        interactor.getMyFriends(this);
    }

    @Override
    public void getInvitations() {
        interactor.getInvitations("default", this);
    }

    @Override
    public void getSent() {
        interactor.getInvitations("true", this);
    }

    @Override
    public void navigateToFriendProfile(int friendId) {
        Tools.replaceView(view, ProfileView.newInstance(friendId), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.getSwipeRefreshLayout().setRefreshing(false);
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetMyFriends(List<Friend> friends) {
        // Set RecyclerView Data
        view.getFriendsRVA().update(friends);

        // Set ProgressBar Visibility
        view.hideProgress();

        // Set SwipeRefreshLayout Refreshing
        view.getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void onSuccessGetInvitations(List<FriendInvitation> friendInvitations, String sent) {
        // Set RecyclerView Data
        if (sent.equals("default"))
            view.getInvitationsRVA().update(friendInvitations);
        else
            view.getSentRVA().update(friendInvitations);

        // Set ProgressBar Visibility
        view.hideProgress();

        // Set SwipeRefreshLayout Refreshing
        view.getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void onSuccessBlockFriend() {

    }

    @Override
    public void onSuccessRemoveFriend() {
        // Set RecyclerView
        getMyFriends(false);

        // Set ProgressBar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessInvitationReply(FriendInvitation friendInvitation, String acceptance) {
        // Set Result Invitation Msg
        if (acceptance.equals("true"))
            friendInvitation.setResult("Invitation acceptée");
        else
            friendInvitation.setResult("Invitation rejetée");

        // Update RecyclerView
        view.getInvitationsRVA().notifyDataSetChanged();

        // Set Progress Bar Visibility
        view.hideProgress();
    }
}
