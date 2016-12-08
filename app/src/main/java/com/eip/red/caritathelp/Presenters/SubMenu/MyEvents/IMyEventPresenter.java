package com.eip.red.caritathelp.Presenters.SubMenu.MyEvents;

import com.eip.red.caritathelp.Models.Organisation.Event;

/**
 * Created by pierr on 18/03/2016.
 */

public interface IMyEventPresenter {

    boolean isMainUser();

    void onClick(int viewId);

    void getMyEvents(boolean init, String range, boolean isSwipeRefresh);

    void navigateToEventView(Event event);
}
