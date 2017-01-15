package com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Guest;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests.OrganisationEventGuestsPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventRVAdapter;

import java.util.List;

/**
 * Created by pierr on 15/04/2016.
 */

public class OrganisationEventGuestsView extends Fragment implements IOrganisationEventGuestsView {

    private OrganisationEventGuestsPresenter presenter;

    private RecyclerView    recyclerView;
    private ProgressBar     progressBar;
    private AlertDialog     dialog;

    public static OrganisationEventGuestsView newInstance(int idEvent, boolean owner) {
        OrganisationEventGuestsView myFragment = new OrganisationEventGuestsView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_organisation_event_guests);
        args.putInt("event id", idEvent);
        args.putBoolean("owner", owner);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model & Id Organisation
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     eventId = getArguments().getInt("event id");
        boolean owner = getArguments().getBoolean("owner");

        // Init Presenter
        presenter = new OrganisationEventGuestsPresenter(this, user, eventId, owner);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organisation_event_guests, container, false);

        // Init UI Element
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Init RecyclerView
        initRecyclerView(view);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init Guests Model
        presenter.getGuests();
    }

    private void initRecyclerView(View view) {
        // Init RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new OrganisationEventGuestsRVAdapter(presenter));

        // Init LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set Options to enable toolbar display/hide
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        // Init Divider (between items)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
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
    public void updateRecyclerView(List<Guest> guests) {
        ((OrganisationEventGuestsRVAdapter) recyclerView.getAdapter()).update(guests);
    }

    @Override
    public void upgrade(int position) {
        ((OrganisationEventGuestsRVAdapter) recyclerView.getAdapter()).upgrade(position);
    }

    @Override
    public void kick(int position) {
        ((OrganisationEventGuestsRVAdapter) recyclerView.getAdapter()).kick(position);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
