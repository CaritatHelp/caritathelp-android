package com.eip.red.caritathelp.Presenters.Home.Post;

import android.content.Context;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.News.NewsJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 07/09/2016.
 */

public class PostInteractor {

    private Context context;
    private User    user;
    private String  groupType;
    private int     groupId;
    private String  groupName;
    private String  rights;

    public PostInteractor(Context context, User user, String groupType, int groupId, String groupName, String rights) {
        this.context = context;
        this.user = user;
        this.groupType = groupType;
        this.groupId = groupId;
        this.groupName = groupName;
        this.rights = rights;
    }

    public void postNews(final IOnPostFinishedListener listener, String content, boolean asGroup) {
        JsonObject json = new JsonObject();
        json.addProperty("content", content);
        json.addProperty("group_id", groupId);
        json.addProperty("group_type", groupType);
        json.addProperty("news_type", News.TYPE_STATUS);
        json.addProperty("as_group", asGroup);
//        json.addProperty("title", "");
//        json.addProperty("private", false);

        Ion.with(context)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_NEWS_WALL_MESSAGE)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .as(new TypeToken<NewsJson>(){})
                .setCallback(new FutureCallback<NewsJson>() {
                    @Override
                    public void onCompleted(Exception error, NewsJson result) {
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                listener.onDialog("Statut 400", result.getMessage());
                            else
                                listener.onSuccessPostNews();
                        }
                        else
                            listener.onDialog("Problème de connection", context.getString(R.string.connection_problem));
                    }
                });
    }

    public User getUser() {
        return user;
    }

    public String getGroupType() {
        return groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getRights() {
        return rights;
    }
}
