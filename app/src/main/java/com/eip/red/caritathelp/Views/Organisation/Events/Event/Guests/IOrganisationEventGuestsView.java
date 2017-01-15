package com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests;

import com.eip.red.caritathelp.Models.Organisation.Guest;

import java.util.List;

/**
 * Created by pierr on 15/04/2016.
 */

public interface IOrganisationEventGuestsView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);

    void updateRecyclerView(List<Guest> guests);

    void upgrade(int position);

    void kick(int position);
}
