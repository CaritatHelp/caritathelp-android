package com.eip.red.caritathelp.Presenters.Organisation.Members;

import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Views.Organisation.Members.OrganisationMembersView;

import java.util.List;

/**
 * Created by pierr on 11/03/2016.
 */

public class OrganisationMembersPresenter implements IOrganisationMembersPresenter, IOnOrganisationMembersFinishedListener {

    private OrganisationMembersView         view;
    private OrganisationMembersInteractor   interactor;

    public OrganisationMembersPresenter(OrganisationMembersView view, User user, int organisationId) {
        this.view = view;
        interactor = new OrganisationMembersInteractor(view.getActivity().getApplicationContext(), user, organisationId);
    }

    @Override
    public void getMembers() {
        view.showProgress();
        interactor.getMembers(this);
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccess(List<Member> members) {
        view.updateListView(members);
        view.hideProgress();
    }
}
