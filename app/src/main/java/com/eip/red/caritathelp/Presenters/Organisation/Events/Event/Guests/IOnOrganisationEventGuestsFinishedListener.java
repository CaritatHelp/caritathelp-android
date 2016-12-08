package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Guests;

import com.eip.red.caritathelp.Models.Organisation.Guest;

import java.util.List;

/**
 * Created by pierr on 15/04/2016.
 */

public interface IOnOrganisationEventGuestsFinishedListener {

    void onDialog(String title, String msg);

    void onSuccess(List<Guest> guests);
}
