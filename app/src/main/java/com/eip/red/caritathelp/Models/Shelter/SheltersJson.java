package com.eip.red.caritathelp.Models.Shelter;

import java.util.List;

/**
 * Created by pierr on 03/12/2016.
 */

public class SheltersJson {

    private Integer status;
    private String message;
    private List<Shelter> response;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Shelter> getResponse() {
        return response;
    }
}
