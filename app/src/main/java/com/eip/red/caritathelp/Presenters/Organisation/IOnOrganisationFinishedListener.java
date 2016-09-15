package com.eip.red.caritathelp.Presenters.Organisation;

import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Organisation;

import java.util.List;

/**
 * Created by pierr on 28/03/2016.
 */

public interface IOnOrganisationFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessGetData(Organisation organisation, List<News> newsList);

    void onSuccessGetNews(List<News> newsList);

    void onSuccessLeave();

    void onSuccessJoin();

    void onSuccessReply(String acceptance);
}
