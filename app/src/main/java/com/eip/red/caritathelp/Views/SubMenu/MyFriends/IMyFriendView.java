package com.eip.red.caritathelp.Views.SubMenu.MyFriends;

/**
 * Created by pierr on 23/04/2016.
 */

public interface IMyFriendView {

    void setTabsTypeface(int tab);

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);
}
