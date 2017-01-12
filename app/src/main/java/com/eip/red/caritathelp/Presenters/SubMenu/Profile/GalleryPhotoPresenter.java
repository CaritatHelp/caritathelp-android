package com.eip.red.caritathelp.Presenters.SubMenu.Profile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Picture.Picture;
import com.eip.red.caritathelp.Models.Picture.PictureJson;
import com.eip.red.caritathelp.Models.Picture.PicturesJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Views.SubMenu.Profile.galleryPhoto.GalleryPhoto;
import com.eip.red.caritathelp.Views.SubMenu.Profile.galleryPhoto.GalleryPhotoView;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by pierr on 08/12/2016.
 */

public class GalleryPhotoPresenter implements GalleryPhoto.Presenter {

    public enum GALLERY_PHOTO_OWNER {
        USER,
        ORGANISATION,
        EVENT,
        SHELTER
    }

    public static final int RESULT_CAPTURE_IMAGE = 0;
    public static final int RESULT_LOAD_IMAGE = 1;

    private GalleryPhotoView view;
    private User user;
    private int id;
    private GALLERY_PHOTO_OWNER type;
    private boolean owner;
    private AlertDialog dialog;

    public GalleryPhotoPresenter(final GalleryPhotoView view, User user, int id, GALLERY_PHOTO_OWNER type, boolean owner) {
        this.view = view;
        this.user = user;
        this.id = id;
        this.type = type;
        this.owner = owner;

        CharSequence[] items = {"Prendre une photo", "Accéder à mes photos"};
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Ajouter une photo")
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
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            view.startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                        }
                    }
                });
        dialog = builder.create();
    }

    @Override
    public void getPhotos() {
        view.showProgress();

        String url;
        if (type == GALLERY_PHOTO_OWNER.USER)
            url = Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS_2 + id + Network.API_REQUEST_PICTURES;
        else
            url = Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_BY_ID + id + Network.API_REQUEST_PICTURES;

        Ion.with(view.getContext())
                .load("GET", url)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<PicturesJson>() {})
                .setCallback(new FutureCallback<PicturesJson>() {
                    @Override
                    public void onCompleted(Exception error, PicturesJson result) {
                        view.hideProgress();
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
    public void addPhoto() {
        Dexter.checkPermissions(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted())
                    dialog.show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void uploadPhoto(Intent data, int code) {
        view.showProgress();

        Bitmap bitmap = null;
        if (code == RESULT_CAPTURE_IMAGE) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
        }
        else {
            Uri targetUri = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(view.getContext().getContentResolver().openInputStream(targetUri));
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (bitmap != null) {
            // Encoded the bitmap in base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();
            String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

            // Upload the image on the server
//            interactor.uploadProfileImg(encodedImage, "filename.jpg", "original_filename.jpg", -1, -1, "true", this);

            JsonObject json = new JsonObject();
            json.addProperty("file", encodedImage);
            json.addProperty("filename", "photo" + System.currentTimeMillis() + ".jpg");
            json.addProperty("original_filename", "original_photo" + System.currentTimeMillis() + ".jpg");
            json.addProperty("is_main", false);

            switch (type) {
                case ORGANISATION:
                    json.addProperty("assoc_id", id);
                    break;
                case EVENT:
                    json.addProperty("event_id", id);
                    break;
                case SHELTER:
                    json.addProperty("shelter_id", id);
                    break;
            }

            Ion.with(view)
                    .load("POST", Network.API_LOCATION_2 + Network.API_REQUEST_PICTURES)
                    .setHeader("access-token", user.getToken())
                    .setHeader("client", user.getClient())
                    .setHeader("uid", user.getUid())
                    .setJsonObjectBody(json)
                    .as(new TypeToken<PictureJson>() {})
                    .setCallback(new FutureCallback<PictureJson>() {
                        @Override
                        public void onCompleted(Exception error, PictureJson result) {
                            view.hideProgress();
                            if (error == null) {
                                if (result.getStatus() == Network.API_STATUS_ERROR)
                                    view.setDialog("Statut 400", result.getMessage());
                                else {
                                    if (type == GALLERY_PHOTO_OWNER.USER)
                                        user.setThumb_path(result.getResponse().getPicturePath().getThumb().getUrl());
                                    view.updateRv(result.getResponse(), false);
                                }
                            }
                            else
                                view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                        }
                    });
        }
    }

    @Override
    public void upgradePhoto(final Picture picture) {
        view.showProgress();
        Ion.with(view)
                .load("PUT", Network.API_LOCATION_2 + Network.API_REQUEST_PICTURES_DELETE + picture.getId())
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<JsonObject>() {})
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        view.hideProgress();
                        if (error == null) {
                            if (type == GALLERY_PHOTO_OWNER.USER)
                                user.setThumb_path(picture.getPicturePath().getThumb().getUrl());
                            view.updateRvMainPhoto(picture);

                            new AlertDialog.Builder(view.getContext())
                                    .setCancelable(true)
                                    .setMessage("Votre photo de profil a bien été modifié.")
                                    .show();
                        }
                        else
                            view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    @Override
    public void deletePhoto(final Picture picture) {
        if (!picture.getMain()) {
            view.showProgress();
            Ion.with(view)
                    .load("DELETE", Network.API_LOCATION_2 + Network.API_REQUEST_PICTURES_DELETE + picture.getId())
                    .setHeader("access-token", user.getToken())
                    .setHeader("client", user.getClient())
                    .setHeader("uid", user.getUid())
                    .as(new TypeToken<JsonObject>() {})
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception error, JsonObject result) {
                            view.hideProgress();
                            if (error == null)
                                view.updateRv(picture, true);
                            else
                                view.setDialog("Problème de connection", "Vérifiez votre connexion Internet");
                        }
                    });
        }
        else {
            new AlertDialog.Builder(view.getContext())
                    .setCancelable(true)
                    .setMessage("Tu ne peux pas supprimer une photo utilisée en tant que photo de profil.")
                    .show();
        }



    }

    @Override
    public boolean isOwner() {
        return owner;
    }
}
