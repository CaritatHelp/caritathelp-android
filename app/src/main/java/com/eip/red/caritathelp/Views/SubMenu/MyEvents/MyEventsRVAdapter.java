package com.eip.red.caritathelp.Views.SubMenu.MyEvents;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Presenters.SubMenu.MyEvents.MyEventsPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by pierr on 18/03/2016.
 */

public class MyEventsRVAdapter extends RecyclerView.Adapter<MyEventsRVAdapter.DataObjectHolder> {

    private final MyEventsPresenter presenter;

    private List<Event> visibleObjects;
    private List<Event> allObjects;

    private DateTimeFormatter   formatter;
    private DateTimeFormatter   formatter2;
    private DateTimeFormatter   newFormatter;

    public MyEventsRVAdapter(MyEventsPresenter presenter) {
        this.presenter = presenter;

        // Init Objects
        visibleObjects = new ArrayList<>();
        allObjects = new ArrayList<>();

        // Init DateTimeFormatter
//        formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");//.withZone(timeZone);
        formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.FRANCE);
        formatter2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").withLocale(Locale.FRANCE);
        newFormatter = DateTimeFormat.forPattern("'Le' E dd MMMM Y 'à' HH:mm");//.withZone(timeZone);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircularImageView   image;
        TextView            title;
        TextView            date;
        TextView            location;

        public DataObjectHolder(View itemView) {
            super(itemView);
            image = (CircularImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            location = (TextView) itemView.findViewById(R.id.location);
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
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_submenu_my_events_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Event   event = visibleObjects.get(position);
        String  title = event.getTitle();
        String  date = event.getBegin();
        String  location = event.getPlace();

        // Set Image
        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + event.getThumb_path(), R.drawable.logo_caritathelp_2017_picture_only_normal);

        // Set Title
        holder.title.setText(Tools.upperCaseFirstLetter(title));

        // Set date
        if (date != null && !TextUtils.isEmpty(date)) {
            System.out.println("Date : " + date);

            try {
                DateTime dt = formatter.parseDateTime(date);
                holder.date.setText(newFormatter.print(dt));
            }
            catch (Exception exception) {
                DateTime dt = formatter2.parseDateTime(date);
                holder.date.setText(newFormatter.print(dt));
            }

//            date = "2017-01-15T17:15:28.332+01:00";
//            DateTime dt = ISODateTimeFormat.dateTime().parseDateTime(date);
//            holder.date.setText(newFormatter.print(dt));

//            DateTime dt = new DateTime(date, DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris")));
//            holder.date.setText(dt.toString());

//            LocalDateTime ldt = LocalDateTime.parse(date);
//            holder.date.setText(ldt.toString());

//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//            try {
//                Date dateStr = dateFormat.parse(date);
//                holder.date.setText(new SimpleDateFormat("'Le' E dd MMMM Y 'à' HH:mm", Locale.FRANCE).format(dateStr));
//            }
//            catch (ParseException e) {
//                e.printStackTrace();
//            }
        }
        else
            holder.date.setVisibility(View.GONE);

        // Set Location
        if (date != null && !TextUtils.isEmpty(location))
            holder.location.setText(location);
        else
            holder.location.setVisibility(View.GONE);
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

}
