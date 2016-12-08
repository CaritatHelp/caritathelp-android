package com.eip.red.caritathelp.Views.Notifications;

import com.eip.red.caritathelp.Models.Notifications.Notification;

import java.util.List;

/**
 * Created by pierr on 21/04/2016.
 */

public interface INotificationsView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);
}
