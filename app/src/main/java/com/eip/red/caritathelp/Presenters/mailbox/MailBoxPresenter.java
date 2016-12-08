package com.eip.red.caritathelp.Presenters.mailbox;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.mailbox.ChatroomMessage;
import com.eip.red.caritathelp.Models.mailbox.ChatroomsJson;
import com.eip.red.caritathelp.Models.mailbox.WebSocketMessage;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.MailBox.MailBox;
import com.eip.red.caritathelp.Views.MailBox.MailBoxView;
import com.eip.red.caritathelp.Views.MailBox.chatroom.ChatroomView;
import com.eip.red.caritathelp.Views.MailBox.chatroomCreation.ChatroomCreationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by pierr on 13/10/2016.
 */

public class MailBoxPresenter implements MailBox.Presenter {

    private MailBoxView view;
    private User user;
    private ChatroomView chatroomView;

    public MailBoxPresenter(MailBoxView view, User user) {
        this.view = view;
        this.user = user;
        chatroomView = null;
    }

    @Override
    public void getChatrooms(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();

        Ion.with(view)
                .load("GET", Network.API_LOCATION + Network.API_REQUEST_MESSAGE_CHATROOMS)
                .setHeader("access-token", user.getToken())
                .setHeader("client", user.getClient())
                .setHeader("uid", user.getUid())
                .as(new TypeToken<ChatroomsJson>(){})
                .setCallback(new FutureCallback<ChatroomsJson>() {
                    @Override
                    public void onCompleted(Exception error, ChatroomsJson result) {
                        view.hideProgress();
                        if (error == null) {
                            if (result.getStatus() == Network.API_STATUS_ERROR)
                                view.setDialog("Statut 400", result.getMessage());
                            else
                                view.updateRVData(result.getResponse());
                        }
                        else
                            view.setDialog("Problème de connection", view.getString(R.string.connection_problem));
                    }
                });
    }

    @Override
    public void onMessage(String message) {
        if (chatroomView != null && chatroomView.isVisible())
            chatroomView.addMessage(new Gson().fromJson(message, ChatroomMessage.class));
        else
            getChatrooms(false);
    }

    @Override
    public void goToChatroom(String chatroomName, Integer chatroomId) {
        chatroomView = (ChatroomView) ChatroomView.newInstance(chatroomName, chatroomId);
        Tools.replaceView(view, chatroomView, Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToChatroomCreationView() {
        Tools.replaceView(view, ChatroomCreationView.newInstance(), Animation.FADE_IN_OUT, false);
    }
}
