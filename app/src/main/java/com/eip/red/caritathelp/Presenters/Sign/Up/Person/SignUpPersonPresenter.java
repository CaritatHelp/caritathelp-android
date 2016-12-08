package com.eip.red.caritathelp.Presenters.Sign.Up.Person;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Sign.Up.SignUpCredentialsView;
import com.eip.red.caritathelp.Views.Sign.Up.SignUpPersonView;

import java.util.HashMap;

/**
 * Created by pierr on 23/03/2016.
 */

public class SignUpPersonPresenter implements ISignUpPersonPresenter, IOnSignUpPersonFinishedListener {

    private SignUpPersonView        view;
    private SignUpPersonInteractor  interactor;

    private boolean     male = false;
    private boolean     female = false;

    private Drawable    male_normal;
    private Drawable    male_selected;
    private Drawable    female_normal;
    private Drawable    female_selected;
    private int         green;
    private int         black;

    public SignUpPersonPresenter(SignUpPersonView view, User user) {
        this.view = view;

        interactor = new SignUpPersonInteractor(user);

        // Init Drawable & Color
        Context context = view.getActivity().getApplicationContext();
        male_normal = ContextCompat.getDrawable(context, R.drawable.male);
        male_selected = ContextCompat.getDrawable(context, R.drawable.male_selected);
        female_normal = ContextCompat.getDrawable(context, R.drawable.female);
        female_selected = ContextCompat.getDrawable(context, R.drawable.female_selected);
        green = ContextCompat.getColor(context, R.color.primary);
        black = ContextCompat.getColor(context, R.color.primary_text);
    }

    @Override
    public void init() {
        User    user = interactor.getUser();
        String  firstName = user.getFirstname();
        String  lastName = user.getLastname();
        String  birthday = user.getBirthday();
        String  gender = user.getGender();

        if (firstName != null)
            view.getFirstname().setText(firstName);

        if (lastName != null)
            view.getLastname().setText(lastName);

        if (birthday != null)
            view.getBirthday().setText(birthday);

        if (gender != null && gender.equals("m"))
            genderDisplay(true);
        else if (gender != null)
            genderDisplay(false);

    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.btn_birthday:
                view.getDatePickerDialog().show();
                break;
            case R.id.btn_gender_male:
                genderDisplay(true);
                break;
            case R.id.btn_gender_female:
                genderDisplay(false);
                break;
            case R.id.btn_next:
                // Init DataView
                HashMap<String, String>     data = new HashMap<>();

                data.put("firstname", view.getFirstname().getText().toString());
                data.put("lastname", view.getLastname().getText().toString());
                data.put("birthday", view.getBirthdayStr());
                if (male)
                    data.put("gender", "m");
                else if (female)
                    data.put("gender", "f");
                else
                    data.put("gender", null);

                interactor.next(data, this);
                break;
        }
    }

    private void genderDisplay(boolean m) {
        if (m) {
            view.getMaleImg().setImageDrawable(male_selected);
            view.getFemaleImg().setImageDrawable(female_normal);

            view.getMaleTv().setTypeface(Typeface.DEFAULT_BOLD);
            view.getFemaleTv().setTypeface(Typeface.DEFAULT);

            view.getMaleTv().setTextColor(green);
            view.getFemaleTv().setTextColor(black);

            male = true;
            female = false;
        }
        else {
            view.getFemaleImg().setImageDrawable(female_selected);
            view.getMaleImg().setImageDrawable(male_normal);

            view.getFemaleTv().setTypeface(Typeface.DEFAULT_BOLD);
            view.getMaleTv().setTypeface(Typeface.DEFAULT);

            view.getFemaleTv().setTextColor(green);
            view.getMaleTv().setTextColor(black);

            male = false;
            female = true;
        }
    }

    @Override
    public void onFirstNameError(String error) {
        view.setFirstNameError(error);
    }

    @Override
    public void onLastNameError(String error) {
        view.setLastNameError(error);
    }

    @Override
    public void onBirthdayError(String error) {
        view.setBirthdayError(error);
    }

    @Override
    public void onSuccess(User user) {
        ((SignActivity) view.getActivity()).replaceView(new SignUpCredentialsView(), Animation.SLIDE_LEFT_RIGHT);
    }

}
