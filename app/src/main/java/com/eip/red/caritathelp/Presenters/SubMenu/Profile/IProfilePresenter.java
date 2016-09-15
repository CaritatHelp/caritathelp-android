package com.eip.red.caritathelp.Presenters.SubMenu.Profile;

import android.content.Intent;
import android.widget.ImageView;

import com.eip.red.caritathelp.Models.News.News;

/**
 * Created by pierr on 11/05/2016.
 */

public interface IProfilePresenter {

    void onClick(int viewId);

    void getData();

    void getNews(boolean isSwipeRefresh);

    void uploadProfileImg(ImageView imageView, Intent data);

    void goToProfileView();

    void goToPostView();

    void goToProfileView(News news);

    void goToCommentView(News news);

}
