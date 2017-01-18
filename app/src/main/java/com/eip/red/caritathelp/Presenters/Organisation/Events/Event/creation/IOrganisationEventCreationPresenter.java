package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.creation;

import android.content.Intent;

/**
 * Created by pierr on 18/03/2016.
 */

public interface IOrganisationEventCreationPresenter {

    void onClick(int viewId);

    void uploadEventImg(Intent data, int code);
}
