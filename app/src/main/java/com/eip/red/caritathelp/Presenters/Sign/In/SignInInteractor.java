package com.eip.red.caritathelp.Presenters.Sign.In;

import android.content.Context;
import android.text.TextUtils;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Profile.MainPicture;
import com.eip.red.caritathelp.Models.Profile.MainPictureJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.User.UserJson;
import com.eip.red.caritathelp.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

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

        json.addProperty("email", email);
        json.addProperty("password", password);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.SESSIONS_SIGN_IN)
                .setJsonObjectBody(json)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception error, Response<String> result) {
                        if (error == null) {
                            try {
                                final JSONObject jsonObject = new JSONObject(result.getResult());

                                switch (result.getHeaders().code()) {
                                    case Network.API_STATUS_ERROR:
                                        final String message = jsonObject.getString("message");
                                        if (message.equals(Network.API_MSG_UNKNOWN_MAIL))
                                            listener.onEmailError("Mail inconnu");
                                        break;
                                    case Network.API_STATUS_ERROR_401:
                                        listener.onPasswordError("Mauvais mot de passe");
                                        break;
                                    default:
                                        User user = new Gson().fromJson(jsonObject.getString("response"), User.class);
                                        user.setToken(result.getHeaders().getHeaders().get("Access-Token"));
                                        user.setClient(result.getHeaders().getHeaders().get("Client"));
                                        listener.onSuccess(user);
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    else
                        listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }

}

