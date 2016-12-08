package com.eip.red.caritathelp.Presenters.Sign.Up.Begin;

import com.eip.red.caritathelp.Models.User.User;

/**
 * Created by pierr on 23/03/2016.
 */

public class SignUpBeginInteractor {

    private User user;

    public SignUpBeginInteractor(User user) {
        this.user = user;
    }

    public void setUserModel(boolean geolocation, boolean notifications) {
//        user.setGeolocation(geolocation);
//        user.setNotifications(notifications);
    }

    public User getUser() {
        return user;
    }
}
