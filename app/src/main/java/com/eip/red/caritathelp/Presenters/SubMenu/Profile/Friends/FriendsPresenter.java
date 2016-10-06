package com.eip.red.caritathelp.Presenters.SubMenu.Profile.Friends;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Friends.Friend;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.SubMenu.Profile.Friends.FriendsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 13/07/2016.
 */
public class FriendsPresenter implements IFriendsPresenter, IOnFriendsFinishedListener {

    private FriendsView         view;
    private FriendsInteractor   interactor;

    public FriendsPresenter(FriendsView view, int profileId, User user) {
        this.view = view;
        interactor = new FriendsInteractor(view.getContext(), profileId, user);
    }

    @Override
    public void getFriends(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();

        interactor.getFriends(this);
    }

    @Override
    public void onClick(int viewId, Friend friend) {
        // Go To User Profile
        Tools.replaceView(view, ProfileView.newInstance(friend.getId()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.getSwipeRefreshLayout().setRefreshing(false);
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetFriends(List<Friend> friends) {
        // Set RecyclerView data
        view.getFriendsRVAdapter().update(friends);

        // Set ProgressBar Visibility & SwipeRefreshLayout Refreshing
        view.hideProgress();
        view.getSwipeRefreshLayout().setRefreshing(false);
    }
}
