package com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations.OrganisationCreation;

import com.eip.red.caritathelp.Models.Organisation.Organisation;

/**
 * Created by pierr on 24/02/2016.
 */

public interface IOnOrganisationCreationsFinishedListener {

    void onNameError(String error);

    void onCityError(String error);

    void onDescriptionError(String error);

    void onDialogError(String title, String msg);

    void onSuccess(Organisation organisation);
}
