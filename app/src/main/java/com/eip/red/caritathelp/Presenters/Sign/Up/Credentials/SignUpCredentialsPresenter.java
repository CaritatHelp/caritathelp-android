package com.eip.red.caritathelp.Presenters.Sign.Up.Credentials;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Sign.In.SignInView;
import com.eip.red.caritathelp.Views.Sign.Up.SignUpCredentialsView;

/**
 * Created by pierr on 23/03/2016.
 */

public class SignUpCredentialsPresenter implements ISignUpCredentialsPresenter, IOnSignUpCredentialsFinishedListener {

    private SignUpCredentialsView       view;
    private SignUpCredentialInteractor  interactor;

    public SignUpCredentialsPresenter(SignUpCredentialsView view, User user) {
        this.view = view;

        interactor = new SignUpCredentialInteractor(view.getActivity().getApplicationContext(), user);
    }

    @Override
    public void init() {
        User    user = interactor.getUser();
        String  mail = user.getMail();

        if (mail != null)
            view.getMail().setText(mail);
    }

    @Override
    public void onClick(int viewId) {
        if (viewId == R.id.btn_next) {
            view.showProgress();
            interactor.signUp(view.getMail().getText().toString(), view.getPassword().getText().toString(), view.getPasswordVerification().getText().toString(), this);
        }
    }

    @Override
    public void onMailError(String error) {
        view.hideProgress();
        view.setMailError(error);
    }

    @Override
    public void onPasswordError(String error) {
        view.hideProgress();
        view.setPasswordError(error);
    }

    @Override
    public void onPasswordVerificationError(String error) {
        view.hideProgress();
        view.setPasswordVerificationError(error);
    }

    @Override
    public void onSuccess(final User user) {
        view.hideProgress();
        new AlertDialog.Builder(view.getActivity())
                .setTitle("Inscription réussie!")
                .setMessage("Voulez-vous vous connecter directement à l'application ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToMainActivity(user);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToSignInActivity();
                    }
                })
                .show();
    }

    private void goToMainActivity(User user) {
        SignActivity    activity = (SignActivity) view.getActivity();
        Intent          intent = new Intent(activity, MainActivity.class);

        intent.putExtra("user", user);
        view.startActivity(intent);
        activity.finish();
    }

    private void goToSignInActivity() {
        SignActivity   activity = (SignActivity) view.getActivity();

        view.startActivity(new Intent(activity, SignInView.class));
        activity.finish();
    }

}
