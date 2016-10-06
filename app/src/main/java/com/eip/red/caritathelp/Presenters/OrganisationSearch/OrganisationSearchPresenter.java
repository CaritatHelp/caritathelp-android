package com.eip.red.caritathelp.Presenters.OrganisationSearch;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Activities.Main.ViewPager.MyFirstPage;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.OrganisationSearch.OrganisationSearchView;

import java.util.List;

/**
 * Created by pierr on 25/02/2016.
 */

public class OrganisationSearchPresenter implements IOrganisationSearchPresenter, IOnOrganisationSearchFinishedListener {

    private OrganisationSearchView          view;
    private OrganisationSearchInteractor    interactor;

    public OrganisationSearchPresenter(OrganisationSearchView view, User user) {
        this.view = view;
        this.interactor = new OrganisationSearchInteractor(view.getContext(), user);
    }

    @Override
    public void getAllOrganisations() {
        view.showProgress();
        interactor.getAllOrganisations(this);
    }

    @Override
    public void goToOrganisationView(Organisation organisation) {
        if (organisation != null)
            Tools.replaceView(view, OrganisationView.newInstance(organisation.getId(), organisation.getName()), Animation.FADE_IN_OUT, false);
    }


    public void replaceView(Fragment fragment, int animation) {
        if (view.getParentFragment() == null)
            System.out.println("OUPSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");

        // Replace Fragment
        FragmentTransaction ft = view.getParentFragment().getChildFragmentManager().beginTransaction();

        // Set Animation
        switch (animation) {
            case Animation.SLIDE_LEFT_RIGHT:
//                ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out);
                break;
            case Animation.SLIDE_UP_DOWN:
//                ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
                break;
            case Animation.FLIP_LEFT_RIGHT:
//                ft.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.fade_in, R.animator.fade_out);//,0 R.animator.card_flip_left_in, R.animator.card_flip_left_out);
                break;
            case Animation.FADE_IN_OUT:
//                ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out);
                break;
        }

//        ft.add(R.id.fragment_container, fragment);
        // Replace Fragment
        ft.replace(R.id.fragment_container, fragment);

        // Save old fragment in the stack
        ft.addToBackStack(null);

        // Commit changes
        ft.commit();
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccess(List<Organisation> organisations) {
        view.updateListView(organisations);
        view.hideProgress();
    }
}
