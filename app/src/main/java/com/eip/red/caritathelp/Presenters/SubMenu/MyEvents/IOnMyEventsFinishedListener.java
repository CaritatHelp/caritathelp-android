package com.eip.red.caritathelp.Presenters.SubMenu.MyEvents;

import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.Organisation;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */

public interface IOnMyEventsFinishedListener {

    void onDialog(String title, String msg, boolean isSwipeRefresh);

    void onSuccessGetMyEventsInit(List<Event> events, boolean owner);

    void onSuccessGetMyEvents(List<Event> events, boolean swipeRefresh);

    void onSuccessGetMyOrganisations(List<Organisation> organisations);
}
