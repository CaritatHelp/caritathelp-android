package com.eip.red.caritathelp.Presenters.Home;

import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Views.Home.HomeView;

import java.util.List;

/**
 * Created by pierr on 11/01/2016.
 */

public class HomePresenter implements IHomePresenter, IOnHomeFinishedListener {

    private HomeView        view;
    private HomeInteractor  interactor;

    public HomePresenter(HomeView view, String token) {
        this.view = view;
        interactor = new HomeInteractor(view.getContext(), token);
    }

    @Override
    public void getNews(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();
        interactor.getNews(this);
    }

    @Override
    public void onClick(int viewId) {

    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetNews(List<News> newsList) {
        view.updateRecyclerViewData(newsList);
        view.hideProgress();
    }
}
