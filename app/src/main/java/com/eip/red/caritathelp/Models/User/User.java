package com.eip.red.caritathelp.Models.User;

import com.eip.red.caritathelp.Models.Profile.MainPicture;

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

    private int     id;
    private String  mail;
    private String  token;
    private String  firstname;
    private String  lastname;
    private String  birthday;
    private String  gender;
    private String  city;
    private String  latitude;
    private String  longitude;
    private boolean allowgps;
    private boolean allow_notifications;
    private String  friendship;
    private int     notif_id;
    private String  thumb_path;

    private String      password;
    private boolean     geolocation;
    private boolean     notifications;

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getToken() {
        return token;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public boolean isAllowgps() {
        return allowgps;
    }

    public boolean isAllow_notifications() {
        return allow_notifications;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setAllowgps(boolean allowgps) {
        this.allowgps = allowgps;
    }

    public void setAllow_notifications(boolean allow_notifications) {
        this.allow_notifications = allow_notifications;
    }

    public String getFriendship() {
        return friendship;
    }

    public int getNotif_id() {
        return notif_id;
    }

    public void setThumb_path(String thumb_path) {
        this.thumb_path = thumb_path;
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
}

//    private int     id;
//    private String  lastname;
//    private String  firstname;
//    private String  gender;
//    private String  birthday;
//
//    private String  mail;
//    private String  password;
//    private boolean geolocation;
//    private boolean notifications;
//
//    public User() {
//    }
//
//    public User(JsonObject result) {
//        JsonObject  res = result.getAsJsonObject(Network.API_PARAMETER_RESPONSE);
//
//        id = initInt(res, "id");
//        mail = initStr(res, "mail");
//        lastname = initStr(res, "lastname");
//        firstname = initStr(res, "firstname");
//        gender = initStr(res, "gender");
//        birthday = initStr(res, "birthday");
//
//
////        JsonElement mail = res.get("mail");
////        JsonElement lastname = res.get("lastname");
////        JsonElement firstname = res.get("firstname");
////        JsonElement birthday = res.get("birthday");
////        JsonElement gender = res.get("gender");
//        JsonElement geolocation = res.get("allowgps");
//
////        System.out.println(res.toString());
//
////        id = res.get("id").getAsInt();
////
////        if (mail != null)
////            this.mail = mail.getAsString();
////
////        if (lastname != null)
////            this.lastName = lastname.getAsString();
////
////        if (firstname != null)
////            this.firstName = firstname.getAsString();
//
////        if (birthday != null) {
////            this.birthday = birthday.getAsString();
////        }
//
////        if (gender != null)
////            this.gender = gender.getAsString();
//
//        if (geolocation != null) {
//            if (geolocation.getAsString().equals("true"))
//                this.geolocation = true;
//            else
//                this.geolocation = false;
//        }
//
//
//    }
//
//    private int initInt(JsonObject jsonObject, String memberName) {
//        JsonElement jsonElement = jsonObject.get(memberName);
//
//        if (!jsonElement.isJsonNull())
//            return (jsonElement.getAsInt());
//        return (-1);
//    }
//
//    private String initStr(JsonObject jsonObject, String memberName) {
//        JsonElement jsonElement = jsonObject.get(memberName);
//
//        if (!jsonElement.isJsonNull())
//            return (jsonElement.getAsString());
//        return (null);
//    }
//
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(String birthday) {
//        this.birthday = birthday;
//    }
//
//    public void setBirthday(List<Integer> birthdayList) {
//        if (birthdayList != null) {
//            int year = birthdayList.get(0);
//            int month = birthdayList.get(0);
//            int day = birthdayList.get(0);
//
//            // Init Sting
//            birthday = "";
//
//            // Init Day
//            if (day < 10)
//                birthday += "0" + String.valueOf(day);
//            else
//                birthday += String.valueOf(day);
//
//            // Init Month
//            if (month < 10)
//                birthday += "/0" + String.valueOf(month);
//            else
//                birthday += "/" + String.valueOf(month);
//
//            // Init Year
//            birthday += "/" + String.valueOf(year);
//        }
//        else
//             birthday = null;
//    }
//
//    public String getMail() {
//        return mail;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }
//
//    public boolean isNotifications() {
//        return notifications;
//    }
//
//    public void setNotifications(boolean notifications) {
//        this.notifications = notifications;
//    }
//
//    public String isGeolocation() {
//        if (geolocation)
//            return ("true");
//        return ("false");
//    }
//
//    public void setGeolocation(boolean geolocation) {
//        this.geolocation = geolocation;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
