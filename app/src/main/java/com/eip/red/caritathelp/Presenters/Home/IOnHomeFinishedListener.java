package com.eip.red.caritathelp.Presenters.Home;

import com.eip.red.caritathelp.Models.News.News;

import java.util.List;

/**
 * Created by pierr on 08/08/2016.
 */

public interface IOnHomeFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessGetNews(List<News> newsList);

}
