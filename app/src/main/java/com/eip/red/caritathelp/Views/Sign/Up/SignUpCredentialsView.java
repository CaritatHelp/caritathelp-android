package com.eip.red.caritathelp.Views.Sign.Up;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Sign.Up.Credentials.SignUpCredentialsPresenter;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 23/03/2016.
 */
public class SignUpCredentialsView extends Fragment implements ISignUpCredentialsView, View.OnClickListener {

    private SignUpCredentialsPresenter presenter;

    private EditText    mail;
    private EditText    password;
    private EditText    passwordVerification;
    private ProgressBar progressBar;

    private AlertDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Presenter
        User        user = ((SignActivity) getActivity()).getUser();
        presenter = new SignUpCredentialsPresenter(this, user);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_credentials, container, false);

        // Set ToolBar
        ((SignActivity) getActivity()).getToolBar().update("Inscription", true, false);

        // Init UI Elements
        mail = (EditText) view.findViewById(R.id.mail);
        password = (EditText) view.findViewById(R.id.password);
        passwordVerification = (EditText) view.findViewById(R.id.password_verification);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Init Listener
        view.findViewById(R.id.btn_next).setOnClickListener(this);

        // Init View Value if User model is not null
        presenter.init();

        return (view);
    }


    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMailError(String error) {
        mail.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        password.setError(error);
    }

    @Override
    public void setPasswordVerificationError(String error) {
        passwordVerification.setError(error);
    }

    @Override
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    public EditText getMail() {
        return mail;
    }

    public EditText getPassword() {
        return password;
    }

    public EditText getPasswordVerification() {
        return passwordVerification;
    }
}
