package com.eip.red.caritathelp.Views.SubMenu.Invitations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.EventInvitation;
import com.eip.red.caritathelp.Presenters.SubMenu.Invitations.InvitationsPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 06/07/2016.
 */

public class EventsInvitationsRVAdapter extends RecyclerView.Adapter<EventsInvitationsRVAdapter.DataObjectHolder> {

    private InvitationsPresenter    presenter;
    private List<EventInvitation>   eventInvitations;

    public EventsInvitationsRVAdapter(InvitationsPresenter presenter) {
        this.presenter = presenter;
        eventInvitations = new ArrayList<>();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircularImageView   image;
        TextView            title;
        TextView            place;
        TextView            nb_friends_members;
        TextView            result;
        LinearLayout        buttons;
        ImageButton         confirm;
        ImageButton         delete;


        public DataObjectHolder(View itemView) {
            super(itemView);

            // Init UI Elements
            image = (CircularImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            place = (TextView) itemView.findViewById(R.id.place);
            nb_friends_members = (TextView) itemView.findViewById(R.id.nb_friends_members);
            result = (TextView) itemView.findViewById(R.id.result);
            buttons = (LinearLayout) itemView.findViewById(R.id.buttons);
            confirm = (ImageButton) itemView.findViewById(R.id.btn_confirm);
            delete = (ImageButton) itemView.findViewById(R.id.btn_delete);

            // Init Listeners
            itemView.setOnClickListener(this);
            image.setOnClickListener(this);
            title.setOnClickListener(this);
            place.setOnClickListener(this);
            nb_friends_members.setOnClickListener(this);
            confirm.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.onClick(v.getId(), eventInvitations.get(getAdapterPosition()));
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_submenu_invitations_events_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        EventInvitation eventInvitation = eventInvitations.get(position);
        String          result = eventInvitation.getResult();
        int             nb_friends_members = eventInvitation.getNb_friends_members();

        // Set Title & Place
        holder.title.setText(eventInvitation.getTitle());
        holder.place.setText(eventInvitation.getPlace());

        // Set Nb Friends Members
        if (nb_friends_members == 0)
            holder.nb_friends_members.setVisibility(View.GONE);
        else if (nb_friends_members == 1) {
            String msg = "1 ami";
            holder.nb_friends_members.setVisibility(View.VISIBLE);
            holder.nb_friends_members.setText(msg);
        }
        else {
            String msg = String.valueOf(nb_friends_members) + " amis";
            holder.nb_friends_members.setVisibility(View.VISIBLE);
            holder.nb_friends_members.setText(msg);
        }

        // load Image
        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + eventInvitation.getThumb_path(), R.drawable.profile_example);

        // Set Result Msg (Invitation accepted...)
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
        return eventInvitations.size();
    }

    public void update(List<EventInvitation> newEventInvitations) {
        eventInvitations.clear();
        eventInvitations.addAll(newEventInvitations);
        notifyDataSetChanged();
    }
}
