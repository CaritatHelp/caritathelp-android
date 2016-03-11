package com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations;

import com.eip.red.caritathelp.MainActivity.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation;
import com.eip.red.caritathelp.Models.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.SubMenu.MyOrganisations.MyOrganisationsView;
import com.eip.red.caritathelp.Views.SubMenu.MyOrganisations.OrganisationCreation.OrganisationCreationView;

import java.util.List;

/**
 * Created by pierr on 24/02/2016.
 */
public class MyOrganisationsPresenter implements IMyOrganisationsPresenter, IOnMyOrganisationsFinishedListener {

    private MyOrganisationsView         view;
    private MyOrganisationsInteractor   interactor;

    private OrganisationCreationView    organisationCreationView;
    private OrganisationView            organisationView;

    public MyOrganisationsPresenter(MyOrganisationsView view, User user, Network network) {
        this.view = view;

        // Init Interactor
        interactor = new MyOrganisationsInteractor(view.getContext(), user, network);

        // Init View
        organisationCreationView = new OrganisationCreationView();
        organisationView = new OrganisationView();
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.top_bar_my_organisations_btn_add_orga:
                // Page Change
                ((MainActivity) view.getActivity()).replaceView(organisationCreationView, true);
                break;
            case R.id.top_bar_my_organisations_return:
                ((MainActivity) view.getActivity()).goToPreviousPage();
                break;
        }
    }

    @Override
    public void getMyOrganisations() {
        view.showProgress();
        interactor.getMyOrganisations(this);
    }

    @Override
    public void goToOrganisationView(String name) {
        Organisation organisation = interactor.getOrganisation(name);

        if (organisation != null)
            ((MainActivity) view.getActivity()).replaceView(OrganisationView.newInstance(organisation), true);
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccess(List<String> myOrganisationsNames) {
        view.hideProgress();
        view.updateListView(myOrganisationsNames);
    }
}
