package com.eip.red.caritathelp.Models.mailbox;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pierr on 14/10/2016.
 */

public class WebSocketMessage {

    public static String WEBSOCKETMESSAGE_TYPE_MESSAGE = "message";

    @SerializedName("volunteer_id")
    private int volunteerId;
    private int id;
    @SerializedName("chatroom_id")
    private Integer chatroomId;
    @SerializedName("sender_id")
    private Integer senderId;
    private String fullname;
    @SerializedName("thumb_path")
    private String senderThumb;
    private String content;
    @SerializedName("created_at")
    private String createdAt;
    private String type;

    public Integer getChatroomId() {
        return chatroomId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getSenderThumb() {
        return senderThumb;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getType() {
        return type;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public int getId() {
        return id;
    }
}
