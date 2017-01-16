package com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Informations;

import android.app.AlertDialog;
import android.widget.Toast;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.EventInformations;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Informations.OrganisationEventInformationsView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventInformationsPresenter implements IOrganisationEventInformationsPresenter, IOnOrganisationEventInformationsFinishedListener {

    private OrganisationEventInformationsView           view;
    private OrganisationEventInformationsInteractor     interactor;

    private DateTimeFormatter   formatter;
    private DateTimeFormatter   formatter2;
    private DateTimeFormatter   newFormatter;

    public OrganisationEventInformationsPresenter(OrganisationEventInformationsView view, User user, int eventId) {
        this.view = view;

        // Init Interactor
        interactor = new OrganisationEventInformationsInteractor(view.getActivity().getApplicationContext(), user, eventId);

        // Init DateTimeFormatter
        formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").withLocale(Locale.FRANCE);
        formatter2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withLocale(Locale.FRANCE);
        newFormatter = DateTimeFormat.forPattern("'Le' E dd MMMM Y 'à' HH:mm");//.withZone(timeZone);
    }

    @Override
    public void getEvent() {
        view.showProgress();
        interactor.getEventInformations(this);
    }

    @Override
    public void onDialogError(String title, String msg) {
        view.hideProgress();
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccess(Event event) {
        // Set DateTime
        DateTime    beginDate = null , endDate = null;

        try {
            beginDate = formatter.parseDateTime(event.getBegin());
        }
        catch (Exception exception) {
            try {
                beginDate = formatter2.parseDateTime(event.getBegin());
            }
            catch (Exception exception2) {}
        }

        try {
            endDate = formatter.parseDateTime(event.getEnd());
        }
        catch (Exception exception) {
            try {
                endDate = formatter2.parseDateTime(event.getEnd());
            }
            catch (Exception exception2) {}
        }

        // Set View Data
        if (beginDate != null && endDate != null)
            view.setViewData(newFormatter.print(beginDate), newFormatter.print(endDate), event.getPlace(), event.getDescription());
        else
            view.setViewData(null, null, event.getPlace(), event.getDescription());

        // Set Progress Bar Visibility
        view.hideProgress();
    }

}
