package com.eip.red.caritathelp.Presenters.mailbox;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.mailbox.ChatroomMessageJson;
import com.eip.red.caritathelp.Models.mailbox.ChatroomMessagesJson;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.MailBox.chatroom.ChatroomView;
import com.eip.red.caritathelp.Views.MailBox.chatroom.IChatroom;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 13/10/2016.
 */

public class ChatroomPresenter implements IChatroom.Presenter {

    private ChatroomView view;
    private User user;
    private Integer chatroomId;

    public ChatroomPresenter(ChatroomView view, User user, Integer chatroomId) {
        this.view = view;
        this.user = user;
        this.chatroomId = chatroomId;
    }

    @Override
    public void getMessages() {
        view.showProgress();

        Ion.with(view)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_MESSAGE_CHATROOMS + "/" + chatroomId)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<ChatroomMessagesJson>() {
                })
                .setCallback(new FutureCallback<ChatroomMessagesJson>() {
                    @Override
                    public void onCompleted(Exception error, ChatroomMessagesJson result) {
                        view.hideProgress();
                        if (error == null) {
                            if (result.getStatus() != Network.API_STATUS_ERROR)
                                view.updateRVData(result.getResponse());
                        }
                        else
                            view.setDialog("Problème de connection", view.getString(R.string.connection_problem));
                    }
                });
    }

    @Override
    public void sendMessage(String message) {
        view.showProgress();

        Ion.with(view)
                .load("PUT", Network.API_LOCATION + Network.API_REQUEST_MESSAGE_CHATROOMS + "/" + chatroomId + Network.API_REQUEST_MESSAGE_NEW_MESSAGE)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .setBodyParameter("content", message)
                .as(new TypeToken<ChatroomMessageJson>() {})
                .setCallback(new FutureCallback<ChatroomMessageJson>() {
                    @Override
                    public void onCompleted(Exception error, ChatroomMessageJson result) {
                        view.hideProgress();
                        if (error != null)
                            view.setDialog("Problème de connection", view.getString(R.string.connection_problem));
//                        if (error == null) {
////                            if (result.getStatus() != Network.API_STATUS_ERROR)
////                                getMessages();
//                        }
//                        else
//                            view.setDialog("Problème de connection", view.getString(R.string.connection_problem));
                    }
                });
    }

    @Override
    public void goToProfileView(Integer userId) {
        Tools.replaceView(view, ProfileView.newInstance(userId), Animation.FADE_IN_OUT, false);
    }

    public Integer getUserId() {
        return user.getId();
    }
}
