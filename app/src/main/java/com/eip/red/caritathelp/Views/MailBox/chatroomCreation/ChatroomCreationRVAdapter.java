package com.eip.red.caritathelp.Views.MailBox.chatroomCreation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Search.Search;
import com.eip.red.caritathelp.Presenters.mailbox.ChatroomCreationPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pierr on 13/10/2016.
 */

public class ChatroomCreationRVAdapter extends RecyclerView.Adapter<ChatroomCreationRVAdapter.DataObjectHolder> {

    private ChatroomCreationPresenter presenter;
    private List<Search> searchList;

    public ChatroomCreationRVAdapter(ChatroomCreationPresenter presenter) {
        this.presenter = presenter;
        searchList = new ArrayList<>();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.thumbnail) ImageView thumbnail;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.added) ImageView added;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Search search = searchList.get(getAdapterPosition());

            if (search != null) {
                if (search.isAdded()) {
                    search.setAdded(false);
                    added.setVisibility(View.GONE);
                }
                else {
                    search.setAdded(true);
                    added.setVisibility(View.VISIBLE);
                    presenter.addTag(search);
                }
            }
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mailbox_chatroom_creation_rv_row, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Search search = searchList.get(position);

        holder.name.setText(search.getName());
        Network.loadImage(holder.thumbnail.getContext(), holder.thumbnail, Network.API_LOCATION + search.getThumb_path(), R.drawable.profile_example);

        if (search.isAdded())
            holder.added.setVisibility(View.VISIBLE);
        else
            holder.added.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public void update(List<Search> searchList) {
        this.searchList.clear();
        this.searchList.addAll(searchList);
        notifyDataSetChanged();
    }

    public void removeData() {
        searchList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
