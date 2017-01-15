package com.eip.red.caritathelp.Presenters.SubMenu;

import android.widget.ImageView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.SubMenu.AccountSettings.AccountSettingsView;
import com.eip.red.caritathelp.Views.SubMenu.MyFriends.MyFriendsView;
import com.eip.red.caritathelp.Views.SubMenu.Invitations.InvitationsView;
import com.eip.red.caritathelp.Views.SubMenu.MyEvents.MyEventsView;
import com.eip.red.caritathelp.Views.SubMenu.MyOrganisations.MyOrganisationsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.eip.red.caritathelp.Views.SubMenu.SubMenuView;

/**
 * Created by pierr on 20/06/2016.
 */

public class SubMenuPresenter implements ISubMenuPresenter {

    private SubMenuView         view;
    private SubMenuInteractor   interactor;

    public SubMenuPresenter(SubMenuView view, User user) {
        this.view = view;
        interactor = new SubMenuInteractor(view.getContext(), user);
    }

    @Override
    public void initProfileImg(ImageView imageView) {
        interactor.loadProfileImg(imageView);
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.submenu_my_profile:
                Tools.replaceView(view, ProfileView.newInstance(interactor.getUserId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.submenu_my_organisations:
                Tools.replaceView(view, MyOrganisationsView.newInstance(interactor.getUserId(), true), Animation.FADE_IN_OUT, false);
                break;
            case R.id.submenu_my_events:
                Tools.replaceView(view, MyEventsView.newInstance(interactor.getUserId(), true), Animation.FADE_IN_OUT, false);
                break;
            case R.id.submenu_friends:
                Tools.replaceView(view, MyFriendsView.newInstance(interactor.getUserId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.submenu_invitations:
                Tools.replaceView(view, InvitationsView.newInstance(), Animation.FADE_IN_OUT, false);
                break;
            case R.id.submenu_account_settings:
                Tools.replaceView(view, AccountSettingsView.newInstance(), Animation.FADE_IN_OUT, false);
                break;
            case R.id.submenu_logout:
                ((MainActivity) view.getActivity()).logout();
                break;
        }
    }

}
