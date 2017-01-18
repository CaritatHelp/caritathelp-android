package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.creation;

/**
 * Created by pierr on 18/03/2016.
 */

public interface IOnOrganisationEventCreationFinishedListener {

    void onTitleError(String error);

    void onPhotoError(String error);

    void onBeginDateError(String error);

    void onEndDateError(String error);

    void onLocationError(String error);

    void onDescriptionError(String error);

    void onDialogError(String title, String msg);

    void onSuccess(int eventId, String eventTitle);

}
