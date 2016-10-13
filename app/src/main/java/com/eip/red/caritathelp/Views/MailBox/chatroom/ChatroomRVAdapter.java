package com.eip.red.caritathelp.Views.MailBox.chatroom;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.mailbox.ChatroomMessage;
import com.eip.red.caritathelp.Presenters.mailbox.ChatroomPresenter;
import com.eip.red.caritathelp.R;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 13/10/2016.
 */

public class ChatroomRVAdapter extends RecyclerView.Adapter<ChatroomRVAdapter.DataObjectHolder> {

    private ChatroomPresenter presenter;
    private List<ChatroomMessage> messages;
    private Integer userId;
    private String fomatter;

    public ChatroomRVAdapter(Resources resources, ChatroomPresenter presenter) {
        this.presenter = presenter;
        userId = presenter.getUserId();
        messages = new ArrayList<>();
        fomatter = resources.getString(R.string.mailbox_chatroom_name_date);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail) ImageView thumbnail;
        @BindView(R.id.name_left) TextView nameLeft;
        @BindView(R.id.name_right) TextView nameRight;
        @BindView(R.id.message_left) TextView messageLeft;
        @BindView(R.id.message_right) TextView messageRight;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.thumbnail)
        public void onClickThumbnail() {
            ChatroomMessage chatroomMessage = messages.get(getAdapterPosition());

            if (chatroomMessage != null)
                presenter.goToProfileView(chatroomMessage.getVolunteerId());
        }

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mailbox_chatroom_rv_row, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        ChatroomMessage message = messages.get(position);

        if (!message.getVolunteerId().equals(userId)) {
            Network.loadImage(holder.thumbnail.getContext(), holder.thumbnail, Network.API_LOCATION + message.getThumbPath(), R.drawable.profile_example);
            holder.messageLeft.setText(message.getContent());

            try {
                long now;
                if (message.getUpdatedAt() != null)
                    now = ISO8601Utils.parse(message.getUpdatedAt(), new ParsePosition(0)).getTime();
                else
                    now = ISO8601Utils.parse(message.getCreatedAt(), new ParsePosition(0)).getTime();
                holder.nameLeft.setText(String.format(fomatter, message.getFullname(), DateUtils.getRelativeTimeSpanString(now)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.nameLeft.setVisibility(View.VISIBLE);
            holder.nameRight.setVisibility(View.GONE);
            holder.thumbnail.setVisibility(View.VISIBLE);
            holder.messageLeft.setVisibility(View.VISIBLE);
            holder.messageRight.setVisibility(View.GONE);
        }
        else {
            holder.messageRight.setText(message.getContent());
            try {
                long now;
                if (message.getUpdatedAt() != null)
                    now = ISO8601Utils.parse(message.getUpdatedAt(), new ParsePosition(0)).getTime();
                else
                    now = ISO8601Utils.parse(message.getCreatedAt(), new ParsePosition(0)).getTime();
                holder.nameRight.setText(DateUtils.getRelativeTimeSpanString(now));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.nameLeft.setVisibility(View.GONE);
            holder.nameRight.setVisibility(View.VISIBLE);
            holder.thumbnail.setVisibility(View.GONE);
            holder.messageLeft.setVisibility(View.GONE);
            holder.messageRight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void update(List<ChatroomMessage> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

}
