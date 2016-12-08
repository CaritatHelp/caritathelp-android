package com.eip.red.caritathelp.Models.mailbox;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pierr on 13/10/2016.
 */

public class ChatroomMessage {

    private Integer id;
    @SerializedName("chatroom_id") private Integer chatroomId;
    @SerializedName("volunteer_id") private Integer volunteerId;
    private String content;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    private String fullname;
    @SerializedName("thumb_path") private String thumbPath;

    public ChatroomMessage() {}

    public ChatroomMessage(WebSocketMessage webSocketMessage) {
        chatroomId = webSocketMessage.getChatroomId();
        volunteerId = webSocketMessage.getSenderId();
        content = webSocketMessage.getContent();
        createdAt = webSocketMessage.getCreatedAt();
        updatedAt = null;
        fullname = webSocketMessage.getSenderFirstname() + " " + webSocketMessage.getSenderLastname();
        thumbPath = webSocketMessage.getSenderThumb();
    }

    public Integer getId() {
        return id;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

    public Integer getVolunteerId() {
        return volunteerId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getFullname() {
        return fullname;
    }

    public String getThumbPath() {
        return thumbPath;
    }
}
