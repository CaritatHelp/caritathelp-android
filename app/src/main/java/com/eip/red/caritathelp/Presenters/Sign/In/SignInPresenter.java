package com.eip.red.caritathelp.Presenters.Sign.In;

import android.content.Intent;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Sign.In.SignInView;

/**
 * Created by pierr on 22/03/2016.
 */

public class SignInPresenter implements ISignInPresenter, IOnSignInFinishedListener{

    SignInView view;
    SignInInteractor    interactor;

    public SignInPresenter(SignInView view) {
        this.view = view;
        interactor = new SignInInteractor(view.getActivity().getApplicationContext());
    }


    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.btn_sign_in:
                view.showProgress();
                interactor.signIn(view.getMail(), view.getPassword(), this);
                break;
            case R.id.btn_password_forgot:
                break;
        }
    }

    @Override
    public void onEmailError(String error) {
        view.hideProgress();
        view.setEmailError(error);
    }

    @Override
    public void onPasswordError(String error) {
        view.hideProgress();
        view.setPasswordError(error);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccess(User user) {
        view.hideProgress();

        // Navigate To MainActivity
        SignActivity activity = (SignActivity) view.getActivity();
        Intent intent = new Intent(activity, MainActivity.class);

        intent.putExtra("user", user);
        view.startActivity(intent);
        activity.finish();
    }

}
