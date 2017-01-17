package com.eip.red.caritathelp.Presenters.Sign.Up.Begin;

import android.Manifest;
import android.widget.FrameLayout;

import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Sign.Up.SignUpBeginView;
import com.eip.red.caritathelp.Views.Sign.Up.SignUpPersonView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by pierr on 23/03/2016.
 */

public class SignUpBeginPresenter implements ISignUpBeginPresenter {

    private SignUpBeginView         view;
    private SignUpBeginInteractor   interactor;

    public SignUpBeginPresenter(SignUpBeginView view, User user) {
        this.view = view;

        interactor = new SignUpBeginInteractor(user);
    }


    @Override
    public void init() {
//        User    user  = interactor.getUser();
//        String  geolocation = "true";//user.isGeolocation();
//        boolean notifications = false;//user.isNotifications();
//
//        if (geolocation != null && geolocation.equals("true"))
//            view.getGeolocation().setChecked(true);
//
//        if (notifications)
//            view.getNotifications().setChecked(true);
    }

    @Override
    public void onClick(int viewid) {
        if (viewid == R.id.btn_next) {
            // Set User Model
            interactor.setUserModel(view.getGeolocation().isChecked(), view.getNotifications().isChecked());

            // Go to next page
            ((SignActivity) view.getActivity()).replaceView(new SignUpPersonView(), Animation.SLIDE_LEFT_RIGHT);
        }
    }

    @Override
    public void setGeolocation(final boolean geolocation) {
        if (geolocation) {
            Dexter.checkPermissions(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.getGrantedPermissionResponses().size() == 2)
                        interactor.setGeolocation(geolocation);
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }
}
