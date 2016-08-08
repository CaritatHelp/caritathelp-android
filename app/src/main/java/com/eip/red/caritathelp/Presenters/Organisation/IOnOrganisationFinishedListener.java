package com.eip.red.caritathelp.Presenters.Organisation;

import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Organisation;

import java.util.List;

/**
 * Created by pierr on 28/03/2016.
 */

public interface IOnOrganisationFinishedListener {

    void onDialogError(String title, String msg);

    void onSuccessGetOrganisation(Organisation organisation);

    void onSuccessLeave();

    void onSuccessJoin();

    void onSuccessReply(String acceptance);

    void onNewsRequestSuccess(List<News> newsList);
}
