package com.eip.red.caritathelp.Views.OrganisationSearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.OrganisationSearch.OrganisationSearchPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;

import java.util.List;
import java.util.Locale;

/**
 * Created by pierr on 23/02/2016.
 */

public class OrganisationSearchView extends Fragment implements IOrganisationSearchView {

    private OrganisationSearchPresenter presenter;

    private View            view;
    private ListView        listView;
    private ProgressBar     progressBar;
    private AlertDialog     dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model
        User user = ((MainActivity) getActivity()).getModelManager().getUser();

        // Init Presenter
        presenter = new OrganisationSearchPresenter(this, user);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_organisation_search, container, false);

        // Init SearchBar
//        initSearchBar();

        // Init UI Element
        progressBar = (ProgressBar) view.findViewById(R.id.organisation_search_progress_bar);

        // Init ListView & Listener & Adapter
        listView = (ListView) view.findViewById(R.id.organisations_search_list_view);
        listView.setAdapter(new OrganisationsSearchListViewAdapter(this));
        Tools.setListViewHeightBasedOnChildren(listView);
        initListViewListener();

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get All Organisations
        presenter.getAllOrganisations();
    }

    private void initListViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Go to organisation page
                presenter.goToOrganisationView((Organisation) parent.getItemAtPosition(position));

//                // Init Text Search Bar
//                searchBar.invalidate();
//                searchBar.getText().clear();
//                searchBar.setHint(R.string.organisations_search_bar);
            }
        });
    }

/*
    private void initSearchBar() {
        MySearchBar         searchBar = ((MainActivity) getActivity()).getMySearchBar();
        final EditText      searchText = searchBar.getSearchText();
        final ImageButton   cancelBtn = searchBar.getCancelBtn();

        // Show SearchBar
        searchBar.setVisibility(View.VISIBLE);

        // Show the SearchBar
        searchBar.show(R.string.search_bar_organisation);

        //Init SearchText listener & filter
        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (TextUtils.isEmpty(arg0)) {
                    // Hide Cancel Btn
                    cancelBtn.setVisibility(View.GONE);

                    // Flush Filter
//                    ((MyEventsRVAdapter) recyclerView.getAdapter()).flushFilter();
                }
                else {
                    // Show Cancel Btn
                    cancelBtn.setVisibility(View.VISIBLE);

                    // Filter text
                    String text = searchText.getText().toString().toLowerCase(Locale.getDefault());
                    ((OrganisationsSearchListViewAdapter) listView.getAdapter()).filter(text);
                }
            }
        });
    }
*/

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
    public void updateListView(List<Organisation> organisations) {
        ((OrganisationsSearchListViewAdapter) listView.getAdapter()).setOrganisations(organisations);
        ((OrganisationsSearchListViewAdapter) listView.getAdapter()).notifyDataSetChanged();
        Tools.setListViewHeightBasedOnChildren(listView);
    }
}
