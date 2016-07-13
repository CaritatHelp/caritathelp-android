package com.eip.red.caritathelp.Presenters.SubMenu.Profile;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Friends.FriendsJson;
import com.eip.red.caritathelp.Models.Friendship;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Profile.MainPictureJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.User.UserJson;
import com.eip.red.caritathelp.R;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 11/05/2016.
 */

public class ProfileInteractor {

    private Context context;
    private User    mainUser;
    private int     userId;
    private User    user;

    public ProfileInteractor(Context context, User mainUser, int userId) {
        this.context = context;
        this.mainUser = mainUser;
        this.userId = userId;
    }

    public void uploadProfileImg(String file, String filename, String originFilename, int assocId, int eventId, String isMain, final IOnProfileFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());
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
                .setJsonObjectBody(json)
                .as(new TypeToken<MainPictureJson>() {})
                .setCallback(new FutureCallback<MainPictureJson>() {
                    @Override
                    public void onCompleted(Exception error, MainPictureJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else {
                                // Set User Model
                                mainUser.setThumb_path(result.getResponse().getPicture_path().getThumb().getUrl());
                                listener.onSuccessUploadProfileImg();
                            }
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void getProfile(final IOnProfileFinishedListener listener, final ImageView imageView, final ProgressBar progressBar) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS + userId)
                .progressBar(progressBar)
                .setJsonObjectBody(json)
                .as(new TypeToken<UserJson>() {})
                .setCallback(new FutureCallback<UserJson>() {
                    @Override
                    public void onCompleted(Exception error, UserJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else {
                                // Set User model
                                user = result.getResponse();

                                // Set User Profile Image
                                Network.loadImage(context, imageView, Network.API_LOCATION_2 + user.getThumb_path(), R.drawable.profile_example);

                                // Go to presenter
                                listener.onSuccessGetProfile(user);
                            }
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void addFriend(final IOnProfileFinishedListener listener, ProgressBar progressBar) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());
        json.addProperty("volunteer_id", String.valueOf(userId));

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_ADD )
                .progressBar(progressBar)
                .setJsonObjectBody(json)
                .as(new TypeToken<Friendship>(){})
                .setCallback(new FutureCallback<Friendship>() {
                    @Override
                    public void onCompleted(Exception error, Friendship result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessAddFriend();
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void removeFriend(final IOnProfileFinishedListener listener, ProgressBar progressBar) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());
        json.addProperty("id", String.valueOf(userId));

        Ion.with(context)
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_REMOVE )
                .progressBar(progressBar)
                .setJsonObjectBody(json)
                .as(new TypeToken<Friendship>(){})
                .setCallback(new FutureCallback<Friendship>() {
                    @Override
                    public void onCompleted(Exception error, Friendship result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessRemoveFriend();
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void replyFriend(final IOnProfileFinishedListener listener, ProgressBar progressBar, final String acceptance) {
        JsonObject json = new JsonObject();

        json.addProperty("token", mainUser.getToken());
        json.addProperty("notif_id", String.valueOf(user.getNotif_id()));
        json.addProperty("acceptance", acceptance);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_REPLY )
                .progressBar(progressBar)
                .setJsonObjectBody(json)
                .as(new TypeToken<Friendship>(){})
                .setCallback(new FutureCallback<Friendship>() {
                    @Override
                    public void onCompleted(Exception error, Friendship result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessReplyFriend(acceptance);
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public boolean isMainUser() {
        return (mainUser.getId() == userId);
    }

    public int getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
    }
}
