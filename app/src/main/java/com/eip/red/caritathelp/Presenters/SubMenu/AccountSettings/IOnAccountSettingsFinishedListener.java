package com.eip.red.caritathelp.Presenters.SubMenu.AccountSettings;

import com.eip.red.caritathelp.Models.User.User;

/**
 * Created by pierr on 22/01/2016.
 */
public interface IOnAccountSettingsFinishedListener {

    void onDialog(String title, String msg);

    void onEmailError(String error);

    void onCurrentPasswordError(String error);

    void onNewPasswordError(String error);

    void onNewPasswordCheckingError(String error);

    void onSuccessGetUser(User user);

    void onSuccessSaveModification(User user);
}
