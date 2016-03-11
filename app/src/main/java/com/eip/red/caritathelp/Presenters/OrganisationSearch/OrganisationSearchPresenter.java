package com.eip.red.caritathelp.Presenters.OrganisationSearch;

import com.eip.red.caritathelp.MainActivity.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.OrganisationSearch.OrganisationSearchView;

import java.util.List;

/**
 * Created by pierr on 25/02/2016.
 */

public class OrganisationSearchPresenter implements IOrganisationSearchPresenter, IOnOrganisationSearchFinishedListener {

    private OrganisationSearchView          view;
    private OrganisationSearchInteractor    interactor;

    public OrganisationSearchPresenter(OrganisationSearchView view, Network network) {
        this.view = view;
        this.interactor = new OrganisationSearchInteractor(view.getContext(), network);
    }

    @Override
    public void getAllOrganisations() {
        view.showProgress();
        interactor.getAllOrganisations(this);
    }

    @Override
    public void goToOrganisationView(String name) {
        Organisation    organisation = interactor.getOrganisation(name);

        if (organisation != null)
            ((MainActivity) view.getActivity()).replaceView(OrganisationView.newInstance(organisation), true);
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccess(List<String> organisationsNames) {
        view.hideProgress();
        view.updateListView(organisationsNames);
    }
}
