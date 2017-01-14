package com.eip.red.caritathelp.Views.SubMenu.MyOrganisations.OrganisationCreation;

/**
 * Created by pierr on 24/02/2016.
 */

public interface IOrganisationCreation {

    void showProgress();

    void hideProgress();

    void setNameError(String error);

    void setCityError(String error);

    void setDescriptionError(String error);

    void setDialogError(String title, String msg);

    void navigateToOrganisationView(String name);
}
