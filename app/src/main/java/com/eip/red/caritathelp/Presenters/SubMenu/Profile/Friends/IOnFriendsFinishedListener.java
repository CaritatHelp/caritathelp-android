package com.eip.red.caritathelp.Presenters.SubMenu.Profile.Friends;

import com.eip.red.caritathelp.Models.Friends.Friend;

import java.util.List;

/**
 * Created by pierr on 13/07/2016.
 */

public interface IOnFriendsFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessGetFriends(List<Friend> friends);
}
