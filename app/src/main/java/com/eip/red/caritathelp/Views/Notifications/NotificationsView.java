package com.eip.red.caritathelp.Views.Notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.Notifications.NotificationsPresenter;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 16/11/2015.
 */

public class NotificationsView extends Fragment implements INotificationsView {

    private NotificationsPresenter  presenter;

    private RecyclerView            volunteerRV;
    private RecyclerView            ownerRV;
    private NotificationsRVAdapter  rvAdapter;
    private ProgressBar             progressBar;
    private AlertDialog             dialog;

    public static Fragment newInstance() {
        NotificationsView       fragment = new NotificationsView();
        Bundle                  args = new Bundle();

        args.putInt("page", R.string.view_name_notifications);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Model
        Network network = ((MainActivity) getActivity()).getModelManager().getNetwork();
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();

        // Init Presenter
        presenter = new NotificationsPresenter(this, network, user);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Init UI Element
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Init RecyclerView
        initRecyclerView(view);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init Notifications Model
        presenter.getNotifications();
    }

    private void initRecyclerView(View view) {
        // Init RecyclerView
        volunteerRV = (RecyclerView) view.findViewById(R.id.recycler_view_volunteer);
        ownerRV = (RecyclerView) view.findViewById(R.id.recycler_view_owner);

        // Init & Set Adapter
        rvAdapter = new NotificationsRVAdapter(presenter);
        volunteerRV.setAdapter(rvAdapter);
//        ownerRV.setAdapter();

        // Init LayoutManager
        volunteerRV.setLayoutManager(new LinearLayoutManager(getContext()));
        ownerRV.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set Options to enable toolbar display/hide
        volunteerRV.setNestedScrollingEnabled(false);
        ownerRV.setNestedScrollingEnabled(false);
        volunteerRV.setHasFixedSize(false);
        ownerRV.setHasFixedSize(false);

        // Init Divider (between items)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL);
        volunteerRV.addItemDecoration(itemDecoration);
        ownerRV.addItemDecoration(itemDecoration);
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

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public NotificationsRVAdapter getRvAdapter() {
        return rvAdapter;
    }
}
