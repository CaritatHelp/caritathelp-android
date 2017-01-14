package com.eip.red.caritathelp.Views.Organisation.Management.members;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Presenters.Organisation.Management.OrganisationManagementInvitationsPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 14/01/2017.
 */

public class OrganisationManageInvitWaitingRvAdapter extends RecyclerView.Adapter<OrganisationManageInvitWaitingRvAdapter.ItemViewHolder> {

    private OrganisationManagementInvitationsPresenter presenter;
    private List<Member> members;

    public OrganisationManageInvitWaitingRvAdapter(OrganisationManagementInvitationsPresenter presenter) {
        this.presenter = presenter;
        members = new ArrayList<>();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail) CircularImageView thumbnail;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.btn_yes) ImageView yesBtn;
        @BindView(R.id.btn_no) ImageView noBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void goToProfileView() {
            Member member = members.get(getAdapterPosition());

            if (member != null)
                presenter.goToProfileView(member.getId());
        }

        @OnClick(R.id.thumbnail)
        public void onClickThumbnailBtn() {
            goToProfileView();
        }

        @OnClick(R.id.name)
        public void onClickNameBtn() {
            goToProfileView();
        }

        @OnClick(R.id.btn_yes)
        public void onClickYesBtn() {
            Member member = members.get(getAdapterPosition());

            if (member != null)
                presenter.join(member.getNotifId(), true, getAdapterPosition());
        }

        @OnClick(R.id.btn_no)
        public void onClickNoBtn() {
            Member member = members.get(getAdapterPosition());

            if (member != null)
                presenter.join(member.getNotifId(), false, getAdapterPosition());
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_management_invitations_waiting_rv_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Member member = members.get(position);

        if (member != null) {
            String name = member.getFirstname() + " " + member.getLastname();
            Network.loadImage(holder.thumbnail.getContext(), holder.thumbnail, Network.API_LOCATION_2 + member.getThumb_path(), R.drawable.profile_example);
            holder.name.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void update(List<Member> members) {
        this.members.clear();
        this.members.addAll(members);
        notifyDataSetChanged();
    }

    public void update(int rvPosition) {
        members.remove(rvPosition);
        notifyItemRemoved(rvPosition);
    }
}
