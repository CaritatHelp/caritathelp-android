package com.eip.red.caritathelp.Views.SubMenu.Profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.ProfilePresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Home.HomeRVAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 11/05/2016.
 */

public class ProfileView extends Fragment implements IProfileView, View.OnClickListener {

    private ProfilePresenter    presenter;

    private HashMap<String, ImageButton>    friendshipBtn;
    private ProfileRVAdapter                adapter;
    private AlertDialog                     dialog;

    @BindView(R.id.image) CircularImageView profileImg;
    @BindView(R.id.image_user) ImageView imageUser;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.btn_send_message) ImageButton messageBtn;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;


    public static ProfileView newInstance(int id) {
        ProfileView    myFragment = new ProfileView();

        Bundle args = new Bundle();

        args.putInt("page", R.string.view_name_submenu_profile);
        args.putInt("id", id);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     id = getArguments().getInt("id");
        presenter = new ProfilePresenter(this, user, id);

        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submenu_profile, container, false);
        ButterKnife.bind(this, view);

        // Init UI Element
        friendshipBtn = new HashMap<>();
        friendshipBtn.put(User.FRIENDSHIP_FRIEND, (ImageButton) view.findViewById(R.id.btn_friendship_friend));
        friendshipBtn.put(User.FRIENDSHIP_NONE, (ImageButton) view.findViewById(R.id.btn_friendship_none));
        friendshipBtn.put(User.FRIENDSHIP_INVITATION_SENT, (ImageButton) view.findViewById(R.id.btn_friendship_invitation_sent));
        friendshipBtn.put(User.FRIENDSHIP_INVITATIONS_RECEIVED_CONFIRM, (ImageButton) view.findViewById(R.id.btn_friendship_confirm));
        friendshipBtn.put(User.FRIENDSHIP_INVITATIONS_RECEIVED_REMOVE, (ImageButton) view.findViewById(R.id.btn_friendship_remove));

        // Init Listener
        profileImg.setOnClickListener(this);
        for (ImageButton imageButton : friendshipBtn.values()) {
            imageButton.setOnClickListener(this);
        }
        messageBtn.setOnClickListener(this);
        ButterKnife.findById(view, R.id.btn_friends).setOnClickListener(this);
        ButterKnife.findById(view, R.id.btn_organisations).setOnClickListener(this);
        ButterKnife.findById(view, R.id.btn_events).setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeRefreshLayout();
        initRecyclerView();
        getActivity().setTitle(getArguments().getInt("page"));
        presenter.getData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When an Image is picked
        if (requestCode == ProfilePresenter.RESULT_LOAD_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadProfileImg(profileImg, data);
        else if (requestCode == ProfilePresenter.RESULT_CAPTURE_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadProfileImg(profileImg, data);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getNews(true);
            }
        });
    }

    private void initRecyclerView() {
        adapter = new ProfileRVAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(true);
        ViewCompat.setNestedScrollingEnabled(recyclerView, true);
        recyclerView.setHasFixedSize(false);
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
    public void updateRecyclerViewData(List<News> newsList) {
        adapter.update(newsList);
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    @OnClick(R.id.image_user)
    public void onClickImageUser() {
        presenter.goToProfileView();
    }

    @OnClick(R.id.text_view_post_news)
    public void onClickPostTextViewBtn() {
        presenter.goToPostView();
    }

    public CircularImageView getProfileImg() {
        return profileImg;
    }

    public ImageView getImageUser() {
        return imageUser;
    }

    public TextView getName() {
        return name;
    }

    public ImageButton getFriendshipBtn(String key) {
        return friendshipBtn.get(key);
    }

    public ImageButton getMessageBtn() {
        return messageBtn;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
