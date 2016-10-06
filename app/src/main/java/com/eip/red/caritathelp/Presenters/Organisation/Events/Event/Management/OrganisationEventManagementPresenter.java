package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management;

import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.OrganisationEventManagementView;

/**
 * Created by pierr on 14/04/2016.
 */

public class OrganisationEventManagementPresenter implements IOrganisationEventManagementPresenter, IOnOrganisationEventManagementFinishedListener {

    private OrganisationEventManagementView         view;
    private OrganisationEventManagementInteractor   interactor;

    public OrganisationEventManagementPresenter(OrganisationEventManagementView view, User user, int eventId) {
        this.view = view;

        interactor = new OrganisationEventManagementInteractor(view.getContext(), user, eventId);
    }


    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.begin_date:
                view.getDateBeginDialog().show();
                break;
            case R.id.begin_time:
                view.getTimeBeginDialog().show();
                break;
            case R.id.end_date:
                view.getDateEndDialog().show();
                break;
            case R.id.end_time:
                view.getTimeEndDialog().show();
                break;
            case R.id.btn_save:
                view.showProgress();
                interactor.saveEventModifications(this, view.getProgressBar(), view.getData());
                break;
        }
    }

    @Override
    public void getEvent() {
        view.showProgress();
        interactor.getEvent(view.getProgressBar(), this);
    }

    @Override
    public void onTitleError(String error) {
        view.hideProgress();
        view.setTitleError(error);
    }

    @Override
    public void onPhotoError(String error) {
        view.hideProgress();
        view.setPhotoError(error);
    }

    @Override
    public void onBeginDateError(String error) {
        view.hideProgress();
        view.setBeginDateError(error);
    }

    @Override
    public void onEndDateError(String error) {
        view.hideProgress();
        view.setEndDateError(error);
    }

    @Override
    public void onLocationError(String error) {
        view.hideProgress();
        view.setLocationError(error);
    }

    @Override
    public void onDescriptionError(String error) {
        view.hideProgress();
        view.setDescriptionError(error);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetEvent(Event event) {
        // Set View Data
        setViewData(event);
        view.hideProgress();
    }

    @Override
    public void onSuccessSaveEventModifications(Event event) {
        // Set View Data
        setViewData(event);

        // Hide Progress Bar
        view.hideProgress();

        // Display Dialog Message
        new AlertDialog.Builder(view.getContext()).setMessage("Les modifications ont bien été prises en compte.").show();
    }

    private void setViewData(Event event) {
        EditText    title = view.getTitle();
        TextView    beginDate = view.getBeginDate();
        TextView    beginTime = view.getBeginTime();
        TextView    endDate = view.getEndDate();
        TextView    endTime = view.getEndTime();
        EditText    location = view.getLocation();
        EditText    description = view.getDescription();

        // Clear Text
        title.getText().clear();
        location.getText().clear();
        description.getText().clear();

        // Set Hint
        title.setHint(event.getTitle());
        beginDate.setHint(event.getBegin());
        beginTime.setHint(event.getBegin());
        endDate.setHint(event.getEnd());
        endTime.setHint(event.getEnd());
        location.setHint(event.getPlace());
        description.setHint(event.getDescription());
    }
}
