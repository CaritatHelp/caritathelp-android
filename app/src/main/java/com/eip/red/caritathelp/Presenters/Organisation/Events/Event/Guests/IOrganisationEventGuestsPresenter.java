package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests;

/**
 * Created by pierr on 15/04/2016.
 */

public interface IOrganisationEventGuestsPresenter {

    void getGuests();

    void upgrade(int id, String rights, int rvPosition);

    void kick(int id, int rvPosition);

    void goToProfileView(int id);
}
