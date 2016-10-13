package com.eip.red.caritathelp.Views.MailBox.chatroomCreation;

import com.eip.red.caritathelp.Models.Search.Search;

import java.util.List;

/**
 * Created by pierr on 13/10/2016.
 */

public interface ChatroomCreation {

    interface View {
        void showProgress();

        void hideProgress();

        void setDialog(String title, String msg);

        void addTag(List<String> tags);

        void updateRVData(List<Search> searchList);
    }

    interface Presenter {

        void search(String search);

        void addTag(Search search);

        void setTags(List<String> tags);

        void goToChatRoomView();
    }

}
