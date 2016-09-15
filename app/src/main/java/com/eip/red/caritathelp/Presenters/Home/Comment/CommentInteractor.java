package com.eip.red.caritathelp.Presenters.Home.Comment;

import android.content.Context;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.comment.CommentJson;
import com.eip.red.caritathelp.Models.News.comment.CommentsJson;
import com.eip.red.caritathelp.R;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 06/09/2016.
 */

public class CommentInteractor {

    private Context context;
    private String  token;
    private int     newsId;

    public CommentInteractor(Context context, String token, int newsId) {
        this.context = context;
        this.token = token;
        this.newsId = newsId;
    }

    public void getComments(final IOnCommentFinishedListener listener) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);

        Ion.with(context)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_NEWS + "/" + newsId + "/" + Network.API_REQUEST_NEWS_COMMENTS)
                .setJsonObjectBody(json)
                .as(new TypeToken<CommentsJson>(){})
                .setCallback(new FutureCallback<CommentsJson>() {
                    @Override
                    public void onCompleted(Exception error, CommentsJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessGetComments(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }

    public void sendComment(final IOnCommentFinishedListener listener, final String content) {
        JsonObject json = new JsonObject();

        json.addProperty("token", token);
        json.addProperty("content", content);
        json.addProperty("new_id", newsId);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_POST_COMMENTS)
                .setJsonObjectBody(json)
                .as(new TypeToken<CommentJson>(){})
                .setCallback(new FutureCallback<CommentJson>() {
                    @Override
                    public void onCompleted(Exception error, CommentJson result) {
                        if (error == null) {
                            // Status == 400 == error
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessSendComment(result.getResponse());
                        }
                        else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });

    }

}
