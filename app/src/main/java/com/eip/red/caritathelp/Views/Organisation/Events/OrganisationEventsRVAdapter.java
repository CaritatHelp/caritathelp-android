package com.eip.red.caritathelp.Views.Organisation.Events;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Presenters.Organisation.Events.OrganisationEventsPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
    private DateTimeFormatter formatter;
    private DateTimeFormatter   newFormatter;


    public OrganisationEventsRVAdapter(OrganisationEventsPresenter presenter) {
        this.presenter = presenter;

        visibleObjects = new ArrayList<>();
        allObjects = new ArrayList<>();
        formatter = DateTimeFormat.forPattern("yyyy-MM-dd' 'HH:mm:ss.SSSSSS").withLocale(Locale.FRANCE);
        newFormatter = DateTimeFormat.forPattern("'Le' E dd MMMM Y 'à' HH:mm");//.withZone(timeZone);
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
        Event event = visibleObjects.get(position);

        if (event != null) {
            String  date = event.getBegin();

            Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + event.getThumb_path(), R.drawable.logo_caritathelp_2017_picture_only_normal);
            holder.title.setText(Tools.upperCaseFirstLetter(event.getTitle()));
            if (date != null && !TextUtils.isEmpty(date)) {
                DateTime dt = formatter.parseDateTime(date);
                holder.date.setText(newFormatter.print(dt));
            }
            else
                holder.date.setVisibility(View.GONE);
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

    public void update(List<Event>  events) {
        visibleObjects.clear();
        allObjects.clear();

        visibleObjects.addAll(events);
        allObjects.addAll(events);

        notifyDataSetChanged();
    }

}
