package com.eip.red.caritathelp.Presenters.Sign.Up.Person;

import com.eip.red.caritathelp.Models.User.User;

/**
 * Created by pierr on 23/03/2016.
 */

public interface IOnSignUpPersonFinishedListener {

    void onFirstNameError(String error);

    void onLastNameError(String error);

    void onBirthdayError(String error);

    void onSuccess(User user);
}
