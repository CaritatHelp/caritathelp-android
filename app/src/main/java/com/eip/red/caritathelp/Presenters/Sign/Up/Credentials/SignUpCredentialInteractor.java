package com.eip.red.caritathelp.Presenters.Sign.Up.Credentials;

import android.content.Context;
import android.text.TextUtils;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.User.UserJson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 23/03/2016.
 */
public class SignUpCredentialInteractor {

    static final private String     ERROR_MANDATORY = "Ce champ est obligatoire";
    static final private String     ERROR_PASSWORD_CHECKING = "Mot de passe différent";

    private Context context;
    private User    user;

    public SignUpCredentialInteractor(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void signUp(String mail, String password, String passwordVerification, IOnSignUpCredentialsFinishedListener listener) {
        if (!checkErrors(mail, password, passwordVerification, listener))
            signUpRequest(listener);
    }

    private boolean checkErrors(String mail, String password, String passwordVerification, IOnSignUpCredentialsFinishedListener listener) {
        boolean error = false;

        // Check Mail
        if (TextUtils.isEmpty(mail)) {
            listener.onMailError(ERROR_MANDATORY);
            error = true;
        }
        else
            user.setMail(mail);


        // Check Password
        if (TextUtils.isEmpty(password)) {
            listener.onPasswordError(ERROR_MANDATORY);
            error = true;
        }

        // Check Password Verification
        if (TextUtils.isEmpty(passwordVerification)) {
            listener.onPasswordVerificationError(ERROR_MANDATORY);
            error = true;
        }
        else if (!password.equals(passwordVerification)) {
            listener.onPasswordVerificationError(ERROR_PASSWORD_CHECKING);
            error = true;
        }
        else
            user.setPassword(password);

        return (error);
    }

    private void signUpRequest(final IOnSignUpCredentialsFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("mail", user.getMail());
        json.addProperty("password", user.getPassword());
        json.addProperty("firstname", user.getFirstname());
        json.addProperty("lastname", user.getLastname());
        json.addProperty("birthday", user.getBirthday());
        json.addProperty("gender", user.getGender());
        json.addProperty("allowgps", user.isGeolocation());

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_SUBSCRIBE)
                .setJsonObjectBody(json)
                .as(new TypeToken<UserJson>() {})
                .setCallback(new FutureCallback<UserJson>() {
                    @Override
                    public void onCompleted(Exception error, UserJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR) {
                                if (result.getMessage().equals(Network.API_MSG_INVALID_MAIL))
                                    listener.onMailError("Mail invalide");
                                else if (result.getMessage().equals(Network.API_MSG_UNAVAILABLE_MAIL))
                                    listener.onMailError("Mail déjà utilisé");
                            } else
                                listener.onSuccess(result.getResponse());
                        }
                    }
                });
    }

    public User getUser() {
        return user;
    }
}
