package com.eip.red.caritathelp.Views.SubMenu.MyOrganisations;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations.MyOrganisationsPresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

/**
 * Created by pierr on 16/11/2015.
 */
public class MyOrganisationsView extends Fragment implements IMyOrganisationsView, View.OnClickListener{

    private MyOrganisationsPresenter        presenter;

    private RecyclerView                    recyclerViewOwner;
    private RecyclerView                    recyclerViewMember;
    private MyOrganisationsRVAdapter        adapterOwner;
    private MyOrganisationsRVAdapter        adapterMember;

    private TextView            noOwnerOrgaTv;
    private TextView            noMemberOrgaTv;
    private SwipeRefreshLayout  swipeRefreshLayout;
    private ProgressBar         progressBar;
    private AlertDialog         dialog;

    public static Fragment newInstance(int userId, boolean mainUser) {
        MyOrganisationsView     fragment = new MyOrganisationsView();
        Bundle                  args = new Bundle();

        // Check if the user is the MAIN user (in order to change the title "My Organisations" by "Organisations").
        if (mainUser)
            args.putInt("page", R.string.view_name_submenu_my_organisations);
        else
            args.putInt("page", R.string.view_name_organisation);

        args.putInt("user id", userId);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Model
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     userId = getArguments().getInt("user id");

        // Init Presenter
        presenter = new MyOrganisationsPresenter(this, user, userId);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_submenu_my_organisations, container, false);

        // Init UI Element
        noOwnerOrgaTv = (TextView) view.findViewById(R.id.tv_no_owner_organisations);
        noMemberOrgaTv = (TextView) view.findViewById(R.id.tv_no_members_organisations);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        progressBar = (ProgressBar)  view.findViewById(R.id.progress_bar);

        // Set Create Button Visisbility
        if (!presenter.isMainUser()) {
            view.findViewById(R.id.btn_create).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.divider).setVisibility(View.INVISIBLE);
        }

        // Init RefreshLayout
        initSwipeRefreshLayout();

        // Init RV
        initRecyclerViews(view);

        // Init Button Listener
        view.findViewById(R.id.btn_create).setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init MyOrganisation Model by requesting the api
        presenter.getMyOrganisations(false);
    }

    private void initSwipeRefreshLayout() {
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);

        // Init Refresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Set MyOrganisations Model
                presenter.getMyOrganisations(true);
            }
        });
    }

    public void initRecyclerViews(View view) {
        // Init Recycler Views
        recyclerViewOwner = (RecyclerView) view.findViewById(R.id.my_organisations_owner_profile_recycler_view);
        recyclerViewMember = (RecyclerView) view.findViewById(R.id.my_organisations_member_profile_recycler_view);

        // Init Adapter
        adapterOwner = new MyOrganisationsRVAdapter(presenter);
        adapterMember = new MyOrganisationsRVAdapter(presenter);

        // Set Adapter
        recyclerViewOwner.setAdapter(adapterOwner);
        recyclerViewMember.setAdapter(adapterMember);

        // Init LayoutManager
        recyclerViewOwner.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerViewMember.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));

        // Set Options to enable toolbar display/hide
        recyclerViewOwner.setNestedScrollingEnabled(false);
        recyclerViewMember.setNestedScrollingEnabled(false);
        recyclerViewOwner.setHasFixedSize(false);
        recyclerViewMember.setHasFixedSize(false);

        // Init Divider (between items)
        // Convert dp to px
//        int spacing = Math.round(2 * getResources().getDisplayMetrics().density);
//        RecyclerView.ItemDecoration itemDecoration = new GridSpacingItemDecoration(3, spacing, true);
//        recyclerViewOwner.addItemDecoration(itemDecoration);
//        recyclerViewMember.addItemDecoration(itemDecoration);
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
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void updateRecyclerView(List<Organisation> myOrganisationsOwner, List<Organisation> myOrganisationsMember) {
        adapterOwner.update(myOrganisationsOwner);
        adapterMember.update(myOrganisationsMember);
    }

    public MyOrganisationsRVAdapter getAdapterOwner() {
        return adapterOwner;
    }

    public MyOrganisationsRVAdapter getAdapterMember() {
        return adapterMember;
    }

    public TextView getNoOwnerOrgaTv() {
        return noOwnerOrgaTv;
    }

    public TextView getNoMemberOrgaTv() {
        return noMemberOrgaTv;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
