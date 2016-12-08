package com.eip.red.caritathelp.Presenters.Search;

import com.eip.red.caritathelp.Models.Search.Search;

/**
 * Created by pierr on 21/04/2016.
 */

public interface IMySearchPresenter {

    void onClick(int viewId, Search search);

    void search(String query);

    boolean isUser(int volunteerId);

}
