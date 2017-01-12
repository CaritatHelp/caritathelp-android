package com.eip.red.caritathelp.Views.MailBox.chatroom;

import com.eip.red.caritathelp.Models.mailbox.ChatroomMessage;

import java.util.List;

/**
 * Created by pierr on 13/10/2016.
 */

public interface IChatroom {

    interface View {
        void showProgress();

        void hideProgress();

        void setDialog(String title, String msg);

        void updateRVData(List<ChatroomMessage> messages);

        void addMessage(ChatroomMessage message);
    }

    interface Presenter {
        void getMessages();

        void sendMessage(String message);

        void goToProfileView(Integer userId);
    }

}
