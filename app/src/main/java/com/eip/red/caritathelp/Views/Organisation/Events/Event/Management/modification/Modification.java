package com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.modification;

/**
 * Created by pierr on 14/04/2016.
 */

public interface Modification {

    void showProgress();

    void hideProgress();

    void setTitleError(String error);

    void setPhotoError(String error);

    void setBeginDateError(String error);

    void setEndDateError(String error);

    void setLocationError(String error);

    void setDescriptionError(String error);

    void setDialog(String title, String msg);
}
