package com.eip.red.caritathelp.Views;

/**
 * Created by pierr on 14/01/2017.
 */

public interface BaseView {
    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);
}
