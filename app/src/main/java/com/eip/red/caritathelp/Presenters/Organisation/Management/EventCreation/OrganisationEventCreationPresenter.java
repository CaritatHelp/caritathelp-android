package com.eip.red.caritathelp.Presenters.Organisation.Management.EventCreation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.Organisation.Management.OrganisationEventCreation.OrganisationEventCreationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventCreationPresenter implements IOrganisationEventCreationPresenter, IOnOrganisationEventCreationFinishedListener {

    static final public  int     RESULT_LOAD_IMAGE = 1;
    static final public  int     RESULT_CAPTURE_IMAGE = 2;

    private OrganisationEventCreationView       view;
    private OrganisationEventCreationInteractor interactor;
    private AlertDialog dialog;

    public OrganisationEventCreationPresenter(final OrganisationEventCreationView view, User user, int organisationId) {
        this.view = view;
        interactor = new OrganisationEventCreationInteractor(view.getActivity().getApplicationContext(), user, organisationId);

        // Init Dialog
        CharSequence[] items = {"Prendre une photo", "Accéder à mes photos"};
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setCancelable(true)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                                view.startActivityForResult(intent, RESULT_CAPTURE_IMAGE);
                            }
                        }
                        else {
                            // Create intent to Open Image applications like Gallery, Google Photos
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            // Start the Intent
                            view.startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                        }
                    }
                });
        dialog = builder.create();
    }

    @Override
    public void uploadEventImg(Intent data, int code) {
        Bitmap bitmap = null;
        if (code == RESULT_CAPTURE_IMAGE) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
        }
        else {
            Uri targetUri = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(view.getContext().getContentResolver().openInputStream(targetUri));
                bitmap = Bitmap.createScaledBitmap(bitmap, 135, 240, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (bitmap != null) {
            view.getThumbnail().setImageBitmap(bitmap);
            view.getThumbnail().setVisibility(View.VISIBLE);

            // Encoded the bitmap in base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();
            String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

            interactor.setEncodedImg(encodedImage);
        }
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.btn_photo:
                dialog.show();
                break;
            case R.id.begin_date:
                view.getDateBeginDialog().show();
                break;
            case R.id.begin_time:
                view.getTimeBeginDialog().show();
                break;
            case R.id.end_date:
                view.getDateEndDialog().show();
                break;
            case R.id.end_time:
                view.getTimeEndDialog().show();
                break;
            case R.id.btn_create:
                view.showProgress();
                interactor.createEvent(this, view.getData());
                break;
        }
    }

    @Override
    public void onTitleError(String error) {
        view.hideProgress();
        view.setTitleError(error);
    }

    @Override
    public void onPhotoError(String error) {
        view.hideProgress();
        view.setPhotoError(error);
    }

    @Override
    public void onBeginDateError(String error) {
        view.hideProgress();
        view.setBeginDateError(error);
    }

    @Override
    public void onEndDateError(String error) {
        view.hideProgress();
        view.setEndDateError(error);
    }

    @Override
    public void onLocationError(String error) {
        view.hideProgress();
        view.setLocationError(error);
    }

    @Override
    public void onDescriptionError(String error) {
        view.hideProgress();
        view.setDescriptionError(error);
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccess(int eventId, String eventTitle) {
        /* NO ERROR, I JUST NEEDED A DIALOG TO DISPLAY THE EVENT CREATION SUCCESS */
        view.setDialogError("Création réussie", "Bienvenue sur la page d'accueil de l'événement " + eventTitle);

        /* Go To Event Page */
        Tools.replaceView(view, OrganisationEventView.newInstance(eventId, eventTitle), Animation.FADE_IN_OUT, false);
    }
}
