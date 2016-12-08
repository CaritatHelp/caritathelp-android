package com.eip.red.caritathelp.Views.Organisation.Informations;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Informations.OrganisationInformationsPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by pierr on 07/05/2016.
 */

public class OrganisationInformationsView extends Fragment implements IOrganisationInformationsView {

    private OrganisationInformationsPresenter   presenter;

    private CircularImageView   image;
    private TextView            name;
    private TextView            location_members;
    private TextView            description;
    private ProgressBar         progressBar;
    private AlertDialog         dialog;

    public static OrganisationInformationsView newInstance(int id) {
        OrganisationInformationsView    myFragment = new OrganisationInformationsView();

        Bundle args = new Bundle();

        args.putInt("page", R.string.view_name_organisation_informations);
        args.putInt("id", id);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User & Organisation Model
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     id = getArguments().getInt("id");

        // Init Presenter
        presenter = new OrganisationInformationsPresenter(this, user, id);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organisation_informations, container, false);

        // Init UI Element
        image = (CircularImageView) view.findViewById(R.id.logo);
        name = (TextView) view.findViewById(R.id.name);
        location_members = (TextView) view.findViewById(R.id.location_and_members);
        description = (TextView) view.findViewById(R.id.description);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // BringToFront the logo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            view.findViewById(R.id.logo).bringToFront();
            view.findViewById(R.id.logo).invalidate();
        }
        else
            view.findViewById(R.id.logo).setElevation(4.5f);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Get Organisation Informations Model
        presenter.getOrganisationInformations(image);
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

    public TextView getName() {
        return name;
    }

    public TextView getLocation_members() {
        return location_members;
    }

    public TextView getDescription() {
        return description;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
