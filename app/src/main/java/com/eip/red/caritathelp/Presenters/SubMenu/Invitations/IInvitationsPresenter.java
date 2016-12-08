package com.eip.red.caritathelp.Presenters.SubMenu.Invitations;

import com.eip.red.caritathelp.Models.Friends.FriendInvitation;
import com.eip.red.caritathelp.Models.User.EventInvitation;
import com.eip.red.caritathelp.Models.User.OrganisationInvitation;

/**
 * Created by pierr on 04/07/2016.
 */

public interface IInvitationsPresenter {

    void getFriendsInvitations(boolean isSwipeRefresh);

    void getOrganisationsInvitations(boolean isSwipeRefresh);

    void getEventsInvitations(boolean isSwipeRefresh);

    void onClick(int viewId);

    void onClick(int viewId, FriendInvitation friendInvitation);

    void onClick(int viewId, OrganisationInvitation organisationInvitation);

    void onClick(int viewId, EventInvitation eventInvitation);
}
