package com.eip.red.caritathelp.Views.Organisation.Events.Event.creation;

/**
 * Created by pierr on 18/03/2016.
 */

public interface IOrganisationEventCreationView {

    void showProgress();

    void hideProgress();

    void setTitleError(String error);

    void setPhotoError(String error);

    void setBeginDateError(String error);

    void setEndDateError(String error);

    void setLocationError(String error);

    void setDescriptionError(String error);

    void setDialogError(String title, String msg);
}
