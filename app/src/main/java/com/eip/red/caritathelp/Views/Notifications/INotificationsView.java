package com.eip.red.caritathelp.Views.Notifications;

/**
 * Created by pierr on 21/04/2016.
 */

public interface INotificationsView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);
}
