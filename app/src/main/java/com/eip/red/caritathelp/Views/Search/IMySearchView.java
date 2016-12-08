package com.eip.red.caritathelp.Views.Search;

/**
 * Created by pierr on 21/04/2016.
 */

public interface IMySearchView {

    void show();

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);

    boolean isIconified();

    void setIconified(boolean iconify);
}
