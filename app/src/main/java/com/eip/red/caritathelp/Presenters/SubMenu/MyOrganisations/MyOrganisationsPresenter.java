package com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations;

import android.view.View;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
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

    public MyOrganisationsPresenter(MyOrganisationsView view, User user, int userId) {
        this.view = view;

        // Init Interactor
        interactor = new MyOrganisationsInteractor(view.getActivity().getBaseContext(), user, userId);
    }

    @Override
    public boolean isMainUser() {
        return (interactor.getMainUserId() == interactor.getUserId());
    }

    @Override
    public void onClick(int viewId) {
        if (viewId == R.id.btn_create)
            Tools.replaceView(view, OrganisationCreationView.newInstance(), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void getMyOrganisations(boolean isSwipeRefresh) {
        // Display the ProgressBar if it's not a SwipRefresh gesture
        if (!isSwipeRefresh)
            view.showProgress();

        interactor.getMyOrganisations(view.getProgressBar(), this, isSwipeRefresh);
    }

    @Override
    public void goToOrganisationView(Organisation organisation) {
        if (organisation != null)
            Tools.replaceView(view, OrganisationView.newInstance(organisation.getId(), organisation.getName()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialog(String title, String msg, boolean isSwipeRefresh) {
        // Set SwipeRefreshLayout or ProgressBar Visibility
        if (isSwipeRefresh)
            view.getSwipeRefreshLayout().setRefreshing(false);
        else
            view.hideProgress();

        // Set Dialog
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccess(List<Organisation> myOrganisationsOwner, List<Organisation> myOrganisationsMember, boolean isSwipeRefresh) {
        // Set Message if the user are no owner organisations or member organisations
        // Set RV data
        if (myOrganisationsOwner.size() != 0) {
            view.getNoOwnerOrgaTv().setVisibility(View.GONE);
            view.getAdapterOwner().update(myOrganisationsOwner);
        }
        else
            view.getNoOwnerOrgaTv().setVisibility(View.VISIBLE);

        // Set Error RV Member Message Visibility
        if (myOrganisationsMember.size() != 0) {
            view.getNoMemberOrgaTv().setVisibility(View.GONE);
            view.getAdapterMember().update(myOrganisationsMember);
        }
        else
            view.getNoMemberOrgaTv().setVisibility(View.VISIBLE);

        // Set SwipeRefreshLayout or ProgressBar Visibility
        if (isSwipeRefresh)
            view.getSwipeRefreshLayout().setRefreshing(false);
        else
            view.hideProgress();
    }

}
