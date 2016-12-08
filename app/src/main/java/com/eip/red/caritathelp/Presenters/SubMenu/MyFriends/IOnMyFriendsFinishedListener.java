package com.eip.red.caritathelp.Presenters.SubMenu.MyFriends;

import com.eip.red.caritathelp.Models.Friends.Friend;
import com.eip.red.caritathelp.Models.Friends.FriendInvitation;

import java.util.List;

/**
 * Created by pierr on 19/04/2016.
 */

public interface IOnMyFriendsFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessGetMyFriends(List<Friend> friends);

    void onSuccessGetInvitations(List<FriendInvitation> friendInvitations, String sent);

    void onSuccessBlockFriend();

    void onSuccessRemoveFriend();

    void onSuccessInvitationReply(FriendInvitation friendInvitation, String acceptance);
}
