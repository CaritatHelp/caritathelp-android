package com.eip.red.caritathelp.Views.Organisation.Events.Event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */
public class OrganisationEventRVAdapter extends RecyclerView.Adapter<OrganisationEventRVAdapter.EventObjectHolder> {

    private List<News> newsList;

    public OrganisationEventRVAdapter() {
        newsList = new ArrayList<>();
    }

    public static class EventObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView   image;
        TextView    title;
        TextView    date;
        TextView    description;

        public EventObjectHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            description = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    @Override
    public EventObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_event_rv_row, parent, false);
        EventObjectHolder   holder = new EventObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(EventObjectHolder holder, int position) {
//        holder.image.setText();
//        holder.title.setText(visibleObjects.get(position).getTitle());
//        holder.date.setText();
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void update(List<News> newsList2) {
        newsList.clear();
        newsList.addAll(newsList2);
        notifyDataSetChanged();
    }
}
