package com.eip.red.caritathelp.Models.mailbox;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pierr on 13/10/2016.
 */

public class Chatroom {

    private Integer id;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;
    private String name;
    @SerializedName("number_volunteers") private Integer volunteersNumber;
    @SerializedName("number_messages") private Integer messagesNumber;
    @SerializedName("is_private") private Boolean privat;
    private List<String> volunteers;

    public Integer getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public Integer getVolunteersNumber() {
        return volunteersNumber;
    }

    public Integer getMessagesNumber() {
        return messagesNumber;
    }

    public Boolean getPrivat() {
        return privat;
    }

    public List<String> getVolunteers() {
        return volunteers;
    }
}
