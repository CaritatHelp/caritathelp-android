package com.eip.red.caritathelp.Views.SubMenu.Profile.galleryPhoto;

import android.content.Intent;
import android.graphics.Bitmap;

import com.eip.red.caritathelp.Models.Picture.Picture;

import java.util.List;

/**
 * Created by pierr on 08/12/2016.
 */

public interface GalleryPhoto {

    interface View {
        void showProgress();

        void hideProgress();

        void setDialog(String title, String msg);

        void updateRV(List<Picture> pictures);

        void updateRv(Picture picture, boolean remove);
    }

    interface Presenter {
        void getPhotos();

        void addPhoto();

        void uploadPhoto(Intent data, int code);

        void upgradePhoto(Picture picture);

        void deletePhoto(Picture picture);

        boolean isOwner();
    }

}
