package com.eip.red.caritathelp.Views.SubMenu.Invitations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Friends.FriendInvitation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Presenters.SubMenu.Invitations.InvitationsPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 04/07/2016.
 */

public class FriendsInvitationsRVAdapter extends RecyclerView.Adapter<FriendsInvitationsRVAdapter.DataObjectHolder> {

    private InvitationsPresenter    presenter;
    private List<FriendInvitation> friendInvitations;

    public FriendsInvitationsRVAdapter(InvitationsPresenter presenter) {
        this.presenter = presenter;
        friendInvitations = new ArrayList<>();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircularImageView   image;
        TextView            name;
        TextView            result;
        LinearLayout        buttons;
        ImageButton         confirm;
        ImageButton         delete;

        public DataObjectHolder(View itemView) {
            super(itemView);

            // Init UI Elements
            image = (CircularImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            result = (TextView) itemView.findViewById(R.id.result);
            buttons = (LinearLayout) itemView.findViewById(R.id.buttons);
            confirm = (ImageButton) itemView.findViewById(R.id.btn_confirm);
            delete = (ImageButton) itemView.findViewById(R.id.btn_delete);

            // Init Listeners
            itemView.setOnClickListener(this);
            image.setOnClickListener(this);
            name.setOnClickListener(this);
            confirm.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.onClick(v.getId(), friendInvitations.get(getAdapterPosition()));
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_submenu_invitations_friends_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        FriendInvitation friendInvitation = friendInvitations.get(position);
        String      name = friendInvitation.getFirstname() + " " + friendInvitation.getLastname();
        String      result = friendInvitation.getResult();

        // Set Name
        holder.name.setText(name);

        // load Image
        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + friendInvitation.getThumb_path(), R.drawable.profile_example);

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
        return friendInvitations.size();
    }

    public void update(List<FriendInvitation> newFriendInvitations) {
        friendInvitations.clear();
        friendInvitations.addAll(newFriendInvitations);
        notifyDataSetChanged();
    }

}
