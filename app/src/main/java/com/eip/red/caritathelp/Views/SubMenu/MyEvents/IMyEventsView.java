package com.eip.red.caritathelp.Views.SubMenu.MyEvents;

import com.eip.red.caritathelp.Models.Organisation.Event;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */

public interface IMyEventsView {

    void showProgress();

    void hideProgress();

    void setDialogError(String title, String msg);
}
