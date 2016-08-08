package com.eip.red.caritathelp.Views.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by pierr on 05/04/2016.
 */

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.DataObjectHolder> {

    private List<News>          newsList;
    private DateTimeFormatter   formatter;
    private DateTimeFormatter   newFormatter;


    public HomeRVAdapter() {
        newsList = new ArrayList<>();
        formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withLocale(Locale.FRANCE);
        newFormatter = DateTimeFormat.forPattern("dd MMMM 'à' HH:mm").withLocale(Locale.FRANCE);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView   image;
        TextView    title;
        TextView    date;
        TextView    description;

        public DataObjectHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            description = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Event event = visibleObjects.get(getAdapterPosition());
//
//            if (event != null)
//                presenter.navigateToEventView(event);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        News        news = newsList.get(position);
        DateTime    dt = formatter.parseDateTime(news.getCreated_at());

        // Set Image & Title & description
        switch (news.getNews_type()) {
            case News.TYPE_VOLUNTEER:
                Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION + news.getVolunteer_thumb_path(), R.drawable.profile_example);
                holder.title.setText(news.getVolunteer_name());
                holder.description.setText(news.getContent());
                break;
            case News.TYPE_ORGANISATION:
                Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION + news.getAssoc_thumb_path(), R.drawable.profile_example);
                holder.title.setText(news.getAssoc_name());
                holder.description.setText(news.getContent());
                break;
            case News.TYPE_EVENT:
                Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION + news.getEvent_thumb_path(), R.drawable.profile_example);
                holder.title.setText(news.getEvent_thumb_path());
                holder.description.setText(news.getContent());
                break;
            default:
                Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION + news.getVolunteer_thumb_path(), R.drawable.profile_example);
                holder.title.setText(news.getVolunteer_name());
                holder.description.setText(news.getContent());
                break;
        }

        // Set Date
        holder.date.setText(newFormatter.print(dt));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void update(List<News> newsList2) {
        newsList.clear();
        newsList.addAll(newsList2);
        notifyDataSetChanged();
    }
}
