package com.eip.red.caritathelp.Models.Shelter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pierr on 03/12/2016.
 */

public class Shelter {

    private Integer id;
    private String name;
    private String address;
    private Integer zipcode;
    private String city;
    private Integer latitude;
    private Integer longitude;
    @SerializedName("total_places")
    private Integer totalPlaces;
    @SerializedName("free_places")
    private Integer freePlaces;
    private List<String> tags;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("assoc_id")
    private Integer assocId;
    @SerializedName("thumb_path")
    private String thumbPath;
    private String description;
    private String phone;
    private String mail;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public Integer getTotalPlaces() {
        return totalPlaces;
    }

    public Integer getFreePlaces() {
        return freePlaces;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getAssocId() {
        return assocId;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }
}
