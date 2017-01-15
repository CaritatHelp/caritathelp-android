package com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Guest;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests.OrganisationEventGuestsPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 15/04/2016.
 */

public class OrganisationEventGuestsRVAdapter extends RecyclerView.Adapter<OrganisationEventGuestsRVAdapter.DataObjectHolder> {

    private final OrganisationEventGuestsPresenter presenter;

    private List<Guest> visibleObjects;
    private List<Guest> allObjects;

    public OrganisationEventGuestsRVAdapter(OrganisationEventGuestsPresenter presenter) {
        this.presenter = presenter;

        visibleObjects = new ArrayList<>();
        allObjects = new ArrayList<>();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image) ImageView thumbnail;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.rights) TextView rights;
        @BindView(R.id.btn_upgrade) TextView upgradeBtn;
        @BindView(R.id.btn_kick) ImageView kickBtn;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void goToProfileView() {
            Guest   guest = visibleObjects.get(getAdapterPosition());

            if (guest != null)
                presenter.goToProfileView(guest.getId());
        }

        @OnClick(R.id.image)
        public void onClickImageBtn() {
            goToProfileView();
        }

        @OnClick(R.id.name)
        public void onClickNameBtn() {
            goToProfileView();
        }

        @OnClick(R.id.btn_upgrade)
        public void onClickUpgradeBtn() {

        }

        @OnClick(R.id.btn_kick)
        public void onClickKickBtn() {

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_event_guests_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        final Guest   guest = visibleObjects.get(position);
        String  name = guest.getFirstname() + " " + guest.getLastname();

        holder.name.setText(name);
        Network.loadImage(holder.thumbnail.getContext(), holder.thumbnail, Network.API_LOCATION_2 + guest.getThumb_path(), R.drawable.profile_example);

        if (presenter.isOwner()) {
            System.out.println("RIGHT : " + guest.getRights());
            if (!guest.getRights().equals(Organisation.ORGANISATION_HOST)) {
                holder.upgradeBtn.setVisibility(View.VISIBLE);
                holder.kickBtn.setVisibility(View.VISIBLE);

                holder.upgradeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.upgrade(guest.getId(), guest.getRights(), position);
                    }
                });

                holder.kickBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(view.getContext())
                                .setCancelable(true)
                                .setMessage(String.format("Voulez-vous renvoyer %s ?", guest.getFirstname() + " " + guest.getLastname()))
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        presenter.kick(guest.getId(), position);
                                    }
                                })
                                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .show();
                    }
                });
            }
            else {
                holder.upgradeBtn.setVisibility(View.GONE);
                holder.kickBtn.setVisibility(View.GONE);
            }
        }
        else {
            holder.upgradeBtn.setVisibility(View.GONE);
            holder.kickBtn.setVisibility(View.GONE);
        }

        switch (guest.getRights()) {
            case Organisation.ORGANISATION_HOST:
                holder.rights.setText("Propriétaire");
                break;
            case Organisation.ORGANISATION_ADMIN:
                holder.rights.setText("Administrateur");
                break;
            case Organisation.ORGANISATION_MEMBER:
                holder.rights.setText("Membre");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (visibleObjects.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void update(List<Guest>  guests) {
        visibleObjects.clear();
        allObjects.clear();

        visibleObjects.addAll(guests);
        allObjects.addAll(guests);

        notifyDataSetChanged();
    }

    public void flushFilter(){
        visibleObjects.clear();
        visibleObjects.addAll(allObjects);
        notifyDataSetChanged();
    }

    public void filter(String queryText) {
        visibleObjects.clear();

        for (Guest guest :  allObjects) {
            String  name = guest.getFirstname() + " " + guest.getLastname();
            if (name.toLowerCase(Locale.getDefault()).contains(queryText))
                visibleObjects.add(guest);
        }
        notifyDataSetChanged();
    }
}
