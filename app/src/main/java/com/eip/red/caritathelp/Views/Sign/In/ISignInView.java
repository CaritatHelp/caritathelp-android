package com.eip.red.caritathelp.Views.Sign.In;

/**
 * Created by pierr on 22/03/2016.
 */

public interface ISignInView {

    void showProgress();

    void hideProgress();

    void setEmailError(String error);

    void setPasswordError(String error);

    void setDialog(String title, String msg);
}
