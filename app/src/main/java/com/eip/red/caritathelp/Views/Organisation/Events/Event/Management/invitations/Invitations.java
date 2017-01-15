package com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.invitations;

import com.eip.red.caritathelp.Models.Organisation.Guest;

import java.util.List;

/**
 * Created by pierr on 15/01/2017.
 */

public interface Invitations {

    interface View {
        void updateInvitSent(List<Guest> guests);

        void updateInvitSent(int rvPosition);

        void updateInvitWaiting(List<Guest> guests);

        void updateInvitWaiting(int rvPosition);
    }

    interface Presenter {
        void getInvitationsSent();

        void getInvitationsWaiting();

        void join(int id, boolean acceptance, int rvPosition);

        void uninvite(int id, int rvPosition);

        void goToSendInvitationView();

        void goToProfileView(int id);
    }
}
