package com.eip.red.caritathelp.Presenters.Sign.In;

import com.eip.red.caritathelp.Models.User.User;

/**
 * Created by pierr on 22/03/2016.
 */

public interface IOnSignInFinishedListener {

    void onEmailError(String error);

    void onPasswordError(String error);

    void onDialog(String title, String msg);

    void onSuccess(User user);
}
