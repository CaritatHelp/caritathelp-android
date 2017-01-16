package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;


import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Event;

import java.util.List;

/**
 * Created by pierr on 14/04/2016.
 */

public interface IOnOrganisationEventFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessGetData(Event event, List<News> newsList);

    void onSuccessGetNews(List<News> newsList);

    void onSuccessJoinEvent();

    void onSuccessLeaveEvent();

    void onSuccessRaiseEmergency();
}
