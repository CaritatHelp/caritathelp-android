package com.eip.red.caritathelp.Views.Notifications;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Notifications.Notification;
import com.eip.red.caritathelp.Presenters.Notifications.NotificationsPresenter;
import com.eip.red.caritathelp.R;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by pierr on 21/04/2016.
 */

public class NotificationsRVAdapter extends RecyclerView.Adapter<NotificationsRVAdapter.DataObjectHolder> {

    private NotificationsPresenter  presenter;
    private List<Notification>      volunteerNotifs;
    private List<Notification>      ownerNotifs;

    private boolean             showVolunteerNotifs;

    public NotificationsRVAdapter(NotificationsPresenter presenter) {
        this.presenter = presenter;
        volunteerNotifs = new ArrayList<>();
        ownerNotifs = new ArrayList<>();
        showVolunteerNotifs = true;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircularImageView   image;
        TextView            message;
        TextView            date;
        TextView            result;
        LinearLayout        buttons;
        ImageButton         confirm;
        ImageButton         delete;

        public DataObjectHolder(View itemView) {
            super(itemView);

            image = (CircularImageView) itemView.findViewById(R.id.image);
            message = (TextView) itemView.findViewById(R.id.message);
            date = (TextView) itemView.findViewById(R.id.date);
            result = (TextView) itemView.findViewById(R.id.result);
            buttons = (LinearLayout) itemView.findViewById(R.id.buttons);
            confirm = (ImageButton) itemView.findViewById(R.id.btn_confirm);
            delete = (ImageButton) itemView.findViewById(R.id.btn_delete);

            confirm.setOnClickListener(this);
            delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Notification    notification;

            if (showVolunteerNotifs)
                notification = volunteerNotifs.get(getAdapterPosition());
            else
                notification = ownerNotifs.get(getAdapterPosition());

            if (notification != null)
                presenter.onClick(v.getId(), notification);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notifications_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Notification    notification;
        String          msg = null;

        if (showVolunteerNotifs)
            notification = volunteerNotifs.get(position);
        else
            notification = ownerNotifs.get(position);

        // Set Img
        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + notification.getThumb_path(), R.drawable.profile_example);

        // Set Msg && Buttons Visibility
        switch (notification.getNotif_type()) {
            case Notification.NOTIF_TYPE_JOIN_ASSOC:
                // Set Msg
                msg = notification.getSender_name() + " souhaite rejoindre l'association " + notification.getAssoc_name() + ".";
                holder.message.setText(msg);
                break;
            case Notification.NOTIF_TYPE_JOIN_EVENT:
                // Set Msg
                msg = notification.getSender_name() + " souhaite participer à l'événement " + notification.getEvent_name() + ".";
                holder.message.setText(msg);
                break;
            case Notification.NOTIF_TYPE_INVITE_MEMBER:
                // Set Msg
                msg = notification.getSender_name() + " t'invite à rejoindre son association " + notification.getAssoc_name() + ".";
                holder.message.setText(msg);
                break;
            case Notification.NOTIF_TYPE_INVITE_GUEST:
                // Set Msg
                msg = notification.getSender_name() + " t'invite à participer son événement " + notification.getEvent_name() + ".";
                holder.message.setText(msg);
                break;
            case Notification.NOTIF_TYPE_ADD_FRIEND:
                // Set Msg
                msg = notification.getSender_name() + " souhaite rejoindre votre liste d'amis.";
                holder.message.setText(msg);
                break;
            case Notification.NOTIF_TYPE_NEW_MEMBER:
                // Set Msg
                msg = notification.getSender_name() + " est maintenant membre de votre association " + notification.getEvent_name() + ".";
                holder.message.setText(msg);

                // Set Buttons Visibility
                holder.confirm.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
                break;
            case Notification.NOTIF_TYPE_NEW_GUEST:
                // Set Msg
                msg = notification.getSender_name() + " est maintenant membre de votre association " + notification.getEvent_name() + ".";
                holder.message.setText(msg);

                // Set Buttons Visibility
                holder.confirm.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
                break;
            case Notification.NOTIF_TYPE_EMERGENCY:
                // Set Msg
                msg ="Urgence : l'événement <b>" + notification.getEvent_name() + "</b> a besoin de vous.";
                holder.message.setText(Html.fromHtml(msg));

                // Set Buttons Visibility
                holder.confirm.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
                break;
        }

        // Set Date
        try {
            long now = ISO8601Utils.parse(notification.getCreated_at(), new ParsePosition(0)).getTime();
            holder.date.setText(String.format("%s", DateUtils.getRelativeTimeSpanString(now)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set Result Msg (Friend Invitation confirmed...)
        String  result = notification.getResult();
        if (result != null) {
            // Set Buttons Visibility
            holder.buttons.setVisibility(View.INVISIBLE);

            // Set Result Msg && Visibility
            holder.result.setText(result);
            holder.result.setVisibility(View.VISIBLE);
        }
        else {
            holder.buttons.setVisibility(View.VISIBLE);
            holder.result.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (showVolunteerNotifs)
            return volunteerNotifs.size();
        return ownerNotifs.size();
    }

    public void update(List<Notification> volunteerNotifs, List<Notification> ownerNotifs) {
        this.volunteerNotifs.clear();
        this.ownerNotifs.clear();

        this.volunteerNotifs.addAll(volunteerNotifs);
        this.ownerNotifs.addAll(ownerNotifs);

        notifyDataSetChanged();
    }

    public boolean isShowVolunteerNotifs() {
        return showVolunteerNotifs;
    }

    public void setShowVolunteerNotifs(boolean showVolunteerNotifs) {
        this.showVolunteerNotifs = showVolunteerNotifs;
        notifyDataSetChanged();
    }

}
