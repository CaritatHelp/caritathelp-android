package com.eip.red.caritathelp.Presenters.SubMenu.Profile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Home.Comment.CommentView;
import com.eip.red.caritathelp.Views.Home.Post.PostView;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.SubMenu.MyFriends.MyFriendsView;
import com.eip.red.caritathelp.Views.SubMenu.MyEvents.MyEventsView;
import com.eip.red.caritathelp.Views.SubMenu.MyOrganisations.MyOrganisationsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.Friends.FriendsView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.galleryPhoto.GalleryPhotoView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by pierr on 11/05/2016.
 */

public class ProfilePresenter implements IProfilePresenter, IOnProfileFinishedListener {

    static final public int RESULT_LOAD_IMAGE = 1;
    static final public int RESULT_CAPTURE_IMAGE = 2;

    private ProfileView view;
    private ProfileInteractor interactor;

    private AlertDialog dialog;

    public ProfilePresenter(final ProfileView view, User user, int id) {
        this.view = view;
        interactor = new ProfileInteractor(view.getContext(), user, id);

        // Init Dialog
        CharSequence[] items = {"Prendre une photo", "Accéder à mes photos"};
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Changer son image de profil")
                .setCancelable(true)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                                view.startActivityForResult(intent, RESULT_CAPTURE_IMAGE);
                            }
                        } else {
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
            case R.id.image:
                Tools.replaceView(view, GalleryPhotoView.newInstance(interactor.getUserId(), GalleryPhotoPresenter.GALLERY_PHOTO_OWNER.USER, interactor.isMainUser()), Animation.FADE_IN_OUT, false);

                // Check if user == main user
                // If true -> modify his profile img
//                if (interactor.isMainUser()) {
//                    Dexter.checkPermissions(new MultiplePermissionsListener() {
//                        @Override
//                        public void onPermissionsChecked(MultiplePermissionsReport report) {
//                            if (report.areAllPermissionsGranted())
//                                dialog.show();
//                        }
//
//                        @Override
//                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                            token.continuePermissionRequest();
//                        }
//                    }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                }
                break;
            case R.id.btn_friendship_friend:
                view.showProgress();
                interactor.removeFriend(this, view.getProgressBar());
                break;
            case R.id.btn_friendship_none:
                view.showProgress();
                interactor.addFriend(this, view.getProgressBar());
                break;
            case R.id.btn_friendship_confirm:
                view.showProgress();
                interactor.replyFriend(this, view.getProgressBar(), "true");
                break;
            case R.id.btn_friendship_remove:
                view.showProgress();
                interactor.replyFriend(this, view.getProgressBar(), "false");
                break;
            case R.id.btn_send_message:
                break;
            case R.id.btn_friends:
                if (interactor.isMainUser())
                    Tools.replaceView(view, MyFriendsView.newInstance(interactor.getUserId()), Animation.FADE_IN_OUT, false);
                else
                    Tools.replaceView(view, FriendsView.newInstance(interactor.getUserId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_organisations:
                Tools.replaceView(view, MyOrganisationsView.newInstance(interactor.getUserId(), interactor.isMainUser()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_events:
                Tools.replaceView(view, MyEventsView.newInstance(interactor.getUserId(), interactor.isMainUser()), Animation.FADE_IN_OUT, false);
                break;
        }
    }

    @Override
    public void getData() {
        view.showProgress();
        interactor.getData(this);
    }

    @Override
    public void getNews(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();
        interactor.getNews(this);
    }

    @Override
    public void uploadProfileImg(ImageView imageView, Intent data, int code) {
        view.showProgress();

        Bitmap bitmap = null;
        if (code == RESULT_CAPTURE_IMAGE) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
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
            view.getProfileImg().setImageBitmap(bitmap);

            // Encoded the bitmap in base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();
            String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

            // Upload the image on the server
            interactor.uploadProfileImg(encodedImage, "filename.jpg", "original_filename.jpg", -1, -1, "true", this);
        }

    }

    @Override
    public void goToProfileView() {
        Tools.replaceView(view, ProfileView.newInstance(interactor.getMainUser().getId()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToPostView() {
        Tools.replaceView(view, PostView.newInstance(News.GROUP_TYPE_VOLUNTEER, interactor.getMainUser().getId(), null, null), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToProfileView(News news) {
        if (news.isAs_group())
            Tools.replaceView(view, OrganisationView.newInstance(news.getGroup_id(),news.getGroup_name()), Animation.FADE_IN_OUT, false);
        else
            Tools.replaceView(view, ProfileView.newInstance(news.getVolunteer_id()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToCommentView(News news) {
        Tools.replaceView(view, CommentView.newInstance(news.getId()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessUploadProfileImg() {
        view.hideProgress();

        // Dialog Msg
        new AlertDialog.Builder(view.getContext())
                .setMessage("Votre image de profil a bien été modifié.")
                .setCancelable(true)
                .show();
    }

    @Override
    public void onSuccessGetData(User user, List<News> newsList) {
        String  name = user.getFirstname() + " " + user.getLastname();
        String  friendship = user.getFriendship();

        view.getName().setText(name);

        if (friendship != null) {
            switch (friendship) {
                case User.FRIENDSHIP_YOURSELF:
                    view.getMessageBtn().setVisibility(View.INVISIBLE);
                    break;
                case User.FRIENDSHIP_NONE:
                    view.getFriendshipBtn(User.FRIENDSHIP_NONE).setVisibility(View.VISIBLE);
                    view.getMessageBtn().setVisibility(View.VISIBLE);
                    break;
                case User.FRIENDSHIP_FRIEND:
                    view.getFriendshipBtn(User.FRIENDSHIP_FRIEND).setVisibility(View.VISIBLE);
                    view.getMessageBtn().setVisibility(View.VISIBLE);
                    break;
                case User.FRIENDSHIP_INVITATION_SENT:
                    view.getFriendshipBtn(User.FRIENDSHIP_INVITATION_SENT).setVisibility(View.VISIBLE);
                    view.getMessageBtn().setVisibility(View.VISIBLE);
                    break;
                case User.FRIENDSHIP_INVITATIONS_RECEIVED:
                    view.getFriendshipBtn(User.FRIENDSHIP_INVITATIONS_RECEIVED_CONFIRM).setVisibility(View.VISIBLE);
                    view.getFriendshipBtn(User.FRIENDSHIP_INVITATIONS_RECEIVED_REMOVE).setVisibility(View.VISIBLE);
                    view.getMessageBtn().setVisibility(View.VISIBLE);
                    break;
            }
        }
        else {
            view.getFriendshipBtn(User.FRIENDSHIP_NONE).setVisibility(View.VISIBLE);
            view.getMessageBtn().setVisibility(View.VISIBLE);
        }

        Network.loadImage(view.getContext(), view.getProfileImg(), Network.API_LOCATION_2 + user.getThumb_path(), R.drawable.profile_example);
        Network.loadImage(view.getContext(), view.getImageUser(), Network.API_LOCATION_2 + interactor.getMainUser().getThumb_path(), R.drawable.profile_example);
        view.updateRecyclerViewData(newsList);
        view.hideProgress();
    }

    @Override
    public void onSuccessGetNews(List<News> newsList) {
        view.updateRecyclerViewData(newsList);
        view.hideProgress();
    }

    @Override
    public void onSuccessAddFriend() {
        // Set Friendship Btn Visibility
        view.getFriendshipBtn(User.FRIENDSHIP_NONE).setVisibility(View.GONE);
        view.getFriendshipBtn(User.FRIENDSHIP_INVITATION_SENT).setVisibility(View.VISIBLE);

        // Set ProgressBar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessRemoveFriend() {
        // Set Friendship Btn Visibility
        view.getFriendshipBtn(User.FRIENDSHIP_NONE).setVisibility(View.VISIBLE);
        view.getFriendshipBtn(User.FRIENDSHIP_FRIEND).setVisibility(View.GONE);

        // Set ProgressBar Visibility
        view.hideProgress();
    }

    @Override
    public void onSuccessReplyFriend(String acceptance) {
        // Set Friendship Btn Visibility
        view.getFriendshipBtn(User.FRIENDSHIP_INVITATIONS_RECEIVED_CONFIRM).setVisibility(View.GONE);
        view.getFriendshipBtn(User.FRIENDSHIP_INVITATIONS_RECEIVED_REMOVE).setVisibility(View.GONE);

        if (acceptance.equals("true"))
            view.getFriendshipBtn(User.FRIENDSHIP_FRIEND).setVisibility(View.VISIBLE);
        else
            view.getFriendshipBtn(User.FRIENDSHIP_NONE).setVisibility(View.VISIBLE);

        // Set ProgressBar Visibility
        view.hideProgress();
    }

}