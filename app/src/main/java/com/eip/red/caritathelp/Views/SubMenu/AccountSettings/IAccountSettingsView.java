package com.eip.red.caritathelp.Views.SubMenu.AccountSettings;

/**
 * Created by pierr on 22/01/2016.
 */

public interface IAccountSettingsView {

    void showProgress();

    void hideProgress();

    void setDialog(String title, String msg);

    void setEmailError(String error);

    void setCurrentPasswordError(String error);

    void setNewPasswordError(String error);

    void setNewPasswordCheckingError(String error);
}
