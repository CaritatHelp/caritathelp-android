package com.eip.red.caritathelp.Views.Organisation.Management.members.invite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Models.Search.Search;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Management.InvitePresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pierr on 14/01/2017.
 */

public class InviteRvAdapter extends RecyclerView.Adapter<InviteRvAdapter.ItemViewHolder> {

    private InvitePresenter presenter;
    private List<User> volunteers;

    public InviteRvAdapter(InvitePresenter presenter) {
        this.presenter = presenter;
        volunteers = new ArrayList<>();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.thumbnail) CircularImageView thumbnail;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.btn_check) ImageView checkBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            User user = volunteers.get(getAdapterPosition());

            if (user != null) {
                if (checkBtn.isShown()) {
                    user.setCheck(false);
                    checkBtn.setVisibility(View.INVISIBLE);
                }
                else {
                    user.setCheck(true);
                    checkBtn.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_management_send_invitations_rv_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        User volunteer = volunteers.get(position);

        if (volunteer != null) {
            Network.loadImage(holder.thumbnail.getContext(), holder.thumbnail, Network.API_LOCATION_2 + volunteer.getThumb_path(), R.drawable.profile_example);
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

    public List<User> getVolunteers() {
        return volunteers;
    }
}
