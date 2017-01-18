package com.eip.red.caritathelp.Models;

import android.content.Intent;
import android.location.Location;

import com.eip.red.caritathelp.Models.User.User;

import java.io.Serializable;

/**
 * Created by pierr on 11/01/2016.
 */

public class ModelManager implements Serializable {

    private User user;

    public ModelManager(Intent intent) {
        this.user =  (User) intent.getSerializableExtra("user");
    }

    public User getUser() {
        return (user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserLocation(Location location) {
        user.setLatitude(String.valueOf(location.getLatitude()));
        user.setLongitude(String.valueOf(location.getLongitude()));
    }
}
