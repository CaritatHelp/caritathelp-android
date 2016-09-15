package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;

import com.eip.red.caritathelp.Models.News.News;

/**
 * Created by pierr on 18/03/2016.
 */
public interface IOrganisationEventPresenter {

    void onClick(int viewId);

    void getData();

    void getNews(boolean isSwipeRefresh);

    void goToProfileView(News news);

    void goToCommentView(News news);
}
