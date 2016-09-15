package com.eip.red.caritathelp.Presenters.Home.Post;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pierr on 07/09/2016.
 */

public interface IPostPresenter {

    void initUserProfile(ImageView imageView, TextView name);

    void postNews(String content);
}
