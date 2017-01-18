package com.eip.red.caritathelp.Presenters.SubMenu.AccountSettings;

import android.widget.EditText;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.SubMenu.AccountSettings.AccountSettingsView;

import java.util.HashMap;

/**
 * Created by pierr on 22/01/2016.
 */

public class AccountSettingsPresenter implements IAccountSettingsPresenter, IOnAccountSettingsFinishedListener {

    private AccountSettingsView         view;
    private AccountSettingsInteractor   interactor;

    public AccountSettingsPresenter(AccountSettingsView view, User user) {
        this.view = view;
        interactor = new AccountSettingsInteractor(view.getContext(), user);
    }

    @Override
    public void getUser() {
        view.showProgress();
        interactor.getUser(this);
    }

    @Override
    public void onClick(int viewId) {
        if (viewId == R.id.btn_save) {
            view.showProgress();
            interactor.saveModification(view.getForm(), this);
        }
    }

    @Override
    public void setGeolocation(boolean geolocation) {
        interactor.setGeolocation(geolocation);
    }

    @Override
    public void setNotification(boolean notification) {
        interactor.setNotification(notification);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onEmailError(String error) {
        view.setEmailError(error);
        view.hideProgress();
    }

    @Override
    public void onCurrentPasswordError(String error) {
        view.setCurrentPasswordError(error);
        view.hideProgress();
    }

    @Override
    public void onNewPasswordError(String error) {
        view.setNewPasswordError(error);
        view.hideProgress();
    }

    @Override
    public void onNewPasswordCheckingError(String error) {
        view.setNewPasswordCheckingError(error);
        view.hideProgress();
    }

    @Override
    public void onSuccessGetUser(User user) {
        updateViewData(user);
        view.hideProgress();
    }

    @Override
    public void onSuccessSaveModification(User user) {
        updateViewData(user);
        view.hideProgress();
    }

    private void updateViewData(User user) {
        HashMap<Integer, EditText>  form = view.getForm();

        // Invalidate && Clear Form Text
        for (EditText editText : form.values()) {
            editText.invalidate();
            editText.getText().clear();
        }

        // Update Text
        form.get(AccountSettingsView.FIRSTNAME).setHint(user.getFirstname());
        form.get(AccountSettingsView.LASTNAME).setHint(user.getLastname());
        form.get(AccountSettingsView.MAIL).setHint(user.getMail());
        form.get(AccountSettingsView.PASSWORD_CURRENT).setHint("Mot de passe actuel");
        form.get(AccountSettingsView.PASSWORD_NEW).setHint("Nouveau mot de passe");
        form.get(AccountSettingsView.PASSWORD_NEW_CHECKING).setHint("Retapez le nouveau mot de passe");

        if (user.isAllowgps())
            view.getGeolSwitch().setChecked(true);

        if (user.isNotifications())
            view.getNotifSwitch().setChecked(true);
    }

}
