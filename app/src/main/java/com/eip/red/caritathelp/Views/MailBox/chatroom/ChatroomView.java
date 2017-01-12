package com.eip.red.caritathelp.Views.MailBox.chatroom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.mailbox.ChatroomMessage;
import com.eip.red.caritathelp.Presenters.mailbox.ChatroomPresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 13/10/2016.
 */

public class ChatroomView extends Fragment implements IChatroom.View {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.message) EditText message;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private ChatroomPresenter presenter;
    private ChatroomRVAdapter adapter;
    private LinearLayoutManager layoutManager;
    private AlertDialog dialog;

    public static Fragment newInstance(String name, Integer chatroomId) {
        ChatroomView     fragment = new ChatroomView();
        Bundle args = new Bundle();

        args.putString("page", name);
        args.putInt("chatroom id", chatroomId);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        presenter = new ChatroomPresenter(this, user, getArguments().getInt("chatroom id"));
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mailbox_chatroom, container, false);
        ButterKnife.bind(this, view);

        adapter = new ChatroomRVAdapter(getResources(), presenter);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getString("page"));
        presenter.getMessages();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void updateRVData(List<ChatroomMessage> messages) {
        adapter.update(messages);
        layoutManager.scrollToPosition(adapter.getItemCount() - 1);
        message.getText().clear();
    }

    @Override
    public void addMessage(ChatroomMessage message) {
//        presenter.getMessages();

        adapter.add(message);
        layoutManager.smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
//        recyclerView.invalidate();

//        layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
//        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @OnClick(R.id.btn_send)
    public void onClickSendBtn() {
        if (!TextUtils.isEmpty(message.getText())) {
            presenter.sendMessage(message.getText().toString());
            message.getText().clear();
        }
    }
}
