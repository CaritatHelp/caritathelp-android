package com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations;

import com.eip.red.caritathelp.Models.Organisation.Organisation;

import java.util.List;

/**
 * Created by pierr on 24/02/2016.
 */

public interface IOnMyOrganisationsFinishedListener {

    void onDialog(String title, String msg, boolean isSwipeRefresh);

    void onSuccess(List<Organisation> myOrganisationsOwner, List<Organisation> myOrganisationsMember, boolean isSwipeRefresh);
}
