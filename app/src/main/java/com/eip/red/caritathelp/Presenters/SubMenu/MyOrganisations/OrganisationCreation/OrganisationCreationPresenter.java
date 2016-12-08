package com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations.OrganisationCreation;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.SubMenu.MyOrganisations.OrganisationCreation.OrganisationCreationView;

import java.io.ByteArrayOutputStream;

/**
 * Created by pierr on 24/02/2016.
 */

public class OrganisationCreationPresenter implements IOrganisationCreationPresenter, IOnOrganisationCreationsFinishedListener {

    static final public  int     RESULT_LOAD_IMAGE = 1;
    static final public  int     RESULT_CAPTURE_IMAGE = 2;

    private OrganisationCreationView        view;
    private OrganisationCreationInteractor  interactor;

    private AlertDialog     dialog;

    public OrganisationCreationPresenter(final OrganisationCreationView view, User user) {
        this.view = view;
        interactor = new OrganisationCreationInteractor(view.getContext(), user);

        // Init Dialog
        CharSequence[] items = {"Prendre une photo", "Accéder à mes photos"};
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setCancelable(true)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
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
    public void uploadProfileImg(ImageView imageView, Intent data) {
        // Set the image
        // Get the Image from data
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        // Get the cursor
        Cursor cursor = view.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        String picturePath = null;

        // Move to first row
        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        }

        // Set the Image in ImageView after decoding the String
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        imageView.setImageBitmap(bm);
        imageView.setVisibility(View.VISIBLE);

        // Upload the image on the server
        // Encoded the bitmap in base64

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        interactor.setEncodedImg(encodedImage);
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.btn_photo:
                dialog.show();
            break;

            case R.id.btn_create:
                String  name = view.getName().getText().toString();
                String  theme = view.getTheme().getText().toString();
                String  city = view.getCity().getText().toString();
                String  description = view.getDescription().getText().toString();

                view.showProgress();
                interactor.createOrganisation(name, theme, city, description, this);
                break;
        }
    }

    @Override
    public void onNameError(String error) {
        view.hideProgress();
        view.setNameError(error);
    }

    @Override
    public void onThemeError(String error) {
        view.hideProgress();
        view.setThemeError(error);
    }

    @Override
    public void onCityError(String error) {
        view.hideProgress();
        view.setCityError(error);
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
    public void onSuccess(Organisation organisation) {
        view.hideProgress();
        view.navigateToOrganisationView(organisation.getName());
        Tools.replaceView(view, OrganisationView.newInstance(organisation.getId(), organisation.getName()), Animation.FADE_IN_OUT, false);
    }
}
