package com.eip.red.caritathelp.Views.SubMenu.Profile;

import com.eip.red.caritathelp.Models.News.News;

import java.util.List;

/**
 * Created by pierr on 11/05/2016.
 */

public interface IProfileView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);

    void updateRecyclerViewData(List<News> newsList);

}
