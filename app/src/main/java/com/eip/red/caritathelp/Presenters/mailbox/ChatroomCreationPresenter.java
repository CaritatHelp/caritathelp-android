package com.eip.red.caritathelp.Presenters.mailbox;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Search.Search;
import com.eip.red.caritathelp.Models.Search.SearchJson;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.mailbox.ChatroomJson;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.MailBox.MailBoxView;
import com.eip.red.caritathelp.Views.MailBox.chatroom.ChatroomView;
import com.eip.red.caritathelp.Views.MailBox.chatroomCreation.ChatroomCreation;
import com.eip.red.caritathelp.Views.MailBox.chatroomCreation.ChatroomCreationView;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pierr on 13/10/2016.
 */

public class ChatroomCreationPresenter implements ChatroomCreation.Presenter {

    private ChatroomCreationView view;
    private User user;
    private HashMap<String, Search> peopleAdded;
    private List<String> tags;
    private boolean search;

    public ChatroomCreationPresenter(ChatroomCreationView view, User user) {
        this.view = view;
        this.user = user;
        peopleAdded = new HashMap<>();
        tags = new ArrayList<>();
        search = true;
    }

    @Override
    public void search(String search) {
        view.showProgress();

        if (tags.size() > 0) {
            int size = 0;

            for (String tag : tags) {
                size += tag.length();
            }

            size += tags.size();
            if (size <= search.length())
                search = search.substring(size, search.length());
        }

        JsonObject json = new JsonObject();
        json.addProperty("research", search);

        Ion.with(view)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_SEARCH)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setJsonObjectBody(json)
                .as(new TypeToken<SearchJson>() {
                })
                .setCallback(new FutureCallback<SearchJson>() {
                    @Override
                    public void onCompleted(Exception error, SearchJson result) {
                        view.hideProgress();
                        if (error == null) {
                            if (result.getStatus() != Network.API_STATUS_ERROR)
                                view.updateRVData(result.getResponse());
                        } else
                            view.setDialog("Problème de connection", view.getString(R.string.connection_problem));
                    }
                });
    }

    @Override
    public void addTag(Search search) {
        if (!peopleAdded.containsKey(search.getName())) {
            this.search = false;
            peopleAdded.put(search.getName(), search);
            tags.add(search.getName());
            view.addTag(tags);
        }
    }

    @Override
    public void setTags(List<String> tags) {
        HashMap<String, Search> peopleAddedUpdate = new HashMap<>();

        for (String tag : tags) {
            peopleAddedUpdate.put(tag, peopleAdded.get(tag));
        }

        peopleAdded.clear();
        peopleAdded.putAll(peopleAddedUpdate);
        this.tags.clear();
        this.tags.addAll(tags);
        this.search = true;
    }

    @Override
    public void goToChatRoomView() {
        List<String> volunteersIds = new ArrayList<>();
        List<String> chatroomName = new ArrayList<>();
        String chatroomNameStr = "";

        for (Search person : peopleAdded.values()) {
            volunteersIds.add(String.valueOf(person.getId()));
        }

        for (int i = 0; i < tags.size(); i++) {
            if (i == (tags.size() - 1))
                chatroomNameStr += tags.get(i);
            else
                chatroomNameStr += tags.get(i) + ", ";
        }

        chatroomName.add(chatroomNameStr);

        Map<String, List<String>> body = new HashMap<>();
        body.put("volunteers[]", volunteersIds);
        body.put("name", chatroomName);

        final String finalChatroomNameStr = chatroomNameStr;
        Ion.with(view)
                .load("POST", Network.API_LOCATION + Network.API_REQUEST_MESSAGE_CHATROOMS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setBodyParameters(body)
                .as(new TypeToken<ChatroomJson>() {
                })
                .setCallback(new FutureCallback<ChatroomJson>() {
                    @Override
                    public void onCompleted(Exception error, ChatroomJson result) {
                        view.hideProgress();
                        if (error == null) {
                            if (result.getStatus() != Network.API_STATUS_ERROR)
                                Tools.replaceView(view, ChatroomView.newInstance(finalChatroomNameStr, result.getResponse().getId()), Animation.FADE_IN_OUT, false);
                        }
                        else
                            view.setDialog("Problème de connection", view.getString(R.string.connection_problem));
                    }
                });
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }
}
