package com.eip.red.caritathelp.Views.Organisation.Events.Event;

import android.app.AlertDialog;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.OrganisationEventPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventView extends Fragment implements IOrganisationEventView, View.OnClickListener {

    private OrganisationEventPresenter presenter;

    private OrganisationEventRVAdapter  adapter;
    private AlertDialog                 dialog;

    @BindView(R.id.picture) ImageView picture;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.btn_post) ImageButton postBtn;
    @BindView(R.id.btn_guests) ImageButton guestBtn;
    @BindView(R.id.btn_informations) ImageButton informationsBtn;
    @BindView(R.id.btn_join) ImageButton joinBtn;
    @BindView(R.id.btn_quit) ImageButton quitBtn;
    @BindView(R.id.btn_emergency) ImageButton emergencyBtn;
    @BindView(R.id.btn_management) ImageButton managementBtn;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public static OrganisationEventView newInstance(int eventId, String title) {
        OrganisationEventView    myFragment = new OrganisationEventView();

        Bundle args = new Bundle();
        args.putString("page", title);
        args.putInt("event id", eventId);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     eventId = getArguments().getInt("event id");

        presenter = new OrganisationEventPresenter(this, user, eventId);
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_organisation_event, container, false);
        ButterKnife.bind(this, view);

        // Init Image Filter (Darken the image)
        LightingColorFilter lcf = new LightingColorFilter(0xFF888888, 0x00222222);
        picture.setColorFilter(lcf);

        initSwipeRefreshLayout();
        initRecyclerView();

        postBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
        guestBtn.setOnClickListener(this);
        informationsBtn.setOnClickListener(this);
        managementBtn.setOnClickListener(this);
        emergencyBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getString("page"));
        presenter.getData();
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
        adapter = new OrganisationEventRVAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
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
    public void updateRV(List<News> newsList) {
        adapter.update(newsList);
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    @OnClick(R.id.picture)
    public void onClickPicture() {
        presenter.goToGalleryPhotoView();
    }

    public ImageView getPicture() {
        return picture;
    }

    public ImageButton getPostBtn() {
        return postBtn;
    }

    public ImageButton getJoinBtn() {
        return joinBtn;
    }

    public ImageButton getQuitBtn() {
        return quitBtn;
    }

    public ImageButton getEmergencyBtn() {
        return emergencyBtn;
    }

    public ImageButton getManagementBtn() {
        return managementBtn;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
