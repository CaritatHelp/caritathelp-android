package com.eip.red.caritathelp.Views.Organisation.Events.Event.emergency;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.EmergencyInvitationsPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pierr on 16/01/2017.
 */

public class EmergencyInvitationsRvAdapter extends RecyclerView.Adapter<EmergencyInvitationsRvAdapter.ItemViewHolder> {

    private EmergencyInvitationsPresenter presenter;
    private List<User> volunteers;

    public EmergencyInvitationsRvAdapter(EmergencyInvitationsPresenter presenter) {
        this.presenter = presenter;
        volunteers = new ArrayList<>();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.thumbnail) CircularImageView thumbnail;
        @BindView(R.id.name) TextView name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            User volunteer = volunteers.get(getAdapterPosition());

            if (volunteer != null)
                presenter.goToProfileView(volunteer.getId());
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_event_emergency_invitations_rv_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        User volunteer = volunteers.get(position);

        if (volunteer != null) {
            Network.loadImage(holder.thumbnail.getContext(), holder.thumbnail, Network.API_LOCATION + volunteer.getThumb_path(), R.drawable.profile_example);
            holder.name.setText(volunteer.getFullname());
        }
    }

    @Override
    public int getItemCount() {
        return volunteers.size();
    }

    public void update(List<User> volunteers) {
        this.volunteers.clear();
        this.volunteers.addAll(volunteers);
        notifyDataSetChanged();
    }

}
