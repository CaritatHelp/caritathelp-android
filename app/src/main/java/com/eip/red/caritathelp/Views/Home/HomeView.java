package com.eip.red.caritathelp.Views.Home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Home.HomePresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by pierr on 16/11/2015.
 */

public class HomeView extends Fragment implements IHomeView {

    private HomePresenter   presenter;

    private Unbinder            unbinder;
    private HomeRVAdapter       adapter;
    private AlertDialog         dialog;

    @BindView(R.id.image_user)          ImageView           imageUser;
    @BindView(R.id.recycler_view)       RecyclerView        recyclerView;
    @BindView(R.id.refresh_layout)      SwipeRefreshLayout  swipeRefreshLayout;
    @BindView(R.id.progress_bar)        ProgressBar         progressBar;

    public static Fragment newInstance() {
        HomeView     fragment = new HomeView();
        Bundle          args = new Bundle();

        args.putInt("page", R.string.view_name_home);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        presenter = new HomePresenter(this, user);
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        initSwipeRefreshLayout();
        initRecyclerView();
        getActivity().setTitle(getArguments().getInt("page"));
        presenter.initUserImage(imageUser);
        presenter.getNews(false);
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
        adapter = new HomeRVAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(true);
        ViewCompat.setNestedScrollingEnabled(recyclerView, true);
        recyclerView.setHasFixedSize(false);
    }

    @OnClick(R.id.image_user)
    public void onClickImageUser() {
        presenter.goToProfileView();
    }

    @OnClick(R.id.text_view_post_news)
    public void onClickPostTextViewBtn() {
        presenter.goToPostView();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
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
        if (newsList != null && newsList.size() > 0)
            adapter.update(newsList);
    }
}

