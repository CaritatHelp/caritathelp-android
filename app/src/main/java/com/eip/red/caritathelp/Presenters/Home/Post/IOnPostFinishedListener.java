package com.eip.red.caritathelp.Presenters.Home.Post;

/**
 * Created by pierr on 07/09/2016.
 */
public interface IOnPostFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessPostNews();
}
