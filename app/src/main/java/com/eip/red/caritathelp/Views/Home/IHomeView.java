package com.eip.red.caritathelp.Views.Home;

import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Event;

import java.util.List;

/**
 * Created by pierr on 05/04/2016.
 */
public interface IHomeView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);

    void updateRecyclerViewData(List<News> newsList);
}
