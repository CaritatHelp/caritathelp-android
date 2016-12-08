package com.eip.red.caritathelp.Presenters.Sign.Up.Person;

import android.text.TextUtils;

import com.eip.red.caritathelp.Models.User.User;

import java.util.HashMap;

/**
 * Created by pierr on 23/03/2016.
 */

public class SignUpPersonInteractor {

    static final private String     ERROR_MANDATORY = "Ce champ est obligatoire";

    private User    user;

    public SignUpPersonInteractor(User user) {
        this.user = user;
    }

    public void next(HashMap<String, String> data, IOnSignUpPersonFinishedListener listener) {
        // Check Errors
        boolean error = false;

        // Check Firstname
        if (TextUtils.isEmpty(data.get("firstname"))) {
            listener.onFirstNameError(ERROR_MANDATORY);
            error = true;
        }

        // Check Lastname
        if (TextUtils.isEmpty(data.get("lastname"))) {
            listener.onLastNameError(ERROR_MANDATORY);
            error = true;
        }

        if (!error) {
            // Set User Model
            user.setFirstname(data.get("firstname"));
            user.setLastname(data.get("lastname"));
            user.setBirthday(data.get("birthday"));
            user.setGender(data.get("gender"));

            listener.onSuccess(user);
        }
    }

    public User getUser() {
        return user;
    }
}
