package com.eip.red.caritathelp.Presenters.Organisation.Informations;

import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.Organisation;

import java.util.List;

/**
 * Created by pierr on 11/05/2016.
 */

public interface IOnOrganisationInformationsFinishedListener {

    void onDialogError(String title, String msg);

    void onSuccess(Organisation organisation);
}
