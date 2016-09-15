package com.eip.red.caritathelp.Models;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

/**
 * Created by pierr on 05/12/2015.
 */

public class Network implements Serializable {


    static final public  String     API_LOCATION = "http://staging.caritathelp.me/";
    static final public  String     API_LOCATION_2 = "http://staging.caritathelp.me";

    static final public  String     API_REQUEST_LOGIN = "login";
    static final public  String     API_REQUEST_ORGANISATION = "associations";
    static final public  String     API_REQUEST_ORGANISATION_BY_ID = "associations/";
    static final public  String     API_REQUEST_ORGANISATION_MEMBERS = "/members";
    static final public  String     API_REQUEST_ORGANISATION_EVENTS = "/events";
    static final public  String     API_REQUEST_ORGANISATION_EVENT = "/events/";
    static final public  String     API_REQUEST_ORGANISATION_EVENTS_GUESTS = "/guests";
    static final public  String     API_REQUEST_ORGANISATION_EVENTS_INFORMATIONS = "/events/";
    static final public  String     API_REQUEST_ORGANISATION_EVENT_MANAGEMENT = "/events/";
    static final public  String     API_REQUEST_ORGANISATION_NEWS = "/news";

    static final public  String     API_REQUEST_VOLUNTEERS = "volunteers/";
    static final public  String     API_REQUEST_VOLUNTEERS_2 = "/volunteers/";
    static final public  String     API_REQUEST_VOLUNTEERS_MAIN_PICTURE = "/main_picture";

    static final public  String     API_REQUEST_GET_MY_ORGANISATIONS = "/associations";
    static final public  String     API_REQUEST_MY_EVENTS = "/volunteers/";

    static final public  String     API_REQUEST_SUBSCRIBE = "volunteers";
    static final public  String     API_REQUEST_ACCOUNT_SETTINGS_MODIFICATION = "volunteers";
    static final public  String     API_REQUEST_VOLUNTEER_DELETE_ACCOUNT = "volunteers/";

    /* NOTIFICATIONS */
    static final public  String     API_REQUEST_NOTIFICATIONS = "/notifications";

    /* SEARCH */
    static final public  String     API_REQUEST_SEARCH = "/search";

    /* FRIENDSHIP */
    static final public  String     API_REQUEST_FRIENDSHIP = "/friends";
    static final public  String     API_REQUEST_FRIENDSHIP_VOLUNTEER = "/volunteers/";
    static final public  String     API_REQUEST_FRIENDSHIP_ADD = "/friendship/add";
    static final public  String     API_REQUEST_FRIENDSHIP_REMOVE = "/friendship/remove";
    static final public  String     API_REQUEST_FRIENDSHIP_REPLY = "/friendship/reply";

    /* MEMBERSHIP */
    static final public  String     API_REQUEST_MEMBERSHIP_KICK = "/membership/kick";
    static final public  String     API_REQUEST_MEMBERSHIP_UPGRADE = "/membership/upgrade";
    static final public  String     API_REQUEST_MEMBERSHIP_JOIN = "/membership/join";
    static final public  String     API_REQUEST_MEMBERSHIP_INVITE = "/membership/invite";
    static final public  String     API_REQUEST_MEMBERSHIP_REPLY_INVITE = "/membership/reply_invite";
    static final public  String     API_REQUEST_MEMBERSHIP_LEAVE = "/membership/leave";
    static final public  String     API_REQUEST_MEMBERSHIP_INVITED = "/membership/invited";
    static final public  String     API_REQUEST_MEMBERSHIP_UNINVITE = "/membership/uninvite";
    static final public  String     API_REQUEST_MEMBERSHIP_WAITING = "/membership/waiting";

    /* GUESTS */
    static final public  String     API_REQUEST_GUESTS_REPLY_INVITE = "/guests/reply_invite";

    /* INVITATIONS Friend-Organisation-Event */
    static final public  String     API_REQUEST_FRIEND_REQUESTS = "/friend_requests";
    static final public  String     API_REQUEST_ORGANISATIONS_INVITED = "/associations/invited";
    static final public  String     API_REQUEST_EVENTS_INVITED = "/events/invited";

    /* NEWS */
    static final public  String     API_REQUEST_NEWS = "/news";
    static final public  String     API_REQUEST_NEWS_WALL_MESSAGE = "/news/wall_message";
    static final public  String     API_REQUEST_NEWS_COMMENTS = "/comments";

    /* COMMENT */
    static final public  String     API_REQUEST_POST_COMMENTS = "/comments";
    static final public  String     API_REQUEST_GET_COMMENTS = "/comments/";
    static final public  String     API_REQUEST_PUT_COMMENTS = "/comments/";
    static final public  String     API_REQUEST_DELETE_COMMENTS = "/comments/";

    /* PICTURE */
    static final public  String     API_REQUEST_PICTURES = "/pictures";


    /* API PARAMETERS */
    static final public  String     API_PARAMETER_STATUS = "status";
    static final public  String     API_PARAMETER_MSG = "message";
    static final public  String     API_PARAMETER_RESPONSE = "response";

    /* API STATUS */
    static final public int     API_STATUS_ERROR = 400;
    static final public int     API_STATUS_SUCCESS = 200;

    /* API MSG */
    /* LOGIN */
    static final public  String     API_MSG_OK = "ok";
    static final public  String     API_MSG_UNKNOWN_MAIL = "Unknown mail";
    static final public  String     API_MSG_WRONG_PASSWORD = "Wrong password";
    /* SUBSCRIBE */
    static final public  String     API_MSG_INVALID_MAIL = "Validation failed: Mail is invalid";
    static final public  String     API_MSG_UNAVAILABLE_MAIL = "Unavailable mail";

    private String  token;

    public Network() {

    }

//    public Network(JsonObject result) {
//        token = result.getAsJsonObject(Network.API_PARAMETER_RESPONSE).get("token").getAsString();
//    }

    public static void loadImage(Context context, ImageView imageView, String url, int errorImg) {
        Picasso.with(context)
                .load(url)
                .noPlaceholder()
                .error(errorImg)
                .into(imageView);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
