package com.eip.red.caritathelp.Models.User;

import com.eip.red.caritathelp.Models.Profile.MainPicture;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pierr on 05/12/2015.
 */

public class User implements Serializable {

    public static final String FRIENDSHIP_YOURSELF = "yourself";
    public static final String FRIENDSHIP_NONE = "none";
    public static final String FRIENDSHIP_FRIEND = "friend";
    public static final String FRIENDSHIP_INVITATION_SENT = "invitation sent";
    public static final String FRIENDSHIP_INVITATIONS_RECEIVED = "invitation received";
    public static final String FRIENDSHIP_INVITATIONS_RECEIVED_CONFIRM = "invitation received confirm";
    public static final String FRIENDSHIP_INVITATIONS_RECEIVED_REMOVE = "invitation received remove";

    private int id;
    private String provider;
    private String uid;
    private String token;
    private String client;
    private String firstname;
    private String lastname;
    private String fullname;
    private String thumb_path;
    private String image;
    @SerializedName("email")
    private String mail;
    private String city;
    private String gender;
    private String birthday;
    private boolean allow_notifications;
    private boolean allowgps;
    private String latitude;
    @SerializedName("notifications_number")
    private Integer notificationsNumber;
    private String longitude;
    private String friendship;
    private int notif_id;

    private String password;
    private boolean geolocation;
    private boolean notifications;

    private boolean check;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public void setThumb_path(String thumb_path) {
        this.thumb_path = thumb_path;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isAllow_notifications() {
        return allow_notifications;
    }

    public void setAllow_notifications(boolean allow_notifications) {
        this.allow_notifications = allow_notifications;
    }

    public boolean isAllowgps() {
        return allowgps;
    }

    public void setAllowgps(boolean allowgps) {
        this.allowgps = allowgps;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getNotificationsNumber() {
        return notificationsNumber;
    }

    public void setNotificationsNumber(Integer notificationsNumber) {
        this.notificationsNumber = notificationsNumber;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFriendship() {
        return friendship;
    }

    public void setFriendship(String friendship) {
        this.friendship = friendship;
    }

    public int getNotif_id() {
        return notif_id;
    }

    public void setNotif_id(int notif_id) {
        this.notif_id = notif_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGeolocation() {
        return geolocation;
    }

    public void setGeolocation(boolean geolocation) {
        this.geolocation = geolocation;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

