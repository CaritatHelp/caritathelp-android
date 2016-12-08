package com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations.OrganisationCreation;

import android.content.Intent;
import android.widget.ImageView;

/**
 * Created by pierr on 24/02/2016.
 */

public interface IOrganisationCreationPresenter {

    void uploadProfileImg(ImageView imageView, Intent data);

    void onClick(int viewId);
    
}
