package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Informations;

import com.eip.red.caritathelp.Models.Organisation.Event;

/**
 * Created by pierr on 18/03/2016.
 */
public interface IOnOrganisationEventInformationsFinishedListener {

    void onDialogError(String title, String msg);

    void onSuccess(Event event);
}
