package com.eip.red.caritathelp.Views.Organisation.Management.members;

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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Management.OrganisationManagementInvitationsPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 12/01/2017.
 */

public class OrganisationManagementInvitationsView extends Fragment implements OrganisationManagementInvitations.View {

    @BindView(R.id.btn_invitations_waiting) TextView waitingInvitationsBtn;
    @BindView(R.id.btn_invitations_sent) TextView sentInvitationsBtn;
    @BindView(R.id.recycler_view_invitations_sent) RecyclerView invitSentRv;
    @BindView(R.id.recycler_view_invitations_waiting) RecyclerView invitWaitingRv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private OrganisationManageInvitWaitingRvAdapter waitingInvitAdapter;
    private OrganisationManageInvitSentRvAdapter sentInvitAdapter;

    private OrganisationManagementInvitationsPresenter presenter;
    private AlertDialog dialog;

    public static OrganisationManagementInvitationsView newInstance(int idOrganisation) {
        OrganisationManagementInvitationsView    myFragment = new OrganisationManagementInvitationsView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_organisation_management_member);
        args.putInt("organisation id", idOrganisation);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model & Id Organisation
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     organisationId = getArguments().getInt("organisation id");

        // Init Presenter
        presenter = new OrganisationManagementInvitationsPresenter(this, user, organisationId);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View    view = inflater.inflate(R.layout.fragment_organisation_management_invitations, container, false);
        ButterKnife.bind(this, view);

        waitingInvitAdapter = new OrganisationManageInvitWaitingRvAdapter(presenter);
        invitWaitingRv.setAdapter(waitingInvitAdapter);
        invitWaitingRv.setLayoutManager(new LinearLayoutManager(getContext()));

        sentInvitAdapter = new OrganisationManageInvitSentRvAdapter(presenter);
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
    public void updateInvitSent(List<Member> members) {
        sentInvitAdapter.update(members);
    }

    @Override
    public void updateInvitSent(int rvPosition) {
        sentInvitAdapter.update(rvPosition);
    }

    @Override
    public void updateInvitWaiting(List<Member> members) {
        waitingInvitAdapter.update(members);
    }

    @Override
    public void updateInvitWaiting(int rvPosition) {
        waitingInvitAdapter.update(rvPosition);
    }

    @OnClick(R.id.btn_invitations)
    public void onClickInvitationsBtn(Button button) {
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
