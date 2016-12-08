package com.eip.red.caritathelp.Presenters.SubMenu.MyEvents;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.Organisation.Management.OrganisationEventCreation.OrganisationEventCreationView;
import com.eip.red.caritathelp.Views.SubMenu.MyEvents.MyEventsView;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */
public class MyEventsPresenter implements IMyEventPresenter, IOnMyEventsFinishedListener {

    private MyEventsView        view;
    private MyEventsInteractor  interactor;

    public MyEventsPresenter(MyEventsView view, User mainUser, int userId) {
        this.view = view;

        interactor = new MyEventsInteractor(view.getActivity().getApplicationContext(), mainUser, userId);
    }

    @Override
    public boolean isMainUser() {
        return (interactor.getMainUserId() == interactor.getUserId());
    }

    @Override
    public void onClick(int viewId) {
        if (viewId == R.id.btn_create) {
            view.showProgress();
            interactor.getMyOrganisations(this);
        }
    }

    @Override
    public void getMyEvents(boolean init, String range, boolean swipeRefresh) {
        // Display the ProgressBar if it's not a SwipRefresh gesture
        if (!swipeRefresh)
            view.showProgress();

        switch (range) {
            case "En ce moment":
                interactor.getMyEvents(this, init, "current", swipeRefresh);
                break;
            case "A venir":
                interactor.getMyEvents(this, init, "futur", swipeRefresh);
                break;
            case "Passé":
                interactor.getMyEvents(this, init, "past", swipeRefresh);
                break;
            case "Organisateur":
                interactor.getMyEvents(this, init, "", swipeRefresh);
                break;
        }
    }

    @Override
    public void navigateToEventView(Event event) {
        /* Go To Event Page */
        Tools.replaceView(view, OrganisationEventView.newInstance(event.getId(), event.getTitle()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialog(String title, String msg, boolean swipeRefresh) {
        // Set SwipeRefreshLayout or ProgressBar Visibility
        if (swipeRefresh)
            view.getSwipeRefreshLayout().setRefreshing(false);
        else
            view.hideProgress();

        // Set Dialog
        view.setDialogError(title, msg);
    }

    @Override
    public void onSuccessGetMyEventsInit(List<Event> events, boolean owner) {
        // Set Create Btn Visibility
        if (owner)
            view.setVisibilityCreationPart(View.VISIBLE);

        // Update Recycler View Data
        view.getAdapter().update(events);

        // Set ProgressBar Visiblity
        view.hideProgress();
    }

    @Override
    public void onSuccessGetMyEvents(List<Event> events, boolean swipeRefresh) {
        // Update Recycler View
        view.getAdapter().update(events);

        // Set ProgressBar Visiblity || SwipeRefreshLayout refreshing
        if (swipeRefresh)
            view.getSwipeRefreshLayout().setRefreshing(false);
        else
            view.hideProgress();
    }

    @Override
    public void onSuccessGetMyOrganisations(List<Organisation> organisations) {
        view.hideProgress();
        showDialog(organisations);
    }

    private void showDialog(final List<Organisation> myOrganisationsOwner) {
        int                 size = myOrganisationsOwner.size();
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        String[]            names = new String[size];
        final int[]         idSelected = {0};

        // Init Arrays
        for (int i = 0; i < size; i++) {
            Organisation    organisation = myOrganisationsOwner.get(i);

            names[i] = organisation.getName();
        }

        builder.setTitle("Choisissez votre association");
        builder.setCancelable(true);

        // Set Single Choice
        builder.setSingleChoiceItems(names, idSelected[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idSelected[0] = which;
            }
        });

        // Set the positive/yes button click listener
        builder.setPositiveButton("Créer un événement", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = myOrganisationsOwner.get(idSelected[0]).getId();

                // Replace the View
                Tools.replaceView(view, OrganisationEventCreationView.newInstance(id), Animation.FADE_IN_OUT, false);
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
                dialog.dismiss();
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
