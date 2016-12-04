package com.eip.red.caritathelp.Views.Organisation.Shelters;

import com.eip.red.caritathelp.Models.Shelter.Shelter;

import java.util.List;

/**
 * Created by pierr on 03/12/2016.
 */

public interface Shelters {

    interface View {
        void showProgress();

        void hideProgress();

        void setDialog(String title, String msg);

        void updateRV(List<Shelter> shelters);
    }

    interface Presenter {
        void getShelters();

        void createShelter();

        void updateShelter(Integer shelterId);

        void deleteShelter(Integer shelterId, String shelterName);
    }
}
