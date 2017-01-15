package com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.invitations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Guest;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management.InvitationsPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 15/01/2017.
 */

public class InvitationsSentRvAdapter extends RecyclerView.Adapter<InvitationsSentRvAdapter.ItemViewHolder> {

    private InvitationsPresenter presenter;
    private List<Guest> guests;

    public InvitationsSentRvAdapter(InvitationsPresenter presenter) {
        this.presenter = presenter;
        guests = new ArrayList<>();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail) CircularImageView thumbnail;
        @BindView(R.id.name) TextView name;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        private void goToProfileView() {
            Guest guest = guests.get(getAdapterPosition());

            if (guest != null)
                presenter.goToProfileView(guest.getId());
        }

        @OnClick(R.id.thumbnail)
        public void onClickThumbnailBtn() {
            goToProfileView();
        }

        @OnClick(R.id.name)
        public void onClickNameBtn() {
            goToProfileView();
        }

        @OnClick(R.id.btn_no)
        public void onClickNoBtn() {
            Guest guest = guests.get(getAdapterPosition());

            if (guest != null)
                presenter.uninvite(guest.getId(), getAdapterPosition());
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_management_invitations_sent_rv_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Guest guest = guests.get(position);

        if (guest != null) {
            String name = guest.getFirstname() + " " + guest.getLastname();
            Network.loadImage(holder.thumbnail.getContext(), holder.thumbnail, Network.API_LOCATION_2 + guest.getThumb_path(), R.drawable.profile_example);
            holder.name.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        return guests.size();
    }

    public void update(List<Guest> guests) {
        this.guests.clear();
        this.guests.addAll(guests);
        notifyDataSetChanged();
    }

    public void update(int rvPosition) {
        guests.remove(rvPosition);
        notifyItemRemoved(rvPosition);
    }

}

