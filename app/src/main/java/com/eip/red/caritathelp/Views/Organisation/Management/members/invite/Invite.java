package com.eip.red.caritathelp.Views.Organisation.Management.members.invite;

import com.eip.red.caritathelp.Models.User.User;

import java.util.List;

/**
 * Created by pierr on 14/01/2017.
 */

public interface Invite {

    interface View {

        void update(List<User> volunteers);
    }

    interface Presenter {

        void getVolunteers();

        void send();

        void goToProfileView(int id);
    }

}
