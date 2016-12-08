package com.eip.red.caritathelp.Presenters.SubMenu.Profile.Friends;

import com.eip.red.caritathelp.Models.Friends.Friend;

/**
 * Created by pierr on 13/07/2016.
 */
public interface IFriendsPresenter {

    void getFriends(boolean isSwipeRefresh);

    void onClick(int viewId, Friend friend);
}
