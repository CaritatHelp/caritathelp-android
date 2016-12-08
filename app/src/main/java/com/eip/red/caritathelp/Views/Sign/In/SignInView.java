package com.eip.red.caritathelp.Views.Sign.In;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Activities.Sign.SignActivityToolbar;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Sign.In.SignInPresenter;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 22/03/2016.
 */

public class SignInView extends Fragment implements ISignInView, View.OnClickListener {

    private SignInPresenter presenter;

    private EditText    email;
    private EditText    password;
    private ProgressBar progressBar;
    private AlertDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Presenter
        presenter = new SignInPresenter(this);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // Set ToolBar
        SignActivityToolbar toolbar = ((SignActivity) getActivity()).getToolBar();
        toolbar.setVisibility(View.VISIBLE);
        toolbar.update("Caritathelp", false, true);

        // Init UI Elements
        email = (EditText) view.findViewById(R.id.mail);
        password = (EditText) view.findViewById(R.id.password);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Init Listener
        view.findViewById(R.id.btn_sign_in).setOnClickListener(this);
        view.findViewById(R.id.btn_password_forgot).setOnClickListener(this);

        // Init User Model for SignUp Fragments
        ((SignActivity) getActivity()).setUser(new User());

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
    public void setEmailError(String error) {
        email.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        password.setError(error);
    }

    @Override
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    public String getMail() {
        return (email.getText().toString());
    }

    public String getPassword() {
        return (password.getText().toString());
    }
}
