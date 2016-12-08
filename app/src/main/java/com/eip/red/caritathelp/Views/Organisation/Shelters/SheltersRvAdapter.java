package com.eip.red.caritathelp.Views.Organisation.Shelters;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Shelter.Shelter;
import com.eip.red.caritathelp.Presenters.Organisation.SheltersPresenter;
import com.eip.red.caritathelp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 03/12/2016.
 */

public class SheltersRvAdapter extends RecyclerView.Adapter<SheltersRvAdapter.DataObjectHolder> {

    private Context context;
    private SheltersPresenter presenter;
    private List<Shelter> shelters;
    private boolean owner;

    public SheltersRvAdapter(Context context, SheltersPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
        shelters = new ArrayList<>();
        owner = presenter.isOwner();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.thumbnail) ImageView thumbnail;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.city) TextView city;
        @BindView(R.id.btn_update) ImageButton update;
        @BindView(R.id.btn_delete) ImageButton delete;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Shelter shelter = shelters.get(getAdapterPosition());

            if (shelter != null) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.view_shelter);

                TextView name = ButterKnife.findById(dialog, R.id.name);
                name.setText(shelter.getName());

                LinearLayout placesContainer = ButterKnife.findById(dialog, R.id.places_container);
                if (shelter.getFreePlaces() != null && shelter.getTotalPlaces() != null) {
                    TextView places = ButterKnife.findById(dialog, R.id.places);
                    placesContainer.setVisibility(View.VISIBLE);
                    String placesStr = shelter.getFreePlaces() + " / " + shelter.getTotalPlaces();
                    places.setText(placesStr);
                }
                else
                    placesContainer.setVisibility(View.GONE);

                LinearLayout phoneContainer = ButterKnife.findById(dialog, R.id.phone_container);
                String phoneStr = shelter.getPhone();
                if (phoneStr != null && !TextUtils.isEmpty(phoneStr)) {
                    TextView phone = ButterKnife.findById(dialog, R.id.phone);
                    phoneContainer.setVisibility(View.VISIBLE);
                    phone.setText(phoneStr);
                }
                else
                    phoneContainer.setVisibility(View.GONE);

                LinearLayout mailContainer = ButterKnife.findById(dialog, R.id.mail_container);
                String mailStr = shelter.getMail();
                if (mailStr != null && !TextUtils.isEmpty(mailStr)) {
                    TextView mail = ButterKnife.findById(dialog, R.id.mail);
                    mailContainer.setVisibility(View.VISIBLE);
                    mail.setText(mailStr);
                }
                else
                    mailContainer.setVisibility(View.GONE);

                LinearLayout infoContainer = ButterKnife.findById(dialog, R.id.info_container);
                String description = shelter.getDescription();
                if (description != null && !TextUtils.isEmpty(description)) {
                    TextView info = ButterKnife.findById(dialog, R.id.more_info);
                    infoContainer.setVisibility(View.VISIBLE);
                    info.setText(description);
                }
                else
                    infoContainer.setVisibility(View.GONE);

                dialog.show();
            }
        }

        @OnClick(R.id.btn_update)
        public void onClickUpdateBtn() {
            Shelter shelter = shelters.get(getAdapterPosition());

            if (shelter != null)
                presenter.updateShelter(shelter);
        }

        @OnClick(R.id.btn_delete)
        public void onClickDeleteBtn() {
                Shelter shelter = shelters.get(getAdapterPosition());

                if (shelter != null)
                    presenter.deleteShelter(shelter.getId(), shelter.getName());

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_shelters_rv_row, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Shelter shelter = shelters.get(position);
        String city = shelter.getCity() + ", " + shelter.getZipcode();

        Picasso.with(context)
                .load(Network.API_LOCATION + shelter.getThumbPath())
                .error(R.drawable.profile_example)
                .into(holder.thumbnail);
        holder.name.setText(shelter.getName());
        holder.address.setText(shelter.getAddress());
        holder.city.setText(city);

        if (owner) {
            holder.update.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
        }
        else {
            holder.update.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return shelters.size();
    }

    public void update(List<Shelter> shelters) {
        this.shelters.clear();
        this.shelters.addAll(shelters);
        notifyDataSetChanged();
    }


}
