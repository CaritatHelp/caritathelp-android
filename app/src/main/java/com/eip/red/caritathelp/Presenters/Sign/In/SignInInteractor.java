package com.eip.red.caritathelp.Presenters.Sign.In;

import android.content.Context;
import android.text.TextUtils;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 22/03/2016.
 */

public class SignInInteractor {
    static final private String     ERROR_MANDATORY = "Ce champ est obligatoire";

    private Context context;

    public SignInInteractor(Context context) {
        this.context = context;
    }

    public void signIn(String email, String password, IOnSignInFinishedListener listener) {
        if (!simpleVerification(email, password, listener))
            apiRequest(email, password, listener);
    }

    private boolean simpleVerification(String email, String password, IOnSignInFinishedListener listener) {
        boolean error = false;

        if (TextUtils.isEmpty(email)){
            listener.onEmailError(ERROR_MANDATORY);
            error = true;
        }
        if (TextUtils.isEmpty(password)){
            listener.onPasswordError(ERROR_MANDATORY);
            error = true;
        }
        return (error);
    }

    private void apiRequest(String email, String password, final IOnSignInFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("mail", email);
        json.addProperty("password", password);

        Ion.with(context)
                .load(Network.API_LOCATION + Network.API_REQUEST_LOGIN)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {

                        if (error != null)
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                        else {
                            if (result.get(Network.API_PARAMETER_STATUS).getAsInt() == Network.API_STATUS_ERROR) {
                                if (result.get(Network.API_PARAMETER_MSG).getAsString().equals(Network.API_MSG_UNKNOWN_MAIL))
                                    listener.onEmailError("Mail inconnu");
                                else
                                    listener.onPasswordError("Mauvais mot de passe");
                            } else {
                                // Init User & Token Model
                                listener.onSuccess(new User(result), new Network(result));
                            }
                        }
                    }
                });
    }
}
