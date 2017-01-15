package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management;

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
import android.widget.EditText;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.modification.ModificationView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

/**
 * Created by pierr on 14/04/2016.
 */

public class ModificationPresenter implements IModificationPresenter, IModificationFinishedListener {

    static final public  int     RESULT_LOAD_IMAGE = 1;
    static final public  int     RESULT_CAPTURE_IMAGE = 2;

    private ModificationView view;
    private ModificationInteractor interactor;
    private AlertDialog dialog;

    public ModificationPresenter(final ModificationView view, User user, int eventId) {
        this.view = view;
        interactor = new ModificationInteractor(view.getContext(), user, eventId);

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
            case R.id.btn_save:
                view.showProgress();
                interactor.saveEventModifications(this, view.getProgressBar(), view.getData());
                break;
        }
    }

    @Override
    public void getEvent() {
        view.showProgress();
        interactor.getEvent(view.getProgressBar(), this);
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
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetEvent(Event event) {
        // Set View Data
        setViewData(event);
        view.hideProgress();
    }

    @Override
    public void onSuccessSaveEventModifications(Event event) {
        // Set View Data
        setViewData(event);

        // Hide Progress Bar
        view.hideProgress();

        // Display Dialog Message
        new AlertDialog.Builder(view.getContext()).setMessage("Les modifications ont bien été prises en compte.").show();
    }

    private void setViewData(Event event) {
        EditText    title = view.getTitle();
        EditText    location = view.getLocation();
        EditText    description = view.getDescription();

        title.getText().clear();
        title.setHint(event.getTitle());
        if (location != null) {
            location.getText().clear();
            location.setHint(event.getPlace());
        }
        description.getText().clear();
        description.setHint(event.getDescription());

        if (event.getThumb_path() != null) {
            view.getThumbnail().setVisibility(View.VISIBLE);
            Network.loadImage(view.getContext(), view.getThumbnail(), Network.API_LOCATION_2 + event.getThumb_path(), R.drawable.solidarite);
        }
        else
            view.getThumbnail().setVisibility(View.GONE);
    }
}
