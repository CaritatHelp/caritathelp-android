package com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations.OrganisationCreation;

import android.content.Context;
import android.text.TextUtils;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.Organisation.OrganisationJson;
import com.eip.red.caritathelp.Models.Profile.MainPictureJson;
import com.eip.red.caritathelp.Models.User.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 24/02/2016.
 */

public class OrganisationCreationInteractor {

    static final private String     ERROR_MANDATORY = "Ce champ est obligatoire";

    private Context context;
    private User    user;
    private String  encodedImg;

    public OrganisationCreationInteractor(Context context, User user) {
        this.context = context;
        this.user = user;
        encodedImg = null;
    }

    public void createOrganisation(String name, String city, String description, IOnOrganisationCreationsFinishedListener listener) {
        if (!checkError(name, city, description, listener))
            apiRequest(name, city, description, listener);
    }

    private boolean checkError(String name, String city, String description, IOnOrganisationCreationsFinishedListener listener) {
        boolean error = false;

        // Check Organisation Name
        if (TextUtils.isEmpty(name)) {
            listener.onNameError(ERROR_MANDATORY);
            error = true;
        }

        // Check Organisation City
        if (TextUtils.isEmpty(city)) {
            listener.onCityError(ERROR_MANDATORY);
            error = true;
        }

        // Check Organisation Description
        if (TextUtils.isEmpty(description)) {
            listener.onDescriptionError(ERROR_MANDATORY);
            error = true;
        }

        return (error);
    }

    private void apiRequest(final String name, String city, String description, final IOnOrganisationCreationsFinishedListener listener) {
        JsonObject          json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("city", city);

        if (!TextUtils.isEmpty(description))
            json.addProperty("description", description);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .as(new TypeToken<OrganisationJson>() {})
                .setCallback(new FutureCallback<OrganisationJson>() {
                    @Override
                    public void onCompleted(Exception error, OrganisationJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR) {
                                if (result.getMessage().equals("Unavailable name"))
                                    listener.onDialogError("Association existante", "Veuillez modifier le nom de l'association");
                            }
                            else {
                                if (encodedImg != null)
                                    uploadProfileImg(result.getResponse(), encodedImg, "filename.jpg", "original_filename.jpg", result.getResponse().getId(), -1, "true", listener);
                                else
                                    listener.onSuccess(result.getResponse());
                            }
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void uploadProfileImg(final Organisation organisation, String file, String filename, String originFilename, int assocId, int eventId, String isMain, final IOnOrganisationCreationsFinishedListener listener) {
        JsonObject json = new JsonObject();
        json.addProperty("file", file);
        json.addProperty("filename", filename);
        json.addProperty("original_filename", originFilename);
        json.addProperty("is_main", isMain);

        if (assocId != -1)
            json.addProperty("assoc_id", assocId);

        if (eventId != -1)
            json.addProperty("event_id", eventId);

        Ion.with(context)
                .load("POST", Network.API_LOCATION_2 + Network.API_REQUEST_PICTURES)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .as(new TypeToken<MainPictureJson>() {})
                .setCallback(new FutureCallback<MainPictureJson>() {
                    @Override
                    public void onCompleted(Exception error, MainPictureJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialogError("Statut 400", result.getMessage());
                            else {
                                // Set Organisation Model
                                organisation.setPicture(result.getResponse());

                                // Go to Organisation View
                                listener.onSuccess(organisation);
                            }
                        }
                        else
                            listener.onDialogError("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void setEncodedImg(String encodedImg) {
        this.encodedImg = encodedImg;
    }
}
