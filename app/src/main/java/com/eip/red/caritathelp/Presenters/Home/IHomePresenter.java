package com.eip.red.caritathelp.Presenters.Home;

import android.widget.ImageView;

import com.eip.red.caritathelp.Models.News.News;

/**
 * Created by pierr on 08/08/2016.
 */

public interface IHomePresenter {

    void initUserImage(ImageView imageView);

    void getNews(boolean isSwipeRefresh);

    void goToProfileView();

    void goToPostView();

    void goToProfileView(News news);

    void goToCommentView(News news);
}
