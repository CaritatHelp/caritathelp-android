package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management;

import com.eip.red.caritathelp.Models.Organisation.Event;

/**
 * Created by pierr on 14/04/2016.
 */

public interface IOnOrganisationEventManagementFinishedListener {

    void onTitleError(String error);

    void onPhotoError(String error);

    void onBeginDateError(String error);

    void onEndDateError(String error);

    void onLocationError(String error);

    void onDescriptionError(String error);

    void onDialog(String title, String msg);

    void onSuccessGetEvent(Event event);

    void onSuccessSaveEventModifications(Event event);
}
