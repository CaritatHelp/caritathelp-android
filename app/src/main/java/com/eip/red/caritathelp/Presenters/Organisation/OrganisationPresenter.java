package com.eip.red.caritathelp.Presenters.Organisation;

import android.view.View;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.OrganisationEventsView;
import com.eip.red.caritathelp.Views.Organisation.Informations.OrganisationInformationsView;
import com.eip.red.caritathelp.Views.Organisation.Management.OrganisationManagementView;
import com.eip.red.caritathelp.Views.Organisation.Members.OrganisationMembersView;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;

import java.util.List;

/**
 * Created by pierr on 11/03/2016.
 */

public class OrganisationPresenter implements IOrganisationPresenter, IOnOrganisationFinishedListener {

    private OrganisationView        view;
    private OrganisationInteractor  interactor;

    public OrganisationPresenter(OrganisationView view, String token, int id) {
        this.view = view;

        // Init Interactor
        interactor = new OrganisationInteractor(view.getActivity().getApplicationContext(), token, id);
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.btn_management:
                Tools.replaceView(view, OrganisationManagementView.newInstance(interactor.getOrganisationId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_membership_owner:
                view.showProgress();
                interactor.leave(this);
                break;
            case R.id.btn_membership_admin:
                view.showProgress();
                interactor.leave(this);
                break;
            case R.id.btn_membership_member:
                view.showProgress();
                interactor.leave(this);
                break;
            case R.id.btn_membership_none:
                view.showProgress();
                interactor.join(this);
                break;
            case R.id.btn_membership_confirm:
                view.showProgress();
                interactor.reply(this, "true");
                break;
            case R.id.btn_membership_remove:
                view.showProgress();
                interactor.reply(this, "false");
                break;
            case R.id.btn_follow:
                break;
            case R.id.btn_post:
                break;
            case R.id.btn_members:
                Tools.replaceView(view, OrganisationMembersView.newInstance(interactor.getOrganisationId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_events:
                Tools.replaceView(view, OrganisationEventsView.newInstance(interactor.getOrganisationId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_informations:
                Tools.replaceView(view, OrganisationInformationsView.newInstance(interactor.getOrganisationId()), Animation.FADE_IN_OUT, false);
                break;
        }

    }

    @Override
    public void getOrganisation() {
        view.showProgress();
        interactor.getOrganisation(this);
    }

    @Override
    public void getNews() {
        view.showProgress();
        interactor.getNews(this);
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetOrganisation(Organisation organisation) {
        // Set Profle Picture
        Network.loadImage(view.getContext(), view.getLogo(), Network.API_LOCATION_2 + organisation.getName(), R.drawable.profile_example);

        // Set Management Button Visibility
        view.setLogoPosition(organisation.getRights());

        // Set Membership Button Visibility
        switch (organisation.getRights()) {
            case Organisation.ORGANISATION_OWNER:
                view.getMembershipBtn(Organisation.ORGANISATION_OWNER).setVisibility(View.VISIBLE);
                break;
            case Organisation.ORGANISATION_ADMIN:
                view.getMembershipBtn(Organisation.ORGANISATION_OWNER).setVisibility(View.VISIBLE);
                break;
            case Organisation.ORGANISATION_MEMBER:
                view.getMembershipBtn(Organisation.ORGANISATION_MEMBER).setVisibility(View.VISIBLE);
                break;
            case Organisation.ORGANISATION_NONE:
                view.getMembershipBtn(Organisation.ORGANISATION_NONE).setVisibility(View.VISIBLE);
                break;
            case Organisation.ORGANISATION_INVITED:
                view.getMembershipBtn(Organisation.ORGANISATION_INVITED_CONFIRM).setVisibility(View.VISIBLE);
                view.getMembershipBtn(Organisation.ORGANISATION_INVITED_REMOVE).setVisibility(View.VISIBLE);
                break;
            case Organisation.ORGANISATION_WAITING:
                view.getMembershipBtn(Organisation.ORGANISATION_WAITING).setVisibility(View.VISIBLE);
                break;
        }

        // Set Progress Bar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessLeave() {
        // Set Membership Btn Visibility
        view.getMembershipBtn(Organisation.ORGANISATION_OWNER).setVisibility(View.GONE);
        view.getMembershipBtn(Organisation.ORGANISATION_ADMIN).setVisibility(View.GONE);
        view.getMembershipBtn(Organisation.ORGANISATION_MEMBER).setVisibility(View.GONE);
        view.getMembershipBtn(Organisation.ORGANISATION_NONE).setVisibility(View.VISIBLE);

        // Set Progress Bar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessJoin() {
        // Set Membership Btn Visibility
        view.getMembershipBtn(Organisation.ORGANISATION_NONE).setVisibility(View.GONE);
        view.getMembershipBtn(Organisation.ORGANISATION_WAITING).setVisibility(View.VISIBLE);

        // Set Progress Bar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessReply(String acceptance) {
        // Set Membership Btn Visibility
        view.getMembershipBtn(Organisation.ORGANISATION_INVITED_CONFIRM).setVisibility(View.GONE);
        view.getMembershipBtn(Organisation.ORGANISATION_INVITED_REMOVE).setVisibility(View.GONE);

        if (acceptance.equals("true"))
            view.getMembershipBtn(Organisation.ORGANISATION_MEMBER).setVisibility(View.VISIBLE);
        else
            view.getMembershipBtn(Organisation.ORGANISATION_NONE).setVisibility(View.VISIBLE);

        // Set Progress Bar Visibility
        view.hideProgress();
    }

    @Override
    public void onNewsRequestSuccess(List<News> newsList) {
//        view.updateRV(newsList);
        view.hideProgress();
    }

}
