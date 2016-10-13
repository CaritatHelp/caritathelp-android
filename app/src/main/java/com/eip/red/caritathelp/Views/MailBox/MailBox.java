package com.eip.red.caritathelp.Views.MailBox;

import com.eip.red.caritathelp.Models.mailbox.Chatroom;

import java.util.List;

/**
 * Created by pierr on 13/10/2016.
 */

public interface MailBox {

    interface View {
        void showProgress();

        void hideProgress();

        void setDialog(String title, String msg);

        void updateRVData(List<Chatroom> chatrooms);

    }

    interface Presenter {

        void getChatrooms(boolean isSwipeRefresh);

        void goToChatroom(String chatroomName, Integer chatroomId);

        void goToChatroomCreationView();
    }
}
