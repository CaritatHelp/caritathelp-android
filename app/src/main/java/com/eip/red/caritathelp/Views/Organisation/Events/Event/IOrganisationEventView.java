package com.eip.red.caritathelp.Views.Organisation.Events.Event;

import com.eip.red.caritathelp.Models.News.News;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */

public interface IOrganisationEventView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);

    void updateRV(List<News> newsList);

}
