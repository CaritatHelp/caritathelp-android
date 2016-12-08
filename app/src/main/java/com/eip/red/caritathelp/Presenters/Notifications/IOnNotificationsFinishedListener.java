package com.eip.red.caritathelp.Presenters.Notifications;

import com.eip.red.caritathelp.Models.Notifications.Notification;

import java.util.List;

/**
 * Created by pierr on 21/04/2016.
 */

public interface IOnNotificationsFinishedListener {

    void onDialogError(String title, String msg);

    void onSuccessGetNotifications(List<Notification> notifications);

    void onSuccessFriendshipReply(Notification notification, String acceptance);
}
