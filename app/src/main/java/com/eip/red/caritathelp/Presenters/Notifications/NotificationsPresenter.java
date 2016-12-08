package com.eip.red.caritathelp.Presenters.Notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Activities.Main.MyNavigationBottomBar;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Notifications.Notification;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Notifications.NotificationsRVAdapter;
import com.eip.red.caritathelp.Views.Notifications.NotificationsView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 21/04/2016.
 */

public class NotificationsPresenter implements INotificationsPresenter, IOnNotificationsFinishedListener {

    private NotificationsView       view;
    private NotificationsInteractor interactor;

    public NotificationsPresenter(NotificationsView view, User user) {
        this.view = view;
        interactor = new NotificationsInteractor(view.getContext(), user);
    }


    @Override
    public void getNotifications() {
        view.showProgress();
        interactor.getNotifications(view.getProgressBar(), this);
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.tab_volunteer:
                // Set Tab TextView
                view.getVolunteerTab().setTypeface(Typeface.DEFAULT_BOLD);
                view.getOwnerTab().setTypeface(Typeface.DEFAULT);

                // Set RV
                view.getRvAdapter().setShowVolunteerNotifs(true);
                break;
            case R.id.tab_owner:
                // Set Tab TextView
                view.getVolunteerTab().setTypeface(Typeface.DEFAULT);
                view.getOwnerTab().setTypeface(Typeface.DEFAULT_BOLD);

                // Set RV
                view.getRvAdapter().setShowVolunteerNotifs(false);
                break;
        }
    }

    @Override
    public void onClick(int viewId, Notification notification) {
        switch (viewId) {
            case R.id.btn_confirm:
                switch (notification.getNotif_type()) {
                    case Notification.NOTIF_TYPE_JOIN_ASSOC:
                        break;
                    case Notification.NOTIF_TYPE_JOIN_EVENT:
                        break;
                    case Notification.NOTIF_TYPE_INVITE_MEMBER:
                        break;
                    case Notification.NOTIF_TYPE_INVITE_GUEST:
                        break;
                    case Notification.NOTIF_TYPE_ADD_FRIEND:
                        interactor.friendshipReply(notification, "true", this);
                        break;
                }
                break;
            case R.id.btn_delete:
                switch (notification.getNotif_type()) {
                    case Notification.NOTIF_TYPE_JOIN_ASSOC:
                        break;
                    case Notification.NOTIF_TYPE_JOIN_EVENT:
                        break;
                    case Notification.NOTIF_TYPE_INVITE_MEMBER:
                        break;
                    case Notification.NOTIF_TYPE_INVITE_GUEST:
                        break;
                    case Notification.NOTIF_TYPE_ADD_FRIEND:
                        interactor.friendshipReply(notification, "false", this);
                        break;
                }
                break;
            default:
                switch (notification.getNotif_type()) {
                    case Notification.NOTIF_TYPE_ADD_FRIEND:
                        Tools.replaceView(view, ProfileView.newInstance(notification.getSender_id()), Animation.FADE_IN_OUT, false);
                        break;
                    case Notification.NOTIF_TYPE_EMERGENCY:
                        Tools.replaceView(view, OrganisationEventView.newInstance(notification.getEvent_id(), notification.getEvent_name()), Animation.FADE_IN_OUT, false);
                        break;
                }
            break;
        }
    }

    /* Websocket msg */
    @Override
    public void onMessage() {
        interactor.getNotifications(view.getProgressBar(), this);
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetNotifications(List<Notification> notifications) {
        // Init Volunteer & Owner notifications lists
        List<Notification>  volunteerNotifs = new ArrayList<>();
        List<Notification>  ownerNotifs = new ArrayList<>();

        for (Notification notification : notifications) {
            String  notifType = notification.getNotif_type();

            if (notifType.equals(Notification.NOTIF_TYPE_INVITE_GUEST) || notifType.equals(Notification.NOTIF_TYPE_INVITE_MEMBER) || notifType.equals(Notification.NOTIF_TYPE_ADD_FRIEND))
                volunteerNotifs.add(notification);
            else
                ownerNotifs.add(notification);
        }

        // Set RecyclerView
        NotificationsRVAdapter adpater = view.getRvAdapter();
        adpater.update(volunteerNotifs, ownerNotifs);

        // Set Notification Number Bottom Navigation Bar
        int number = adpater.getItemCount();
        ((MainActivity) view.getActivity()).getMyNavigationBottomBar().setNotifications(number, MyNavigationBottomBar.NOTIFICATIONS);

        // Set ProgressBar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessFriendshipReply(Notification notification, String acceptance) {
        // Set ProgressBar Visibility
        view.hideProgress();

        // Set Notification Result Msg
        switch (acceptance) {
            case "true":
                switch (notification.getNotif_type()) {
                    case Notification.NOTIF_TYPE_JOIN_ASSOC:
                        break;
                    case Notification.NOTIF_TYPE_JOIN_EVENT:
                        break;
                    case Notification.NOTIF_TYPE_INVITE_MEMBER:
                        break;
                    case Notification.NOTIF_TYPE_INVITE_GUEST:
                        break;
                    case Notification.NOTIF_TYPE_ADD_FRIEND:
                        notification.setResult("Invitation acceptée");
                        break;
                }
                break;
            case "false":
                switch (notification.getNotif_type()) {
                    case Notification.NOTIF_TYPE_JOIN_ASSOC:
                        break;
                    case Notification.NOTIF_TYPE_JOIN_EVENT:
                        break;
                    case Notification.NOTIF_TYPE_INVITE_MEMBER:
                        break;
                    case Notification.NOTIF_TYPE_INVITE_GUEST:
                        break;
                    case Notification.NOTIF_TYPE_ADD_FRIEND:
                        notification.setResult("Invitation rejetée");
                        break;
                }
                break;
        }

        // Update RecyclerView
        view.getRvAdapter().notifyDataSetChanged();
    }
}
