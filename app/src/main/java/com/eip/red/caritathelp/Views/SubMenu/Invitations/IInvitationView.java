package com.eip.red.caritathelp.Views.SubMenu.Invitations;

/**
 * Created by pierr on 04/07/2016.
 */

public interface IInvitationView {

    void setTabsTypeface(int tab);

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);
}
