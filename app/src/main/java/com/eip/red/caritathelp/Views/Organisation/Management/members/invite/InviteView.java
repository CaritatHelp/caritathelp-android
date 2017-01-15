package com.eip.red.caritathelp.Views.Organisation.Management.members.invite;

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
import com.eip.red.caritathelp.Presenters.Organisation.Management.InvitePresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.BaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 14/01/2017.
 */

public class InviteView extends Fragment implements BaseView, Invite.View {

    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private InvitePresenter presenter;
    private InviteRvAdapter adapter;
    private AlertDialog dialog;

    public static InviteView newInstance(int id, boolean organisation) {
        InviteView myFragment = new InviteView();
        Bundle args = new Bundle();

        args.putInt("page", R.string.view_name_organisation_management_member_invite);
        args.putInt("id", id);
        args.putBoolean("organisation", organisation);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model & Id Organisation
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     id = getArguments().getInt("id");
        boolean organisation = getArguments().getBoolean("organisation");

        // Init Presenter
        presenter = new InvitePresenter(this, user, id, organisation);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View    view = inflater.inflate(R.layout.fragment_organisation_management_send_invitation, container, false);
        ButterKnife.bind(this, view);

        RecyclerView recyclerView = ButterKnife.findById(view, R.id.recycler_view);
        adapter = new InviteRvAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getVolunteers();
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

    @Override
    public void update(List<User> volunteers) {
        adapter.update(volunteers);
    }

    @OnClick(R.id.btn_send)
    public void onClickSendBtn() {
        presenter.send();
    }

    public InviteRvAdapter getAdapter() {
        return adapter;
    }

}
