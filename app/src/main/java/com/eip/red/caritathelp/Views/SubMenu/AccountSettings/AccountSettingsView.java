package com.eip.red.caritathelp.Views.SubMenu.AccountSettings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.AccountSettings.AccountSettingsPresenter;
import com.eip.red.caritathelp.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by pierr on 22/01/2016.
 */

public class AccountSettingsView extends Fragment implements IAccountSettingsView, View.OnClickListener {

    static final public int    FIRSTNAME = 0;
    static final public int    LASTNAME = 1;
    static final public int    MAIL = 2;
    static final public int    PASSWORD_CURRENT= 3;
    static final public int    PASSWORD_NEW = 4;
    static final public int    PASSWORD_NEW_CHECKING = 5;

    @BindView(R.id.switch_geolocation) Switch geolSwitch;
    @BindView(R.id.switch_notification) Switch notifSwitch;

    private AccountSettingsPresenter    presenter;

    private HashMap<Integer, EditText>  form;
    private Button                      saveBtn;
    private ProgressBar                 progressBar;
    private AlertDialog                 dialog;

    public static Fragment newInstance() {
        AccountSettingsView fragment = new AccountSettingsView();
        Bundle              args = new Bundle();

        args.putInt("page", R.string.view_name_submenu_account_management);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User  Model
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();

        // Init Presenter
        presenter = new AccountSettingsPresenter(this, user);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submenu_account_settings, container, false);
        ButterKnife.bind(this, view);

        // Init UI Element
        form = new HashMap<>();
        form.put(FIRSTNAME, (EditText) view.findViewById(R.id.firstname));
        form.put(LASTNAME, (EditText) view.findViewById(R.id.lastname));
        form.put(MAIL, (EditText) view.findViewById(R.id.mail));
        form.put(PASSWORD_CURRENT, (EditText) view.findViewById(R.id.current_password));
        form.put(PASSWORD_NEW, (EditText) view.findViewById(R.id.new_password));
        form.put(PASSWORD_NEW_CHECKING, (EditText) view.findViewById(R.id.new_password_verification));
        saveBtn = (Button) view.findViewById(R.id.btn_save);
        progressBar = (ProgressBar) view.findViewById(R.id.account_settings_progress_bar);

        // Init Listener
        view.findViewById(R.id.btn_save).setOnClickListener(this);
        for (EditText editText : form.values()) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0)
                        saveBtn.setVisibility(View.VISIBLE);
                    else
                        saveBtn.setVisibility(View.INVISIBLE);
                }
            });
        }

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init User Profile
        presenter.getUser();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void setEmailError(String error) {
        form.get(MAIL).setError(error);
    }

    @Override
    public void setCurrentPasswordError(String error) {
        form.get(PASSWORD_CURRENT).setError(error);
    }

    @Override
    public void setNewPasswordError(String error) {
        form.get(PASSWORD_NEW).setError(error);
    }

    @Override
    public void setNewPasswordCheckingError(String error) {
        form.get(PASSWORD_NEW_CHECKING).setError(error);
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    @OnCheckedChanged(R.id.switch_geolocation)
    public void onCheckedChangedGeo(boolean isChecked) {
        presenter.setGeolocation(isChecked);
    }

    @OnCheckedChanged(R.id.switch_notification)
    public void onCheckedChangedNotif(boolean isChecked) {
        presenter.setNotification(isChecked);
    }

    public HashMap<Integer, EditText> getForm() {
        return form;
    }

    public Switch getGeolSwitch() {
        return geolSwitch;
    }

    public Switch getNotifSwitch() {
        return notifSwitch;
    }
}
