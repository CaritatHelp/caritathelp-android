package com.eip.red.caritathelp.Views.Home.Comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.comment.Comment;
import com.eip.red.caritathelp.Presenters.Home.Comment.CommentPresenter;
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
 * Created by pierr on 06/09/2016.
 */

public class CommentRVAdapter extends RecyclerView.Adapter<CommentRVAdapter.DataObjectHolder> {

    private CommentPresenter    presenter;
    private List<Comment>       comments;
    private DateTimeFormatter   formatter;
    private DateTimeFormatter   newFormatter;

    public CommentRVAdapter(CommentPresenter presenter) {
        this.presenter = presenter;
        comments = new ArrayList<>();
        formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withLocale(Locale.FRANCE);
        newFormatter = DateTimeFormat.forPattern("EEEE d MMM 'à' HH:mm").withLocale(Locale.FRANCE);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)       ImageView   image;
        @BindView(R.id.name)        TextView    name;
        @BindView(R.id.description) TextView    description;
        @BindView(R.id.date)        TextView    date;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.name)
        public void onClick() {
            Comment comment = comments.get(getAdapterPosition());

            if (comment != null)
                presenter.goToProfile(comment.getVolunteer_id());
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_comment_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Comment     comment = comments.get(position);
        String      name = comment.getFirstname() + " " + comment.getLastname();
        DateTime    dt = formatter.parseDateTime(comment.getCreated_at());

        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION + comment.getThumb_path(), R.drawable.profile_example);
        holder.name.setText(name);
        holder.description.setText(comment.getContent());
        holder.date.setText(newFormatter.print(dt));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void update(List<Comment> newsComments) {
        comments.clear();
        comments.addAll(newsComments);
        notifyDataSetChanged();
    }

    public int addComment(Comment comment) {
        int index = comments.size();

        comments.add(index, comment);
        notifyItemInserted(index);

        return index;
    }

}
