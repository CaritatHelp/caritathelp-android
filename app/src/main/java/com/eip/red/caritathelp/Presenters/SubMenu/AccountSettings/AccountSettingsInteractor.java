package com.eip.red.caritathelp.Presenters.SubMenu.AccountSettings;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.User.UserJson;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.SubMenu.AccountSettings.AccountSettingsView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by pierr on 22/01/2016.
 */
public class AccountSettingsInteractor {

    private Context     context;
    private User        user;

    public AccountSettingsInteractor(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void getUser(final IOnAccountSettingsFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS + user.getId())
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<UserJson>() {})
                .setCallback(new FutureCallback<UserJson>() {
                    @Override
                    public void onCompleted(Exception error, UserJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetUser(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void saveModification(final HashMap<Integer, EditText> modification, IOnAccountSettingsFinishedListener listener) {
        if (!simpleVerification(modification, listener))
            apiRequest(modification, listener);

    }

    private boolean simpleVerification(final HashMap<Integer, EditText> modification, final IOnAccountSettingsFinishedListener listener) {
        boolean     error = false;
        String      password_current = modification.get(AccountSettingsView.PASSWORD_CURRENT).getText().toString();
        String      password_new = modification.get(AccountSettingsView.PASSWORD_NEW).getText().toString();
        String      password_new_checking = modification.get(AccountSettingsView.PASSWORD_NEW_CHECKING).getText().toString();

        if (!TextUtils.isEmpty(password_current)) {
            if (TextUtils.isEmpty(password_new)) {
                listener.onNewPasswordError("Champ obligatoire");
                error = true;
            }
            if (TextUtils.isEmpty(password_new_checking)) {
                listener.onNewPasswordCheckingError("Champ obligatoire");
                error = true;
            }
            else if (password_new_checking.length() < 8) {
                listener.onNewPasswordCheckingError("Le mot de passe doit contenir 8 caractères minimum.");
                error = true;
            }
        }

        if(!password_new.equals(password_new_checking)) {
            listener.onNewPasswordCheckingError("Mot de passe différent");
            error = true;
        }

        return (error);
    }

    private void apiRequest(final HashMap<Integer, EditText> modification, final IOnAccountSettingsFinishedListener listener) {
        final JsonObject          json = new JsonObject();
        final String        firstname = modification.get(AccountSettingsView.FIRSTNAME).getText().toString();
        final String        lastname = modification.get(AccountSettingsView.LASTNAME).getText().toString();
        final String        mail = modification.get(AccountSettingsView.MAIL).getText().toString();
        final String        password = modification.get(AccountSettingsView.PASSWORD_NEW).getText().toString();

        if (!TextUtils.isEmpty(mail) && !mail.equals(user.getMail()))
            json.addProperty("email", mail);

        if (!TextUtils.isEmpty(firstname) && !firstname.equals(user.getFirstname()))
            json.addProperty("firstname", firstname);

        if (!TextUtils.isEmpty(lastname) && !lastname.equals(user.getLastname()))
            json.addProperty("lastname", lastname);

        json.addProperty("allowgps", user.isAllowgps());
        json.addProperty("allow_notifications", user.isAllow_notifications());

        if (user.isAllowgps()) {
            json.addProperty("latitude", user.getLatitude());
            json.addProperty("longitude", user.getLongitude());
        }

        Ion.with(context)
                .load("PUT", Network.API_LOCATION + Network.API_REQUEST_REGISTRATIONS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
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
                                        break;
                                    case Network.API_STATUS_ERROR_401:
                                        break;
                                    default:
                                        user = new Gson().fromJson(jsonObject.getString("response"), User.class);
                                        user.setToken(result.getHeaders().getHeaders().get("Access-Token"));
                                        user.setClient(result.getHeaders().getHeaders().get("Client"));

                                        listener.onSuccessSaveModification(user);
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

        if (!TextUtils.isEmpty(password))
            setPassword(listener, password);
    }

    private void setPassword(final IOnAccountSettingsFinishedListener listener, String password) {
        Ion.with(context)
                .load("PUT", Network.API_LOCATION + Network.API_REQUEST_PASSWORD)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .addQuery("password", password)
                .addQuery("password_confirmation", password)
                .as(new TypeToken<UserJson>() {})
                .setCallback(new FutureCallback<UserJson>() {
                    @Override
                    public void onCompleted(Exception error, UserJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessSaveModification(user);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void setGeolocation(boolean geolocation) {
        user.setGeolocation(geolocation);
    }

    public void setNotification(boolean notification) {
        user.setNotifications(notification);
    }
}
