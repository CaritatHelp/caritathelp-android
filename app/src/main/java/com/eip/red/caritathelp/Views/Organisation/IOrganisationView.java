package com.eip.red.caritathelp.Views.Organisation;

import com.eip.red.caritathelp.Models.News.News;

import java.util.List;

/**
 * Created by pierr on 28/03/2016.
 */

public interface IOrganisationView {

    void setLogoPosition(String right);

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);

    void updateRV(List<News> newsList);
}
