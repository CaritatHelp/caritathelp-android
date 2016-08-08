package com.eip.red.caritathelp.Views.SubMenu.Profile.Friends;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.Friends.FriendsPresenter;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 13/07/2016.
 */

public class FriendsView extends Fragment implements IFriendsView {

    private FriendsPresenter    presenter;

    private FriendsRVAdapter    friendsRVAdapter;
    private SwipeRefreshLayout  swipeRefreshLayout;
    private ProgressBar         progressBar;
    private AlertDialog         dialog;

    public static FriendsView newInstance(int profileid) {
        FriendsView    myFragment = new FriendsView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_submenu_friends);
        args.putInt("profile id", profileid);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     profileId = getArguments().getInt("profile id");

        // Init Presenter
        presenter = new FriendsPresenter(this, profileId, user.getToken());

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submenu_profile_friends, container, false);

        // Init UI element
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Init RefreshLayout
        initSwipeRefreshLayout();

        // Init Recycler View
        initRecyclerView(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Get Friends Model
        presenter.getFriends(false);
    }

    private void initSwipeRefreshLayout() {
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);

        // Init Refresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Get Friends Model
                presenter.getFriends(true);
            }
        });
    }

    private void initRecyclerView(View view) {
        // Init Recycler Views
        RecyclerView friendsRV = (RecyclerView) view.findViewById(R.id.recycler_view);

        // Init & Set Adapter
        friendsRVAdapter = new FriendsRVAdapter(presenter);
        friendsRV.setAdapter(friendsRVAdapter);

        // Init LayoutManagers
        friendsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Set Options to enable toolbar display/hide
        friendsRV.setNestedScrollingEnabled(true);
        friendsRV.setHasFixedSize(false);

        // Init Dividers (between items)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        friendsRV.addItemDecoration(itemDecoration);
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

    public FriendsRVAdapter getFriendsRVAdapter() {
        return friendsRVAdapter;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}
