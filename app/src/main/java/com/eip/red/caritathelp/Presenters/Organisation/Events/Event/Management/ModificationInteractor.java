package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.EventInformations;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by pierr on 14/04/2016.
 */

public class ModificationInteractor {

    static final private String     ERROR_MANDATORY = "Ce champ est obligatoire";

    private Context context;
    private User    user;
    private int     eventId;
    private String  encodedImg;

    public ModificationInteractor(Context context, User user, int eventId) {
        this.context = context;
        this.user = user;
        this.eventId = eventId;
    }

    public void getEvent(ProgressBar progressBar, final IModificationFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENTS_INFORMATIONS + eventId)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .progressBar(progressBar)
                .as(new TypeToken<EventInformations>(){})
                .setCallback(new FutureCallback<EventInformations>() {
                    @Override
                    public void onCompleted(Exception error, EventInformations result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetEvent(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void saveEventModifications(final IModificationFinishedListener listener, ProgressBar progressBar, HashMap<String, String> data) {
        JsonObject json = new JsonObject();
        String title = data.get("title");
        String description = data.get("description");
        String place = data.get("location");
        String dateBegin = data.get("date begin");
        String dateEnd = data.get("date end");

        if (title != null && !TextUtils.isEmpty(title))
            json.addProperty("title", title);
        if (description != null && !TextUtils.isEmpty(description))
            json.addProperty("description", description);
        if (place != null && !TextUtils.isEmpty(place))
            json.addProperty("place", place);
        if (dateBegin != null && !TextUtils.isEmpty(dateBegin))
            json.addProperty("begin", dateBegin);
        if (dateEnd != null && !TextUtils.isEmpty(dateEnd))
            json.addProperty("end", dateEnd);

        if (place != null && !TextUtils.isEmpty(place)) {
            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addressLocationList = gcd.getFromLocationName(place, 1);

                if (addressLocationList != null && addressLocationList.size() > 0) {
                    json.addProperty("latitude", addressLocationList.get(0).getLatitude());
                    json.addProperty("longitude", addressLocationList.get(0).getLongitude());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Ion.with(context)
                .load("PUT", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT_MANAGEMENT + eventId)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .progressBar(progressBar)
                .setJsonObjectBody(json)
                .as(new TypeToken<EventInformations>(){})
                .setCallback(new FutureCallback<EventInformations>() {
                    @Override
                    public void onCompleted(Exception error, EventInformations result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else {
                                if (encodedImg != null)
                                    uploadEventImg(result.getResponse(), encodedImg, "filename.jpg", "original_filename.jpg", true, listener);
                                else
                                    listener.onSuccessSaveEventModifications(result.getResponse());

                            }
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void uploadEventImg(final Event event, String file, String filename, String originFilename, boolean isMain, final IModificationFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("file", file);
        json.addProperty("filename", filename);
        json.addProperty("original_filename", originFilename);
        json.addProperty("is_main", isMain);
        json.addProperty("event_id", event.getId());

        Ion.with(context)
                .load("POST", Network.API_LOCATION_2 + Network.API_REQUEST_PICTURES)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .as(new TypeToken<JsonObject>() {})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        if (error == null)
                            listener.onSuccessSaveEventModifications(event);
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void setEncodedImg(String encodedImg) {
        this.encodedImg = encodedImg;
    }
}
