package com.eip.red.caritathelp.Views.Organisation.Events.Event.emergency;

import com.eip.red.caritathelp.Models.User.User;

import java.util.List;

/**
 * Created by pierr on 16/01/2017.
 */

public interface EmergencyInvitations {

    interface View {

        void update(List<User> volunteers);

    }

    interface Presenter {
        void getVolunteers();

        void goToProfileView(int id);
    }
}
