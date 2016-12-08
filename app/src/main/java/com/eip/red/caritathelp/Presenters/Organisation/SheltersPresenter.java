package com.eip.red.caritathelp.Presenters.Organisation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Shelter.Shelter;
import com.eip.red.caritathelp.Models.Shelter.ShelterJson;
import com.eip.red.caritathelp.Models.Shelter.SheltersJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Organisation.Shelters.Shelters;
import com.eip.red.caritathelp.Views.Organisation.Shelters.SheltersView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import butterknife.ButterKnife;

/**
 * Created by pierr on 03/12/2016.
 */

public class SheltersPresenter implements Shelters.Presenter {

    private SheltersView view;
    private User user;
    private int organisationId;
    private boolean owner;

    public SheltersPresenter(SheltersView view, User user, int organisationId, boolean owner) {
        this.view = view;
        this.user = user;
        this.organisationId = organisationId;
        this.owner = owner;
    }

    @Override
    public void getShelters() {
        view.showProgress();

        Ion.with(view.getContext())
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_BY_ID + organisationId + Network.API_REQUEST_ORGANISATION_SHELTERS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<SheltersJson>(){})
                .setCallback(new FutureCallback<SheltersJson>() {
                    @Override
                    public void onCompleted(Exception error, SheltersJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                view.setDialog("Statut 400", result.getMessage());
                            else
                                view.updateRV(result.getResponse());
                        }
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void createShelter() {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.view_shelter_creation);
        final EditText name = ButterKnife.findById(dialog, R.id.name);
        final EditText address = ButterKnife.findById(dialog, R.id.address);
        final EditText zipcode = ButterKnife.findById(dialog, R.id.zipcode);
        final EditText city = ButterKnife.findById(dialog, R.id.city);
        final EditText places = ButterKnife.findById(dialog, R.id.places);
        final EditText phone = ButterKnife.findById(dialog, R.id.phone);
        final EditText mail = ButterKnife.findById(dialog, R.id.mail);
        final EditText description = ButterKnife.findById(dialog, R.id.description);
        Button cancel = ButterKnife.findById(dialog, R.id.btn_cancel);
        Button create = ButterKnife.findById(dialog, R.id.btn_create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                boolean error = false;
                String errorStr = "Champ obligatoire";

                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(button.getWindowToken(), 0);

                if (TextUtils.isEmpty(name.getText())) {
                    error = true;
                    name.setError(errorStr);
                }
                if (TextUtils.isEmpty(address.getText())) {
                    error = true;
                    address.setError(errorStr);
                }
                if (TextUtils.isEmpty(zipcode.getText())) {
                    error = true;
                    zipcode.setError(errorStr);
                }
                if (TextUtils.isEmpty(city.getText())) {
                    error = true;
                    city.setError(errorStr);
                }
                if (TextUtils.isEmpty(places.getText())) {
                    error = true;
                    places.setError(errorStr);
                }

                if (!error) {
                    JsonObject json = new JsonObject();
                    json.addProperty("assoc_id", organisationId);
                    json.addProperty("name", name.getText().toString());
                    json.addProperty("address", address.getText().toString());
                    json.addProperty("zipcode", Integer.valueOf(zipcode.getText().toString()));
                    json.addProperty("city", city.getText().toString());
                    json.addProperty("total_places", Integer.valueOf(places.getText().toString()));
                    json.addProperty("free_places", Integer.valueOf(places.getText().toString()));
                    if (!TextUtils.isEmpty(phone.getText()))
                        json.addProperty("phone", phone.getText().toString());
                    if (!TextUtils.isEmpty(mail.getText()))
                        json.addProperty("mail", mail.getText().toString());
                    if (!TextUtils.isEmpty(description.getText()))
                        json.addProperty("description", description.getText().toString());

                    Ion.with(view.getContext())
                            .load("POST", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_SHELTERS_CREATE)
                            .setHeader("access-token", user.getToken())
                            .setHeader("client", user.getClient())
                            .setHeader("uid", user.getUid())
                            .setJsonObjectBody(json)
                            .as(new TypeToken<ShelterJson>(){})
                            .setCallback(new FutureCallback<ShelterJson>() {
                                @Override
                                public void onCompleted(Exception error, ShelterJson result) {
                                    if (error == null) {
                                        if (result.getStatus() == Network.API_STATUS_ERROR)
                                            view.setDialog("Statut 400", result.getMessage());
                                        else {
                                            dialog.dismiss();
                                            dialog.cancel();

                                            new AlertDialog.Builder(view.getContext())
                                                    .setCancelable(true)
                                                    .setMessage("Le centre d'hebergement \"" + result.getResponse().getName() + "\" a bien été créé.")
                                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                        @Override
                                                        public void onCancel(DialogInterface dialogInterface) {
                                                            getShelters();
                                                            dialogInterface.dismiss();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                    else
                                        view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                                }
                            });

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void updateShelter(final Shelter shelter) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.view_shelter_modification);
        final EditText name = ButterKnife.findById(dialog, R.id.name);
        final EditText address = ButterKnife.findById(dialog, R.id.address);
        final EditText zipcode = ButterKnife.findById(dialog, R.id.zipcode);
        final EditText city = ButterKnife.findById(dialog, R.id.city);
        final EditText totalPlaces = ButterKnife.findById(dialog, R.id.places_total);
        final EditText freePlaces = ButterKnife.findById(dialog, R.id.places_free);
        final EditText phone = ButterKnife.findById(dialog, R.id.phone);
        final EditText mail = ButterKnife.findById(dialog, R.id.mail);
        final EditText description = ButterKnife.findById(dialog, R.id.description);
        Button cancel = ButterKnife.findById(dialog, R.id.btn_cancel);
        Button modify = ButterKnife.findById(dialog, R.id.btn_modify);

        name.setText(shelter.getName());
        address.setText(shelter.getAddress());
        zipcode.setText(String.valueOf(shelter.getZipcode()));
        city.setText(shelter.getCity());
        totalPlaces.setText(String.valueOf(shelter.getTotalPlaces()));
        freePlaces.setText(String.valueOf(shelter.getFreePlaces()));

        if (!TextUtils.isEmpty(shelter.getPhone()))
            phone.setText(shelter.getPhone());
        if (!TextUtils.isEmpty(shelter.getMail()))
            mail.setText(shelter.getMail());
        if (!TextUtils.isEmpty(shelter.getDescription()))
            description.setText(shelter.getDescription());

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                boolean error = false;
                String errorStr = "Champ obligatoire";

                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(button.getWindowToken(), 0);

                if (TextUtils.isEmpty(name.getText())) {
                    error = true;
                    name.setError(errorStr);
                }
                if (TextUtils.isEmpty(address.getText())) {
                    error = true;
                    address.setError(errorStr);
                }
                if (TextUtils.isEmpty(zipcode.getText())) {
                    error = true;
                    zipcode.setError(errorStr);
                }
                if (TextUtils.isEmpty(city.getText())) {
                    error = true;
                    city.setError(errorStr);
                }
                if (TextUtils.isEmpty(totalPlaces.getText())) {
                    error = true;
                    totalPlaces.setError(errorStr);
                }
                if (TextUtils.isEmpty(freePlaces.getText())) {
                    error = true;
                    freePlaces.setError(errorStr);
                }

                if (!TextUtils.isEmpty(totalPlaces.getText()) && !TextUtils.isEmpty(freePlaces.getText())) {
                    if (Integer.valueOf(totalPlaces.getText().toString()) < Integer.valueOf(freePlaces.getText().toString())) {
                        error = true;
                        freePlaces.setError("Le nombre de places disponibles ne peut pas être supérieures au nombre de places totales.");
                    }
                }

                if (!error) {
                    JsonObject json = new JsonObject();
                    json.addProperty("assoc_id", organisationId);
                    if (!TextUtils.isEmpty(name.getText()))
                        json.addProperty("name", name.getText().toString());
                    if (!TextUtils.isEmpty(address.getText()))
                        json.addProperty("address", address.getText().toString());
                    if (!TextUtils.isEmpty(zipcode.getText()))
                        json.addProperty("zipcode", Integer.valueOf(zipcode.getText().toString()));
                    if (!TextUtils.isEmpty(city.getText()))
                        json.addProperty("city", city.getText().toString());
                    if (!TextUtils.isEmpty(totalPlaces.getText()))
                        json.addProperty("total_places", Integer.valueOf(totalPlaces.getText().toString()));
                    if (!TextUtils.isEmpty(freePlaces.getText()))
                        json.addProperty("free_places", Integer.valueOf(freePlaces.getText().toString()));
                    if (!TextUtils.isEmpty(phone.getText()))
                        json.addProperty("phone", phone.getText().toString());
                    if (!TextUtils.isEmpty(mail.getText()))
                        json.addProperty("mail", mail.getText().toString());
                    if (!TextUtils.isEmpty(description.getText()))
                        json.addProperty("description", description.getText().toString());

                    Ion.with(view.getContext())
                            .load("PUT", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_SHELTERS_UPDATE + shelter.getId())
                            .setHeader("access-token", user.getToken())
                            .setHeader("client", user.getClient())
                            .setHeader("uid", user.getUid())
                            .setJsonObjectBody(json)
                            .as(new TypeToken<ShelterJson>() {
                            })
                            .setCallback(new FutureCallback<ShelterJson>() {
                                @Override
                                public void onCompleted(Exception error, ShelterJson result) {
                                    if (error == null) {
                                        if (result.getStatus() == Network.API_STATUS_ERROR)
                                            view.setDialog("Statut 400", result.getMessage());
                                        else {
                                            dialog.dismiss();
                                            dialog.cancel();

                                            new AlertDialog.Builder(view.getContext())
                                                    .setCancelable(true)
                                                    .setMessage("Le centre d'hebergement \"" + result.getResponse().getName() + "\" a bien été modifié.")
                                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                        @Override
                                                        public void onCancel(DialogInterface dialogInterface) {
                                                            getShelters();
                                                            dialogInterface.dismiss();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    } else
                                        view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                                }
                            });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void deleteShelter(final Integer shelterId, final String shelterName) {
        new AlertDialog.Builder(view.getContext())
                .setCancelable(false)
                .setMessage("Êtes-vous sûr de vouloir supprimer le centre d'hébergement \"" + shelterName + "\".")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JsonObject json = new JsonObject();
                        json.addProperty("assoc_id", organisationId);

                        Ion.with(view.getContext())
                                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_SHELTERS_DELETE + shelterId)
                                .setHeader("access-token", user.getToken())
                                .setHeader("client", user.getClient())
                                .setHeader("uid", user.getUid())
                                .setJsonObjectBody(json)
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception error, JsonObject result) {
                                        if (error == null) {
//                                            if (result.getStatus() == Network.API_STATUS_ERROR)
//                                                view.setDialog("Statut 400", result.getMessage());
//                                            else {
                                                new AlertDialog.Builder(view.getContext())
                                                        .setMessage("Le centre d'hebergement \"" + shelterName + "\" a bien été supprimé.")
                                                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                            @Override
                                                            public void onCancel(DialogInterface dialogInterface) {
                                                                getShelters();
                                                                dialogInterface.dismiss();
                                                            }
                                                        })
                                                        .show();
//                                            }
                                        }
                                        else
                                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                                    }
                                });

                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public boolean isOwner() {
        return owner;
    }
}
