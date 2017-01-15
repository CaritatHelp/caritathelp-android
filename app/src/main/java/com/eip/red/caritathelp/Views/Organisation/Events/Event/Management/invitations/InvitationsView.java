package com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.invitations;

import android.graphics.Typeface;
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
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Organisation.Guest;
import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management.InvitationsPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.BaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 15/01/2017.
 */

public class InvitationsView extends Fragment implements BaseView, Invitations.View {

    @BindView(R.id.btn_invitations_waiting) TextView waitingInvitationsBtn;
    @BindView(R.id.btn_invitations_sent) TextView sentInvitationsBtn;
    @BindView(R.id.recycler_view_invitations_sent) RecyclerView invitSentRv;
    @BindView(R.id.recycler_view_invitations_waiting) RecyclerView invitWaitingRv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private InvitationsPresenter presenter;
    private InvitationsWaitingRvAdapter  waitingInvitAdapter;
    private InvitationsSentRvAdapter sentInvitAdapter;
    private AlertDialog dialog;

    public static InvitationsView newInstance(int eventId) {
        InvitationsView fragment = new InvitationsView();
        Bundle args = new Bundle();

        args.putInt("event id", eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model & Id Organisation
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     id = getArguments().getInt("event id");

        // Init Presenter
        presenter = new InvitationsPresenter(this, user, id);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View    view = inflater.inflate(R.layout.fragment_organisation_event_management_invitations, container, false);
        ButterKnife.bind(this, view);

        waitingInvitAdapter = new InvitationsWaitingRvAdapter(presenter);
        invitWaitingRv.setAdapter(waitingInvitAdapter);
        invitWaitingRv.setLayoutManager(new LinearLayoutManager(getContext()));

        sentInvitAdapter = new InvitationsSentRvAdapter(presenter);
        invitSentRv.setAdapter(sentInvitAdapter);
        invitSentRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getInvitationsSent();
        presenter.getInvitationsWaiting();
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
    public void updateInvitSent(List<Guest> guests) {
        sentInvitAdapter.update(guests);
    }

    @Override
    public void updateInvitSent(int rvPosition) {
        sentInvitAdapter.update(rvPosition);
    }

    @Override
    public void updateInvitWaiting(List<Guest> guests) {
        waitingInvitAdapter.update(guests);
    }

    @Override
    public void updateInvitWaiting(int rvPosition) {
        waitingInvitAdapter.update(rvPosition);
    }

    @OnClick(R.id.btn_invitations)
    public void onClickInvitationsBtn() {
        presenter.goToSendInvitationView();
    }

    @OnClick(R.id.btn_invitations_waiting)
    public void onClickWaintingInvitationsBtn() {
        waitingInvitationsBtn.setTypeface(Typeface.DEFAULT_BOLD);
        sentInvitationsBtn.setTypeface(Typeface.DEFAULT);
        invitSentRv.setVisibility(View.GONE);
        invitWaitingRv.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_invitations_sent)
    public void onClickSentInvitationsBtn() {
        waitingInvitationsBtn.setTypeface(Typeface.DEFAULT);
        sentInvitationsBtn.setTypeface(Typeface.DEFAULT_BOLD);
        invitSentRv.setVisibility(View.VISIBLE);
        invitWaitingRv.setVisibility(View.GONE);
    }


}
