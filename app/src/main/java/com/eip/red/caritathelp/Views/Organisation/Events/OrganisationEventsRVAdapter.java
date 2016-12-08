package com.eip.red.caritathelp.Views.Organisation.Events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Presenters.Organisation.Events.OrganisationEventsPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by pierr on 17/03/2016.
 */

public class OrganisationEventsRVAdapter extends RecyclerView.Adapter<OrganisationEventsRVAdapter.DataObjectHolder> {

    private final OrganisationEventsPresenter presenter;

    private List<Event> visibleObjects;
    private List<Event> allObjects;

    public OrganisationEventsRVAdapter(OrganisationEventsPresenter presenter) {
        this.presenter = presenter;

        visibleObjects = new ArrayList<>();
        allObjects = new ArrayList<>();

    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView   image;
        TextView    title;
        TextView    date;

        public DataObjectHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.organisation_events_image);
            title = (TextView) itemView.findViewById(R.id.organisation_events_title);
            date = (TextView) itemView.findViewById(R.id.organisation_events_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Event   event = visibleObjects.get(getAdapterPosition());

            if (event != null)
                presenter.navigateToEventView(event);
        }
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_events_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
//        holder.image.setText();
        holder.title.setText(visibleObjects.get(position).getTitle());
//        holder.date.setText();
    }

    @Override
    public int getItemCount() {
        return (visibleObjects.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void update(List<Event>  events) {
        visibleObjects.clear();
        allObjects.clear();

        visibleObjects.addAll(events);
        allObjects.addAll(events);

        notifyDataSetChanged();
    }

    public void flushFilter(){
        visibleObjects.clear();
        visibleObjects.addAll(allObjects);
        notifyDataSetChanged();
    }

    public void filter(String queryText) {
        visibleObjects.clear();

        for (Event event :  allObjects) {
            if (event.getTitle().toLowerCase(Locale.getDefault()).contains(queryText))
                visibleObjects.add(event);
        }
        notifyDataSetChanged();
    }

//    public interface OnItemClickListener {
//        public void onItemClick(View view , int position);
//    }
//
//    public interface MyClickListener {
//        public void onItemClick(int position, View v);
//    }
}
