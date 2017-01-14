package com.eip.red.caritathelp.Views.SubMenu.MyOrganisations.OrganisationCreation;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations.OrganisationCreation.OrganisationCreationPresenter;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.ProfilePresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by pierr on 23/02/2016.
 */

public class OrganisationCreationView extends Fragment implements IOrganisationCreation, View.OnClickListener{

    private OrganisationCreationPresenter presenter;

    private CircularImageView   image;
    private EditText            name;
    private EditText            city;
    private EditText            description;

    private AlertDialog     dialog;
    private ProgressBar     progressBar;

    public static Fragment newInstance() {
        OrganisationCreationView    fragment = new OrganisationCreationView();
        Bundle                      args = new Bundle();

        args.putInt("page", R.string.view_name_submenu_my_organisations_creation);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model
        User user = ((MainActivity) getActivity()).getModelManager().getUser();

        // Init Presenter
        presenter = new OrganisationCreationPresenter(this, user);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_organisation_creation, container, false);

//        // Init SearchBar
//        ((MainActivity) getActivity()).getToolBar().getSearchBar().setVisibility(View.GONE);

        // Init UI Element
        image = (CircularImageView) view.findViewById(R.id.image);
        name = (EditText) view.findViewById(R.id.name);
        city = (EditText) view.findViewById(R.id.location);
        description = (EditText) view.findViewById(R.id.description);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Init Button Listener
        view.findViewById(R.id.btn_photo).setOnClickListener(this);
        view.findViewById(R.id.btn_create).setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ProfilePresenter.RESULT_LOAD_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadProfileImg(image, data, OrganisationCreationPresenter.RESULT_LOAD_IMAGE);
        else if (requestCode == ProfilePresenter.RESULT_CAPTURE_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadProfileImg(image, data, OrganisationCreationPresenter.RESULT_CAPTURE_IMAGE);
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
    public void setNameError(String error) {
        name.setError(error);
    }

    @Override
    public void setCityError(String error) {
        city.setError(error);
    }

    @Override
    public void setDescriptionError(String error) {
        description.setError(error);
    }

    @Override
    public void setDialogError(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void navigateToOrganisationView(String name) {
        // Display Dialog Box
        dialog.setTitle("Création réussie");
        dialog.setMessage("Bienvenue sur la page d'accueil de l'association " + name);
        dialog.show();
    }

    public EditText getName() {
        return name;
    }

    public EditText getCity() {
        return city;
    }

    public EditText getDescription() {
        return description;
    }
}
