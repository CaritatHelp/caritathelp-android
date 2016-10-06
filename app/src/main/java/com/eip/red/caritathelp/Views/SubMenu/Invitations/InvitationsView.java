package com.eip.red.caritathelp.Views.SubMenu.Invitations;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.SubMenu.Invitations.InvitationsPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 04/07/2016.
 */

public class InvitationsView extends Fragment implements IInvitationView, View.OnClickListener {

    private InvitationsPresenter    presenter;

    private RecyclerView    friendsInvitationsRV;
    private RecyclerView    organisationsInvitationsRV;
    private RecyclerView    eventsInvitationsRV;

    private FriendsInvitationsRVAdapter         friendsInvitationsRVA;
    private OrganisationsInvitationsRVAdapter   organisationsInvitationsRVA;
    private EventsInvitationsRVAdapter          eventsInvitationsRVA;

    private List<TextView>  tabs;
    private int             tabSelected = TAB_FRIENDS;
    public static final int TAB_FRIENDS = 0;
    public static final int TAB_ORGANISATIONS = 1;
    public static final int TAB_EVENTS = 2;

    private SwipeRefreshLayout  swipeRefreshLayout;
    private ProgressBar         progressBar;
    private AlertDialog         dialog;

    public static InvitationsView newInstance() {
        InvitationsView    myFragment = new InvitationsView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_submenu_invitations);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model
        User user = ((MainActivity) getActivity()).getModelManager().getUser();

        // Init Presenter
        presenter = new InvitationsPresenter(this, user);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submenu_invitations, container, false);

        // Init UI Element
        tabs = new ArrayList<>();
        tabs.add((TextView) view.findViewById(R.id.friends));
        tabs.add((TextView) view.findViewById(R.id.organisations));
        tabs.add((TextView) view.findViewById(R.id.events));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        // Init RefreshLayout
        initSwipeRefreshLayout();

        // Init RecyclerView
        initRecyclerView(view);

        // Init Listener
        for (TextView textView : tabs) {
            textView.setOnClickListener(this);
        }

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init FriendsInvitations Model
        presenter.getFriendsInvitations(false);
    }

    private void initSwipeRefreshLayout() {
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);

        // Init Refresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (tabSelected) {
                    case TAB_FRIENDS :
                        presenter.getFriendsInvitations(true);
                        break;
                    case TAB_ORGANISATIONS:
                        presenter.getOrganisationsInvitations(true);
                        break;
                    case TAB_EVENTS:
                        presenter.getEventsInvitations(true);
                        break;
                }
            }
        });
    }

    private void initRecyclerView(View view) {
        // Init Recycler Views
        friendsInvitationsRV = (RecyclerView) view.findViewById(R.id.recycler_view_friends_invitations);
        organisationsInvitationsRV = (RecyclerView) view.findViewById(R.id.recycler_view_organisations_invitations);
        eventsInvitationsRV = (RecyclerView) view.findViewById(R.id.recycler_view_events_invitations);

        // Init Adapters
        friendsInvitationsRVA = new FriendsInvitationsRVAdapter(presenter);
        organisationsInvitationsRVA = new OrganisationsInvitationsRVAdapter(presenter);
        eventsInvitationsRVA = new EventsInvitationsRVAdapter(presenter);

        // Set Adapters
        friendsInvitationsRV.setAdapter(friendsInvitationsRVA);
        organisationsInvitationsRV.setAdapter(organisationsInvitationsRVA);
        eventsInvitationsRV.setAdapter(eventsInvitationsRVA);

        // Init LayoutManagers
        friendsInvitationsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        organisationsInvitationsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventsInvitationsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Set Options to enable toolbar display/hide
        friendsInvitationsRV.setNestedScrollingEnabled(false);
        friendsInvitationsRV.setHasFixedSize(false);
        organisationsInvitationsRV.setNestedScrollingEnabled(false);
        organisationsInvitationsRV.setHasFixedSize(false);
        eventsInvitationsRV.setNestedScrollingEnabled(false);
        eventsInvitationsRV.setHasFixedSize(false);

        // Init Dividers (between items)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        friendsInvitationsRV.addItemDecoration(itemDecoration);
        organisationsInvitationsRV.addItemDecoration(itemDecoration);
        eventsInvitationsRV.addItemDecoration(itemDecoration);
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    @Override
    public void setTabsTypeface(int tab) {
        tabSelected = tab;
        for (int i = 0; i < tabs.size(); i++) {
            if (tab == i)
                tabs.get(i).setTypeface(Typeface.DEFAULT_BOLD);
            else
                tabs.get(i).setTypeface(Typeface.DEFAULT);
        }
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

    public InvitationsPresenter getPresenter() {
        return presenter;
    }

    public RecyclerView getFriendsInvitationsRV() {
        return friendsInvitationsRV;
    }

    public RecyclerView getOrganisationsInvitationsRV() {
        return organisationsInvitationsRV;
    }

    public RecyclerView getEventsInvitationsRV() {
        return eventsInvitationsRV;
    }

    public FriendsInvitationsRVAdapter getFriendsInvitationsRVA() {
        return friendsInvitationsRVA;
    }

    public OrganisationsInvitationsRVAdapter getOrganisationsInvitationsRVA() {
        return organisationsInvitationsRVA;
    }

    public EventsInvitationsRVAdapter getEventsInvitationsRVA() {
        return eventsInvitationsRVA;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}
