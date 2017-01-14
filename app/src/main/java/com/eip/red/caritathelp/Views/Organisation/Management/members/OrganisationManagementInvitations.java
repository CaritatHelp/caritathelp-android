package com.eip.red.caritathelp.Views.Organisation.Management.members;

import com.eip.red.caritathelp.Models.Organisation.Member;

import java.util.List;

/**
 * Created by pierr on 12/01/2017.
 */

public interface OrganisationManagementInvitations {

    interface View {
        void showProgress();

        void hideProgress();

        void setDialog(String title, String msg);

        void updateInvitSent(List<Member> members);

        void updateInvitSent(int rvPosition);

        void updateInvitWaiting(List<Member> members);

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
