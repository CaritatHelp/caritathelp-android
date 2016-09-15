package com.eip.red.caritathelp.Presenters.Organisation;

import com.eip.red.caritathelp.Models.News.News;

/**
 * Created by pierr on 11/03/2016.
 */

public interface IOrganisationPresenter {

    void onClick(int viewId);

    void getData();

    void getNews(boolean isSwipeRefresh);

    void goToProfileView(News news);

    void goToCommentView(News news);
}
