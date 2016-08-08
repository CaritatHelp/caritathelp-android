package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;


import com.eip.red.caritathelp.Models.News.News;

import java.util.List;

/**
 * Created by pierr on 14/04/2016.
 */

public interface IOnOrganisationEventFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessGetEvent(String title, String rights);

    void onSuccessGetNews(List<News> newsList);
}
