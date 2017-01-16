package com.eip.red.caritathelp.Views.Organisation.Events.Event.emergency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.EmergencyInvitationsPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.BaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pierr on 16/01/2017.
 */

public class EmergencyInvitationsView extends Fragment implements BaseView, EmergencyInvitations.View {

    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private EmergencyInvitationsPresenter presenter;
    private EmergencyInvitationsRvAdapter adapter;
    private AlertDialog dialog;

    public static EmergencyInvitationsView newInstance(int eventId) {
        EmergencyInvitationsView fragment = new EmergencyInvitationsView();
        Bundle args = new Bundle();

        args.putString("page", "Invitations acceptées");
        args.putInt("event id", eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     eventId = getArguments().getInt("event id");

        presenter = new EmergencyInvitationsPresenter(this, user, eventId);
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organisation_event_emergency_invitations, container, false);
        ButterKnife.bind(this, view);

        RecyclerView recyclerView = ButterKnife.findById(view, R.id.recycler_view);
        adapter = new EmergencyInvitationsRvAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getString("page"));
        presenter.getVolunteers();
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
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void update(List<User> volunteers) {
        adapter.update(volunteers);
    }
}
