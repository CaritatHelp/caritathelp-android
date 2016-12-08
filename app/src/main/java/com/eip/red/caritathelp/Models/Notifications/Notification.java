package com.eip.red.caritathelp.Models.Notifications;

/**
 * Created by pierr on 21/04/2016.
 */
public class Notification {

    public final static String  NOTIF_TYPE_JOIN_ASSOC = "JoinAssoc";
    public final static String  NOTIF_TYPE_JOIN_EVENT = "JoinEvent";
    public final static String  NOTIF_TYPE_INVITE_MEMBER = "InviteMember";
    public final static String  NOTIF_TYPE_INVITE_GUEST = "InviteGuest";
    public final static String  NOTIF_TYPE_ADD_FRIEND = "AddFriend";
    public final static String  NOTIF_TYPE_NEW_GUEST = "NewGuest";
    public final static String  NOTIF_TYPE_NEW_MEMBER = "NewMember";
    public final static String  NOTIF_TYPE_EMERGENCY = "Emergency";

    private int     id;
    private int     sender_id;
    private int     receiver_id;
    private int     assoc_id;
    private int     event_id;
    private boolean read;
    private String  notif_type;
    private String  created_at;
    private String  updated_at;
    private String  assoc_name;
    private String  event_name;
    private String  sender_name;
    private String  receiver_name;
    private String  thumb_path;

    private String  result = null;

    public int getId() {
        return id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public int getAssoc_id() {
        return assoc_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public boolean isRead() {
        return read;
    }

    public String getNotif_type() {
        return notif_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getAssoc_name() {
        return assoc_name;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
