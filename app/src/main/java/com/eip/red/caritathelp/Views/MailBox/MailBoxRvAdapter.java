package com.eip.red.caritathelp.Views.MailBox;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.mailbox.Chatroom;
import com.eip.red.caritathelp.Presenters.mailbox.MailBoxPresenter;
import com.eip.red.caritathelp.R;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pierr on 13/10/2016.
 */

public class MailBoxRvAdapter extends RecyclerView.Adapter<MailBoxRvAdapter.DataObjectHolder> {

    private MailBoxPresenter presenter;
    private List<Chatroom> chatrooms;
    private String informationStr;

    public MailBoxRvAdapter(Resources resources, MailBoxPresenter presenter) {
        this.presenter = presenter;
        chatrooms = new ArrayList<>();
        informationStr = resources.getString(R.string.mailbox_chatrooms_information);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.date) TextView date;
        @BindView(R.id.number_volunteers_messages) TextView information;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Chatroom chatroom = chatrooms.get(getAdapterPosition());

            if (chatroom != null)
                presenter.goToChatroom(chatroom.getName(), chatroom.getId());
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mailbox_rv_row, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Chatroom chatroom = chatrooms.get(position);

        holder.name.setText(chatroom.getName());
        holder.information.setText(String.format(informationStr, chatroom.getVolunteersNumber(), chatroom.getMessagesNumber()));
        try {
            long now;

            if (chatroom.getUpdatedAt() != null)
                now = ISO8601Utils.parse(chatroom.getUpdatedAt(), new ParsePosition(0)).getTime();
            else
                now = ISO8601Utils.parse(chatroom.getCreatedAt(), new ParsePosition(0)).getTime();
            holder.date.setText(DateUtils.getRelativeTimeSpanString(now));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return chatrooms.size();
    }

    public void update(List<Chatroom> chatrooms) {
        this.chatrooms.clear();
        this.chatrooms.addAll(chatrooms);
        notifyDataSetChanged();
    }


}
