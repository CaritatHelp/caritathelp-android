package com.eip.red.caritathelp.Views.Organisation.Events.Event.Informations;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Informations.OrganisationEventInformationsPresenter;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventInformationsView extends Fragment implements IOrganisationEventInformationsView {

    private OrganisationEventInformationsPresenter presenter;

    private TextView    dateBegin;
    private TextView    dateEnd;
    private TextView    location;
    private TextView    description;
    private ProgressBar progressBar;
    private AlertDialog dialog;


    public static OrganisationEventInformationsView newInstance(int eventId) {
        OrganisationEventInformationsView    myFragment = new OrganisationEventInformationsView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_organisation_informations);
        args.putInt("event id", eventId);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model & Id Organisation
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     eventId = getArguments().getInt("event id");

        // Init Presenter
        presenter = new OrganisationEventInformationsPresenter(this, user, eventId);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_organisation_event_informations, container, false);

        // Init UI Element
        dateBegin = (TextView) view.findViewById(R.id.date_begin);
        dateEnd = (TextView) view.findViewById(R.id.date_end);
        location = (TextView) view.findViewById(R.id.location);
        description = (TextView) view.findViewById(R.id.description);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init Event Model
        presenter.getEvent();
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
    public void setDialogError(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void setViewData(String dateBegin, String dateEnd, String location, String description) {
        if (dateBegin != null && !TextUtils.isEmpty(dateBegin)) {
            this.dateBegin.setText(dateBegin);
            this.dateBegin.setVisibility(View.VISIBLE);
        }
        else
            this.dateBegin.setVisibility(View.GONE);
        if (dateEnd != null && !TextUtils.isEmpty(dateEnd)) {
            this.dateEnd.setText(dateEnd);
            this.dateEnd.setVisibility(View.VISIBLE);
        }
        else
            this.dateEnd.setVisibility(View.GONE);

        if (location != null && !TextUtils.isEmpty(location)) {
            this.location.setText(location);
            this.location.setVisibility(View.VISIBLE);
        }
        else
            this.location.setVisibility(View.GONE);

        if (description != null && !TextUtils.isEmpty(description)) {
            this.description.setText(description);
            this.description.setVisibility(View.VISIBLE);
        }
        else
            this.description.setVisibility(View.GONE);
    }

}
