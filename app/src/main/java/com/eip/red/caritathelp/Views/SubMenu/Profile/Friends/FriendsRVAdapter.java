package com.eip.red.caritathelp.Views.SubMenu.Profile.Friends;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Friends.Friend;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.Friends.FriendsPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 13/07/2016.
 */

public class FriendsRVAdapter extends RecyclerView.Adapter<FriendsRVAdapter.DataObjectHolder> {

    private static final String MSG_COMMON_FRIEND = " ami en commun";
    private static final String MSG_COMMON_FRIENDS = " amis en commun";

    private FriendsPresenter    presenter;
    private List<Friend>        friends;

    public FriendsRVAdapter(FriendsPresenter presenter) {
        this.presenter = presenter;
        friends = new ArrayList<>();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircularImageView   image;
        TextView            name;
        TextView            nb_common_friends;

        public DataObjectHolder(View itemView) {
            super(itemView);

            // Init UI Elements
            image = (CircularImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            nb_common_friends = (TextView) itemView.findViewById(R.id.nb_common_friends);

            // Init Listeners
            image.setOnClickListener(this);
            name.setOnClickListener(this);
            nb_common_friends.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.onClick(v.getId(), friends.get(getAdapterPosition()));
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_submenu_profile_friends_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Friend  friend = friends.get(position);
        String  name = friend.getFirstname() + " " + friend.getLastname();
        String  nb_commons_friends = friend.getNb_common_friends();

        // Set Name
        holder.name.setText(name);

        // Load image
        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + friend.getThumb_path(), R.drawable.profile_example);

        // Set Nb commons friends
        if (Integer.valueOf(nb_commons_friends) <= 1)
            nb_commons_friends += MSG_COMMON_FRIEND;
        else
            nb_commons_friends += MSG_COMMON_FRIENDS;
        holder.nb_common_friends.setText(nb_commons_friends);
    }

    @Override
    public int getItemCount() {
        return (friends.size());
    }

    public void update(List<Friend> newFriends) {
        friends.clear();
        friends.addAll(newFriends);
        notifyDataSetChanged();
    }

}
