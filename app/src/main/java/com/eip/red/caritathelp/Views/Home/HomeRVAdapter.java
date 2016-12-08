package com.eip.red.caritathelp.Views.Home;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Presenters.Home.HomePresenter;
import com.eip.red.caritathelp.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 05/04/2016.
 */

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.DataObjectHolder> {

    private HomePresenter       presenter;
    private List<News>          newsList;
    private DateTimeFormatter   formatter;
    private DateTimeFormatter   newFormatter;

    public HomeRVAdapter(HomePresenter presenter) {
        this.presenter = presenter;
        newsList = new ArrayList<>();
        formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withLocale(Locale.FRANCE);
        newFormatter = DateTimeFormat.forPattern("dd MMMM 'à' HH:mm").withLocale(Locale.FRANCE);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)                   ImageView       image;
        @BindView(R.id.title)                   TextView        title;
        @BindView(R.id.date)                    TextView        date;
        @BindView(R.id.description)             TextView        description;
        @BindView(R.id.btn_comment)             LinearLayout    commentsBtn;
        @BindView(R.id.text_view_comment_amount)TextView        commentsNumber;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.image)
        public void OnClickImage() {
            News news = newsList.get(getAdapterPosition());

            if (news != null)
                presenter.goToProfileView(news);
        }

        @OnClick(R.id.title)
        public void OnClickTitle() {
            News news = newsList.get(getAdapterPosition());

            if (news != null)
                presenter.goToProfileView(news);
        }

        @OnClick(R.id.description)
        public void OnClickDescription() {
            News    news = newsList.get(getAdapterPosition());

            if (news != null)
                presenter.goToCommentView(news);
        }

        @OnClick(R.id.btn_comment)
        public void OnClickCommentBtn() {
            News    news = newsList.get(getAdapterPosition());

            if (news != null)
                presenter.goToCommentView(news);
        }

        @OnClick(R.id.text_view_comment_amount)
        public void OnClickCommentAmountBtn() {
            News    news = newsList.get(getAdapterPosition());

            if (news != null)
                presenter.goToCommentView(news);
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
        int         commentsNumberValue = news.getNumber_comments();

        if (news.isAs_group()) {
            Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION + news.getGroup_thumb_path(), R.drawable.profile_example);
            holder.title.setText(news.getGroup_name());
            holder.description.setText(news.getContent());
        }
        else {
            Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION + news.getVolunteer_thumb_path(), R.drawable.profile_example);
            holder.title.setText(news.getVolunteer_name());
            holder.description.setText(news.getContent());
        }

        if (commentsNumberValue > 0) {
            if (commentsNumberValue == 1)
                holder.commentsNumber.setText(new StringBuilder(String.valueOf(commentsNumberValue)).append(" commentaire"));
            else
                holder.commentsNumber.setText(new StringBuilder(String.valueOf(commentsNumberValue)).append(" commentaires"));
            holder.commentsNumber.setVisibility(View.VISIBLE);
            holder.commentsBtn.setGravity(Gravity.NO_GRAVITY);
        }
        else {
            holder.commentsNumber.setVisibility(View.GONE);
            holder.commentsBtn.setGravity(Gravity.CENTER);
        }

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

    public void addNews(News news) {
        newsList.add(0, news);
        notifyItemInserted(0);
    }
}
