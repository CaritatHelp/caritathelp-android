package com.eip.red.caritathelp.Presenters.SubMenu.Invitations;

import com.eip.red.caritathelp.Models.Friends.FriendInvitation;
import com.eip.red.caritathelp.Models.User.EventInvitation;
import com.eip.red.caritathelp.Models.User.OrganisationInvitation;

import java.util.List;

/**
 * Created by pierr on 04/07/2016.
 */

public interface IOnInvitationsFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessGetFriendsInvitations(List<FriendInvitation> friendInvitations);

    void onSuccessGetOrganisationsInvitations(List<OrganisationInvitation>  organisationInvitations);

    void onSuccessGetEventsInvitations(List<EventInvitation> eventInvitations);

    void onSuccessFriendsInvitationReply(FriendInvitation friendInvitation, String acceptance);

    void onSuccessOrganisationsInvitationReply(OrganisationInvitation organisationInvitation, String acceptance);

    void onSuccessEventsInvitationReply(EventInvitation eventInvitation, String acceptance);
}
