package com.eip.red.caritathelp.Presenters.Notifications;

import com.eip.red.caritathelp.Models.Notifications.Notification;

/**
 * Created by pierr on 21/04/2016.
 */
public interface INotificationsPresenter {

    void getNotifications();

    void onClick(int viewId);

    void onClick(int viewId, Notification notification);

    void onMessage();

}
