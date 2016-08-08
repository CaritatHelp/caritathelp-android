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
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.Home.HomePresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

/**
 * Created by pierr on 16/11/2015.
 */

public class HomeView extends Fragment implements IHomeView {

    private HomePresenter   presenter;

    private RecyclerView        recyclerView;
    private SwipeRefreshLayout  swipeRefreshLayout;
    private ProgressBar         progressBar;
    private AlertDialog         dialog;

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

        // Get User Model
        User user = ((MainActivity) getActivity()).getModelManager().getUser();

        // Init Presenter
        presenter = new HomePresenter(this, user.getToken());

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_home, container, false);

        // Init UI Element
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        initSwipeRefreshLayout();
        initRecyclerView(view);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init News Model
        presenter.getNews(false);
    }

    private void initSwipeRefreshLayout() {
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);

        // Init Refresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Get Friends Model
                presenter.getNews(true);
            }
        });
    }

    private void initRecyclerView(View view) {
        // Init RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new HomeRVAdapter());

        // Init LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Set Options to enable toolbar display/hide
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
        ((HomeRVAdapter) recyclerView.getAdapter()).update(newsList);
    }
}

