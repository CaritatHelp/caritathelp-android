package com.eip.red.caritathelp.Views.Sign.Up;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Sign.Up.Person.SignUpPersonPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by pierr on 23/03/2016.
 */

public class SignUpPersonView extends Fragment implements ISignUpPersonView, View.OnClickListener {

    private SignUpPersonPresenter   presenter;

    private EditText    firstname;
    private EditText    lastname;
    private TextView    birthday;
    private CardView    maleBtn;
    private ImageView   maleImg;
    private TextView    maleTv;
    private CardView    femaleBtn;
    private ImageView   femaleImg;
    private TextView    femaleTv;

    private DatePickerDialog    datePickerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Presenter
        User        user = ((SignActivity) getActivity()).getUser();
        presenter = new SignUpPersonPresenter(this, user);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_person, container, false);

        // Set ToolBar
        ((SignActivity) getActivity()).getToolBar().update("Inscription", true, false);

        // Init UI Elements
        firstname = (EditText) view.findViewById(R.id.firstname);
        lastname = (EditText) view.findViewById(R.id.lastname);
        birthday = (TextView) view.findViewById(R.id.birthday);
        maleBtn = (CardView) view.findViewById(R.id.btn_gender_male);
        maleImg = (ImageView) view.findViewById(R.id.img_gender_male);
        maleTv = (TextView) view.findViewById(R.id.tv_gender_male);
        femaleBtn = (CardView) view.findViewById(R.id.btn_gender_female);
        femaleImg = (ImageView) view.findViewById(R.id.img_gender_female);
        femaleTv = (TextView) view.findViewById(R.id.tv_gender_female);

        // Init DatePicker
        initDatePicker();

        // Init Listener
        view.findViewById(R.id.btn_birthday).setOnClickListener(this);
        view.findViewById(R.id.btn_next).setOnClickListener(this);
        maleBtn.setOnClickListener(this);
        femaleBtn.setOnClickListener(this);

        // Init View If User Model Not Empty
        presenter.init();

        return (view);
    }

    private void initDatePicker() {
        // Init Date values
        Calendar calendar = Calendar.getInstance();
        int         year = calendar.get(Calendar.YEAR);
        int         month = calendar.get(Calendar.MONTH);
        int         day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                // Init Birthday Container
                List<Integer> birthdayList = new ArrayList<>();

                birthdayList.add(year);
                birthdayList.add(month);
                birthdayList.add(day);

                // Init Birthday String
                String birthdayStr = "";

                // Init Day
                if (day < 10)
                    birthdayStr += "0" + String.valueOf(day);
                else
                    birthdayStr += String.valueOf(day);

                // Init Month
                if (month < 10)
                    birthdayStr += "/0" + String.valueOf(month);
                else
                    birthdayStr += "/" + String.valueOf(month);

                // Init Year
                birthdayStr += "/" + String.valueOf(year);

                // Set Text Button
                birthday.setText(birthdayStr);

            }
        };

        datePickerDialog = new DatePickerDialog(this.getActivity(), myDateListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
    }


    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    @Override
    public void setFirstNameError(String error) {
        firstname.setError(error);
    }

    @Override
    public void setLastNameError(String error) {
        lastname.setError(error);
    }

    @Override
    public void setBirthdayError(String error) {
        birthday.setError(error);
    }

    public EditText getFirstname() {
        return firstname;
    }

    public EditText getLastname() {
        return lastname;
    }

    public TextView getBirthday() {
        return (birthday);
    }

    public String getBirthdayStr() {
        String  text = birthday.getText().toString();

        if (text.equals("Date de naissance"))
            return (null);
        return (text);
    }

    public TextView getMaleTv() {
        return maleTv;
    }

    public CardView getFemaleBtn() {
        return femaleBtn;
    }

    public ImageView getFemaleImg() {
        return femaleImg;
    }

    public TextView getFemaleTv() {
        return femaleTv;
    }

    public ImageView getMaleImg() {
        return maleImg;
    }

    public CardView getMaleBtn() {
        return maleBtn;
    }

    public DatePickerDialog getDatePickerDialog() {
        return datePickerDialog;
    }
}
