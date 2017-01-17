package com.eip.red.caritathelp.Views.Sign.Up;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Sign.Up.Begin.SignUpBeginPresenter;
import com.eip.red.caritathelp.R;
import com.karumi.dexter.Dexter;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by pierr on 23/03/2016.
 */

public class SignUpBeginView extends Fragment implements View.OnClickListener {

    private SignUpBeginPresenter presenter;

    private Switch      geolocation;
    private Switch      notifications;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dexter.initialize(getActivity());

        // Init Presenter
        User        user = ((SignActivity) getActivity()).getUser();
        presenter = new SignUpBeginPresenter(this, user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_begin, container, false);
        ButterKnife.bind(this, view);

        // Set ToolBar
        ((SignActivity) getActivity()).getToolBar().update("Inscription", true, false);

        // Init UI Elements
        geolocation = (Switch) view.findViewById(R.id.switch_geolocation);
        notifications = (Switch) view.findViewById(R.id.switch_notification);

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

    @OnCheckedChanged(R.id.switch_geolocation)
    public void onCheckedChangedGeolocation(boolean isChecked) {
        presenter.setGeolocation(isChecked);
    }

    public Switch getGeolocation() {
        return geolocation;
    }

    public Switch getNotifications() {
        return notifications;
    }
}
