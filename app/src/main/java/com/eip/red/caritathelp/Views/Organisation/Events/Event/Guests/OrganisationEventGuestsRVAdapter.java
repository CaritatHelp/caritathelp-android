package com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Organisation.Guest;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests.OrganisationEventGuestsPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView   image;
        TextView    name;

        public DataObjectHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Guest   guest = visibleObjects.get(getAdapterPosition());

//            if (guest != null)
//                presenter.navigateToEventView(event);
        }
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_event_guests_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Guest   guest = visibleObjects.get(position);
        String  name = guest.getFirstname() + " " + guest.getLastname();

        holder.name.setText(name);
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
