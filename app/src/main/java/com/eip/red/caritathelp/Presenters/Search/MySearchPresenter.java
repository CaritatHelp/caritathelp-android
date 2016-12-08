package com.eip.red.caritathelp.Presenters.Search;

import android.content.Context;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Search.Search;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.Search.MySearchView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import java.util.List;

/**
 * Created by pierr on 21/04/2016.
 */
public class MySearchPresenter implements IMySearchPresenter, IOnMySearchFinishedListener {

    private MySearchView        view;
    private MySearchInteractor  interactor;

    public MySearchPresenter(Context context, MySearchView view, User user) {
        this.view = view;
        interactor = new MySearchInteractor(context, user);
    }

    @Override
    public void onClick(int viewId, Search search) {
        String  resultType = search.getResult_type();

        switch (viewId) {
            case R.id.image:
                // Redirect Volunteer Profile Page
                view.getActivity().onBackPressed();

                // Go to the right page
                switch (resultType) {
                    case Search.RESULT_TYPE_VOLUNTEER:
                        Tools.replaceView(view.getActivity().getCurrentFragment(), ProfileView.newInstance(search.getId()), Animation.FADE_IN_OUT, false);
                        break;
                    case Search.RESULT_TYPE_ASSOC:
                        Tools.replaceView(view.getActivity().getCurrentFragment(), OrganisationView.newInstance(search.getId(), search.getName()), Animation.FADE_IN_OUT, false);
                        break;
                    case Search.RESULT_TYPE_EVENT:
                        Tools.replaceView(view.getActivity().getCurrentFragment(), OrganisationEventView.newInstance(search.getId(), search.getName()), Animation.FADE_IN_OUT, false);
                        break;
                }
                break;
            case R.id.name:
                // Redirect Volunteer Profile Page
                view.getActivity().onBackPressed();

                // Go to the right page
                switch (resultType) {
                    case Search.RESULT_TYPE_VOLUNTEER:
                        Tools.replaceView(view.getActivity().getCurrentFragment(), ProfileView.newInstance(search.getId()), Animation.FADE_IN_OUT, false);
                        break;
                    case Search.RESULT_TYPE_ASSOC:
                        Tools.replaceView(view.getActivity().getCurrentFragment(), OrganisationView.newInstance(search.getId(), search.getName()), Animation.FADE_IN_OUT, false);
                        break;
                    case Search.RESULT_TYPE_EVENT:
                        Tools.replaceView(view.getActivity().getCurrentFragment(), OrganisationEventView.newInstance(search.getId(), search.getName()), Animation.FADE_IN_OUT, false);
                        break;
                }
                break;
            case R.id.btn_add:
                switch (resultType) {
                    case Search.RESULT_TYPE_VOLUNTEER:
                        // Set ProgressBar Visibility
                        view.showProgress();

                        // Add Friend Request
                        interactor.addFriend(search, this);
                        break;
                    case Search.RESULT_TYPE_ASSOC:
                        break;
                    case Search.RESULT_TYPE_EVENT:
                        break;
                }
                break;
        }
    }

    @Override
    public void search(String query) {
        view.showProgress();
        interactor.search(query, view.getProgressBar(), this);
    }

    @Override
    public boolean isUser(int volunteerId) {
        return (volunteerId == interactor.getUserId());
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessSearch(List<Search> searches) {
        // Set RecyclerView
        view.getRvAdapter().update(searches);

        // Set ProgressBar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessAdd(Search search) {
        // Set Result Research Msg
        search.setResult("Invitation envoyée");

        // Update Recycler View
        view.getRvAdapter().notifyDataSetChanged();

        // Set ProgressBar Visibility
        view.hideProgress();
    }
}
