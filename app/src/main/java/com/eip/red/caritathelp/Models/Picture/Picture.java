package com.eip.red.caritathelp.Models.Picture;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pierr on 08/12/2016.
 */

public class Picture {
    private Integer id;
    @SerializedName("file_size")
    private Integer fileSize;
    @SerializedName("picture_path")
    private PicturePath picturePath;
    @SerializedName("is_main")
    private Boolean main;

    public Integer getId() {
        return id;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public PicturePath getPicturePath() {
        return picturePath;
    }

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }
}
