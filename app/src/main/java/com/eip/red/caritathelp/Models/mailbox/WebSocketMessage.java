package com.eip.red.caritathelp.Models.mailbox;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pierr on 14/10/2016.
 */

public class WebSocketMessage {

    @SerializedName("chatroom_id") private Integer chatroomId;
    @SerializedName("sender_id") private Integer senderId;
    @SerializedName("sender_firstname") private String senderFirstname;
    @SerializedName("sender_lastname") private String senderLastname;
    @SerializedName("sender_thumb_path") private String senderThumb;
    private String content;
    @SerializedName("created_at") private String createdAt;
    private String type;

    public Integer getChatroomId() {
        return chatroomId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public String getSenderFirstname() {
        return senderFirstname;
    }

    public String getSenderLastname() {
        return senderLastname;
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
}
