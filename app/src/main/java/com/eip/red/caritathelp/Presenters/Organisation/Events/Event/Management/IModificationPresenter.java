package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management;

import android.content.Intent;

/**
 * Created by pierr on 14/04/2016.
 */

public interface IModificationPresenter {

    void onClick(int viewId);

    void getEvent();

    void uploadEventImg(Intent data, int code);
}
