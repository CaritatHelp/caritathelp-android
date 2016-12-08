package com.eip.red.caritathelp.Views.MailBox;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.mailbox.Chatroom;
import com.eip.red.caritathelp.Presenters.mailbox.MailBoxPresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 19/01/2016.
 */

public class MailBoxView extends Fragment implements MailBox.View {

    @BindView(R.id.refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    private AlertDialog dialog;

    private MailBoxPresenter presenter;
    private MailBoxRvAdapter adapter;

    public static Fragment newInstance() {
        MailBoxView     fragment = new MailBoxView();
        Bundle          args = new Bundle();

        args.putInt("page", R.string.view_name_mailbox);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        presenter = new MailBoxPresenter(this, user);
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mailbox, container, false);
        ButterKnife.bind(this, view);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getInt("page"));

        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getChatrooms(true);
            }
        });

        RecyclerView recyclerView = ButterKnife.findById(view, R.id.recycler_view);
        adapter = new MailBoxRvAdapter(getResources(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);

        presenter.getChatrooms(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void updateRVData(List<Chatroom> chatrooms) {
        adapter.update(chatrooms);
    }

    @OnClick(R.id.btn_create)
    public void onClickCreateBtn() {
        presenter.goToChatroomCreationView();
    }

    public MailBoxPresenter getPresenter() {
        return presenter;
    }
}
