package com.eip.red.caritathelp.Views.SubMenu.MyFriends;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Friends.FriendInvitation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Presenters.SubMenu.MyFriends.MyFriendsPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 01/07/2016.
 */

public class SentRVAdapter extends RecyclerView.Adapter<SentRVAdapter.DataObjectHolder> {

    private MyFriendsPresenter presenter;
    private List<FriendInvitation> friendInvitations;

    public SentRVAdapter(MyFriendsPresenter presenter) {
        this.presenter = presenter;
        friendInvitations = new ArrayList<>();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircularImageView   image;
        TextView            name;

        public DataObjectHolder(View itemView) {
            super(itemView);

            // Init UI Elements
            image = (CircularImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);

            // Init Listeners
            itemView.setOnClickListener(this);
            image.setOnClickListener(this);
            name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.onClick(v.getId(), friendInvitations.get(getAdapterPosition()));
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_submenu_my_friends_sent_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        FriendInvitation friendInvitation = friendInvitations.get(position);
        String      name = friendInvitation.getFirstname() + " " + friendInvitation.getLastname();

        // Set Name
        holder.name.setText(name);

        // load Image
        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + friendInvitation.getThumb_path(), R.drawable.profile_example);
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
