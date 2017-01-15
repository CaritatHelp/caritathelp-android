package com.eip.red.caritathelp.Presenters.SubMenu.Profile;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Models.Friends.FriendsJson;
import com.eip.red.caritathelp.Models.Friendship;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.News.NewsListJson;
import com.eip.red.caritathelp.Models.Profile.MainPictureJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.User.UserJson;
import com.eip.red.caritathelp.Presenters.Home.IOnHomeFinishedListener;
import com.eip.red.caritathelp.R;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

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
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid   ())
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

    public void getData(final IOnProfileFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS + userId)
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
                .as(new TypeToken<UserJson>() {})
                .setCallback(new FutureCallback<UserJson>() {
                    @Override
                    public void onCompleted(Exception error, UserJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else {
                                user = result.getResponse();
                                getNews(listener, result.getResponse());
                            }
                        }
                        else
                            listener.onDialog("Problème de connection", "Vérifiez votre connexion Internet");
                    }
                });
    }

    public void getNews(final IOnProfileFinishedListener listener, final User user) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS_2 + userId + Network.API_REQUEST_NEWS)
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
                .as(new TypeToken<NewsListJson>(){})
                .setCallback(new FutureCallback<NewsListJson>() {
                    @Override
                    public void onCompleted(Exception error, NewsListJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetData(user, result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }

    public void getNews(final IOnProfileFinishedListener listener) {
        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEERS_2 + userId + Network.API_REQUEST_NEWS)
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
                .as(new TypeToken<NewsListJson>(){})
                .setCallback(new FutureCallback<NewsListJson>() {
                    @Override
                    public void onCompleted(Exception error, NewsListJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetNews(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }


    public void addFriend(final IOnProfileFinishedListener listener, ProgressBar progressBar) {
        JsonObject json = new JsonObject();
        json.addProperty("volunteer_id", String.valueOf(userId));

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_ADD )
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
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
        json.addProperty("id", String.valueOf(userId));

        Ion.with(context)
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_REMOVE )
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
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
        json.addProperty("notif_id", String.valueOf(user.getNotif_id()));
        json.addProperty("acceptance", acceptance);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_FRIENDSHIP_REPLY )
                .setHeader("access-token", mainUser.getToken())
                .setHeader("client", mainUser.getClient())
                .setHeader("uid", mainUser.getUid())
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
        return mainUser.getId() == userId;
    }

    public int getUserId() {
        return userId;
    }

    public User getMainUser() {
        return mainUser;
    }

    public User getUser() {
        return user;
    }
}
