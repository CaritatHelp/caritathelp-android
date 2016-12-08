package com.eip.red.caritathelp.Views.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.Search.MySearchPresenter;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 19/04/2016.
 */

public class MySearchView implements IMySearchView {

    private MainActivity        activity;
    private MySearchPresenter   presenter;
    private SearchView          searchView;
    private MenuItem            item;

    private RecyclerView        recyclerView;
    private MySearchRVAdapter   rvAdapter;
    private AlertDialog         dialog;
    private ProgressBar         progressBar;

    public MySearchView(MainActivity activity, Menu menu) {
        this.activity = activity;

        // Init Presenter
        presenter = new MySearchPresenter(activity, this, activity.getModelManager().getUser());

        // Init UI
        progressBar = (ProgressBar) activity.findViewById(R.id.progress_bar);

        // Init Dialog
        dialog = new AlertDialog.Builder(activity)
                .setCancelable(true)
                .create();

        // Init Menu & Listener
        initMenu(menu);

        // Init Recycler View
        initRV();
    }

    private void initMenu(Menu menu) {
        item = menu.findItem(R.id.action_search);

        // Init Search View
        searchView = (SearchView) MenuItemCompat.getActionView(item);

        // Init Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText))
                    presenter.search(newText);
                else
                    rvAdapter.update(null);
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Show RV
                recyclerView.bringToFront();
                recyclerView.setVisibility(View.VISIBLE);

                // Hide NavigationBottomBar
                activity.getMyNavigationBottomBar().getBar().setVisibility(View.GONE);

                // Show Keyboard
                ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                return true; // Return true to expand action view
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Hide RV
                recyclerView.setVisibility(View.GONE);

                // Show NavigationBottomBar
                activity.getMyNavigationBottomBar().getBar().setVisibility(View.VISIBLE);
                return true; // Return true to collapse action view
            }
        });

    }

    private void initRV() {
        // Init recycler view
        recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view);

        // Set Adapter
        rvAdapter = new MySearchRVAdapter(presenter);
        recyclerView.setAdapter(rvAdapter);

        // Init LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        // Set Options to enable toolbar display/hide
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        // Init Divider (between items)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void show() {
        MenuItemCompat.expandActionView(item);
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
    public boolean isIconified() {
        return searchView.isIconified();
    }

    @Override
    public void setIconified(boolean iconify) {
        searchView.setIconified(iconify);
        MenuItemCompat.collapseActionView(item);
    }

    public MySearchRVAdapter getRvAdapter() {
        return rvAdapter;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public MainActivity getActivity() {
        return activity;
    }
}
