package com.eip.red.caritathelp.Views.Organisation.Members;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Members.OrganisationMembersPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 25/02/2016.
 */

public class OrganisationMembersView extends Fragment implements IOrganisationMembersView {

    private OrganisationMembersPresenter    presenter;

    private User        user;

    private ListView    listView;
    private ProgressBar progressBar;
    private AlertDialog dialog;

    public static OrganisationMembersView newInstance(int idOrganisation, boolean isOwner) {
        OrganisationMembersView    myFragment = new OrganisationMembersView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_organisation_members);
        args.putInt("organisation id", idOrganisation);
        args.putBoolean("owner", isOwner);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = ((MainActivity) getActivity()).getModelManager().getUser();

        // Get User Model & Id Organisation
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     organisationId = getArguments().getInt("organisation id");
        boolean owner = getArguments().getBoolean("owner");

        // Init Presenter
        presenter = new OrganisationMembersPresenter(this, user, organisationId, owner);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_organisation_members, container, false);

        // Init UI Element
        progressBar = (ProgressBar) view.findViewById(R.id.organisation_members_progress_bar);

        // Init ListView & Listener & Adapter
        listView = (ListView)view.findViewById(R.id.organisation_members_list_view);
        listView.setAdapter(new OrganisationMembersListViewAdapter(this, presenter));
        initListViewListener();

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init Members Model
        presenter.getMembers();
    }

    private void initListViewListener() {
        final OrganisationMembersView frag = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int userId =  ((Member) parent.getItemAtPosition(position)).getId();
                Tools.replaceView(frag, ProfileView.newInstance(userId), Animation.FADE_IN_OUT, false);
            }
        });
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

    @Override
    public void updateListView(List<Member> members) {
        ((OrganisationMembersListViewAdapter) listView.getAdapter()).update(members);
    }

    @Override
    public void upgrade(int position) {
        ((OrganisationMembersListViewAdapter) listView.getAdapter()).upgrade(position);
    }

    @Override
    public void kick(int position) {
        ((OrganisationMembersListViewAdapter) listView.getAdapter()).kick(position);
    }
}
