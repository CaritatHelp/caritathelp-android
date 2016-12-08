package com.eip.red.caritathelp.Presenters.Search;

import com.eip.red.caritathelp.Models.Search.Search;
import java.util.List;

/**
 * Created by pierr on 21/04/2016.
 */

public interface IOnMySearchFinishedListener {

    void onDialog(String title, String msg);

    void onSuccessSearch(List<Search> searches);

    void onSuccessAdd(Search search);
}
