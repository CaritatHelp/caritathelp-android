package com.eip.red.caritathelp.Views.SubMenu.MyFriends;

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
import com.eip.red.caritathelp.Presenters.SubMenu.MyFriends.MyFriendsPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 19/04/2016.
 */

public class MyFriendsView extends Fragment implements IMyFriendView, View.OnClickListener {

    private MyFriendsPresenter presenter;

    private RecyclerView        friendsRV;
    private RecyclerView        invitationsRV;
    private RecyclerView        sentRV;

    private MyFriendsRVAdapter      friendsRVA;
    private InvitationsRVAdapter    invitationsRVA;
    private SentRVAdapter           sentRVA;

    private List<TextView>  tabs;
    private int             tabSelected = TAB_FRIEND;
    public static final int TAB_FRIEND = 0;
    public static final int TAB_INVITATIONS = 1;
    public static final int TAB_SENT = 2;

    private SwipeRefreshLayout  swipeRefreshLayout;
    private ProgressBar         progressBar;
    private AlertDialog         dialog;

    public static MyFriendsView newInstance(int userId) {
        MyFriendsView myFragment = new MyFriendsView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_submenu_friends);
        args.putInt("user id", userId);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model
        User    mainUser = ((MainActivity) getActivity()).getModelManager().getUser();
        int     userId = getArguments().getInt("user id");

        // Init Presenter
        presenter = new MyFriendsPresenter(this, mainUser, userId);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submenu_my_friends, container, false);

        // Init UI Element
        tabs = new ArrayList<>();
        tabs.add((TextView) view.findViewById(R.id.my_friends));
        tabs.add((TextView) view.findViewById(R.id.invitations));
        tabs.add((TextView) view.findViewById(R.id.sent));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        // Init RefreshLayout
        initSwipeRefreshLayout();

        // Init RecyclerView
        initRecyclerView(view);

        // Init Listener
        view.findViewById(R.id.btn_add).setOnClickListener(this);
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

        // Init Friends Model
        presenter.getMyFriends(false);
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
                    case TAB_FRIEND :
                        presenter.getMyFriends(true);
                        break;
                    case TAB_INVITATIONS:
                        presenter.getInvitations();
                        break;
                    case TAB_SENT:
                        presenter.getSent();
                        break;
                }
            }
        });
    }

    private void initRecyclerView(View view) {
        // Init Recycler Views
        friendsRV = (RecyclerView) view.findViewById(R.id.recycler_view_my_friends);
        invitationsRV = (RecyclerView) view.findViewById(R.id.recycler_view_invitations);
        sentRV = (RecyclerView) view.findViewById(R.id.recycler_view_sent);

        // Init Adapters
        friendsRVA = new MyFriendsRVAdapter(presenter);
        invitationsRVA = new InvitationsRVAdapter(presenter);
        sentRVA = new SentRVAdapter(presenter);

        // Set Adapters
        friendsRV.setAdapter(friendsRVA);
        invitationsRV.setAdapter(invitationsRVA);
        sentRV.setAdapter(sentRVA);

        // Init LayoutManagers
        friendsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        invitationsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sentRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Set Options to enable toolbar display/hide
        friendsRV.setNestedScrollingEnabled(false);
        friendsRV.setHasFixedSize(false);
        invitationsRV.setNestedScrollingEnabled(false);
        invitationsRV.setHasFixedSize(false);
        sentRV.setNestedScrollingEnabled(false);
        sentRV.setHasFixedSize(false);

        // Init Dividers (between items)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        friendsRV.addItemDecoration(itemDecoration);
        invitationsRV.addItemDecoration(itemDecoration);
        sentRV.addItemDecoration(itemDecoration);
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

    public RecyclerView getFriendsRV() {
        return friendsRV;
    }

    public RecyclerView getInvitationsRV() {
        return invitationsRV;
    }

    public RecyclerView getSentRV() {
        return sentRV;
    }

    public MyFriendsRVAdapter getFriendsRVA() {
        return friendsRVA;
    }

    public InvitationsRVAdapter getInvitationsRVA() {
        return invitationsRVA;
    }

    public SentRVAdapter getSentRVA() {
        return sentRVA;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}
