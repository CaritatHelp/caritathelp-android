package com.eip.red.caritathelp.Views.Organisation.Events;

import com.eip.red.caritathelp.Models.Organisation.Event;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */
public interface IOrganisationEventsView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);
}
