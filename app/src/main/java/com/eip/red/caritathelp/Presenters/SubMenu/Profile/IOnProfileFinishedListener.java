package com.eip.red.caritathelp.Presenters.SubMenu.Profile;

import com.eip.red.caritathelp.Models.Friends.Friend;
import com.eip.red.caritathelp.Models.Profile.MainPicture;
import com.eip.red.caritathelp.Models.User.User;

import java.util.List;

/**
 * Created by pierr on 11/05/2016.
 */

public interface IOnProfileFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessUploadProfileImg();

    void onSuccessGetProfile(User user);

    void onSuccessGetNews();

    void onSuccessAddFriend();

    void onSuccessRemoveFriend();

    void onSuccessReplyFriend(String acceptance);
}
