package com.eip.red.caritathelp.Views.SubMenu.MyEvents;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.SubMenu.MyEvents.MyEventsPresenter;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 18/03/2016.
 */

public class MyEventsView extends Fragment implements IMyEventsView, View.OnClickListener {

    private MyEventsPresenter   presenter;

    private RecyclerView        recyclerView;
    private MyEventsRVAdapter   adapter;
    private View                dividerV;
    private Button              createBtn;

    private SwipeRefreshLayout  swipeRefreshLayout;
    private Spinner             spinner;
    private ProgressBar         progressBar;
    private AlertDialog         dialog;

    public static MyEventsView newInstance(int userId, boolean mainUser) {
        MyEventsView    myFragment = new MyEventsView();

        Bundle args = new Bundle();

        // Check if the user is the MAIN user (in order to change the title "My Events" by "Events").
        if (mainUser)
            args.putInt("page", R.string.view_name_submenu_my_events);
        else
            args.putInt("page", R.string.view_name_organisation_events);

        args.putInt("user id", userId);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model & Id Organisation
        User    mainUser = ((MainActivity) getActivity()).getModelManager().getUser();
        int     userId = getArguments().getInt("user id");

        // Init Presenter
        presenter = new MyEventsPresenter(this, mainUser, userId);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_submenu_my_events, container, false);

        // Init UI Element
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        dividerV = view.findViewById(R.id.divider_vertical);
        createBtn = (Button) view.findViewById(R.id.btn_create);

        // Set Create Button Visisbility
        if (presenter.isMainUser())
            setVisibilityCreationPart(View.VISIBLE);

        // Init RefreshLayout
        initSwipeRefreshLayout();

        // Init Spinner
        initSpinner(view);

        // Init RecyclerView
        initRecyclerView(view);

        // Init Listener
        view.findViewById(R.id.btn_create).setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init Events Model
        presenter.getMyEvents(true, "En ce moment", false);
    }

    private void initSwipeRefreshLayout() {
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);

        // Init Refresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Set Events Model
                presenter.getMyEvents(false, (String) spinner.getSelectedItem(), true);
            }
        });
    }

    private void initSpinner(View view) {
        // Init Spinner
        spinner = (Spinner) view.findViewById(R.id.spinner);

        // Init Listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.getMyEvents(false, (String) parent.getItemAtPosition(position), false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecyclerView(View view) {
        // Init Recycler Views
        recyclerView = (RecyclerView) view.findViewById(R.id.my_events_recycler_view);

        // Init Adapter
        adapter = new MyEventsRVAdapter(presenter);

        // Set Adapter
        recyclerView.setAdapter(adapter);

        // Init LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Set Options to enable toolbar display/hide
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        // Init Divider (between items)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setDialogError(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    public void setVisibilityCreationPart(int visibility) {
        dividerV.setVisibility(visibility);
        createBtn.setVisibility(visibility);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public MyEventsRVAdapter getAdapter() {
        return adapter;
    }
}
