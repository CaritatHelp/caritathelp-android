package com.eip.red.caritathelp.Presenters.Organisation.Events.Event;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Event;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.GalleryPhotoPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Home.Comment.CommentView;
import com.eip.red.caritathelp.Views.Home.Post.PostView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Guests.OrganisationEventGuestsView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Informations.OrganisationEventInformationsView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.OrganisationEventManagementView;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.OrganisationEventView;
import com.eip.red.caritathelp.Views.Organisation.OrganisationView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;
import com.eip.red.caritathelp.Views.SubMenu.Profile.galleryPhoto.GalleryPhotoView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventPresenter implements IOrganisationEventPresenter, IOnOrganisationEventFinishedListener {

    private OrganisationEventView           view;
    private OrganisationEventInteractor     interactor;

    public OrganisationEventPresenter(OrganisationEventView view, User user, int eventId) {
        this.view = view;
        interactor = new OrganisationEventInteractor(view.getContext(), user, eventId);
    }

    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.btn_post:
                Event   event = interactor.getEvent();
                Tools.replaceView(view, PostView.newInstance(News.GROUP_TYPE_EVENT, event.getId(), event.getTitle(), event.getRights()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_join:
                break;
            case R.id.btn_quit:
                break;
            case R.id.btn_guests:
                Tools.replaceView(view, OrganisationEventGuestsView.newInstance(interactor.getEvent().getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_emergency:
                showEmergencyDialog();
                break;
            case R.id.btn_informations:
                Tools.replaceView(view, OrganisationEventInformationsView.newInstance(interactor.getEvent().getId()), Animation.FADE_IN_OUT, false);
                break;
            case R.id.btn_management:
                Tools.replaceView(view, OrganisationEventManagementView.newInstance(interactor.getEvent().getId()), Animation.FADE_IN_OUT, false);
                break;
        }
    }

    private void showEmergencyDialog() {
        final IOnOrganisationEventFinishedListener listener = this;
        View emergencyView = View.inflate(view.getContext(), R.layout.view_emergency, null);
        final TextView volunteer = (TextView) emergencyView.findViewById(R.id.volunteer);
        final TextView zone = (TextView) emergencyView.findViewById(R.id.zone);
        final SeekBar volunteerSeekbar = (SeekBar) emergencyView.findViewById(R.id.seekbar_volunteer);
        final SeekBar zoneSeekbar = (SeekBar) emergencyView.findViewById(R.id.seekbar_zone);
        final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle("Création d'une urgence")
                .setView(emergencyView)
                .setCancelable(false)
                .setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Lancer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        interactor.raiseEmergency(listener, volunteer.getText().toString(), zone.getText().toString());
                    }
                })
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setVisibility(View.INVISIBLE);

                volunteerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        if (progress > 0)
                            positiveBtn.setVisibility(View.VISIBLE);
                        else
                            positiveBtn.setVisibility(View.INVISIBLE);

                        volunteer.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                zoneSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        if (progress > 0)
                            positiveBtn.setVisibility(View.VISIBLE);
                        else
                            positiveBtn.setVisibility(View.INVISIBLE);

                        String text = String.valueOf(progress) + " km";
                        zone.setText(text);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });

        dialog.show();
    }

    @Override
    public void getData() {
        view.showProgress();
        interactor.getData(this);
    }

    @Override
    public void getNews(boolean isSwipeRefresh) {
        if (!isSwipeRefresh)
            view.showProgress();
        interactor.getNews(this);
    }

    @Override
    public void goToGalleryPhotoView() {
        Event event = interactor.getEvent();

        if (event != null && event.getRights() != null) {
            if (event.getRights().equals("owner"))
                Tools.replaceView(view, GalleryPhotoView.newInstance(event.getId(), GalleryPhotoPresenter.GALLERY_PHOTO_OWNER.EVENT, true), Animation.FADE_IN_OUT, false);
            else
                Tools.replaceView(view, GalleryPhotoView.newInstance(event.getId(), GalleryPhotoPresenter.GALLERY_PHOTO_OWNER.EVENT, false), Animation.FADE_IN_OUT, false);
        }
    }

    @Override
    public void goToProfileView(News news) {
        if (news.isAs_group())
            Tools.replaceView(view, OrganisationView.newInstance(news.getGroup_id(),news.getGroup_name()), Animation.FADE_IN_OUT, false);
        else
            Tools.replaceView(view, ProfileView.newInstance(news.getVolunteer_id()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void goToCommentView(News news) {
        Tools.replaceView(view, CommentView.newInstance(news.getId()), Animation.FADE_IN_OUT, false);
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessGetData(Event event, List<News> newsList) {
        String  rights = event.getRights();

        Network.loadImage(view.getContext(), view.getPicture(), Network.API_LOCATION_2 + event.getThumb_path(), R.drawable.solidarite);

        if (rights != null) {
            switch (rights) {
                case "admin":
                    view.getEmergencyBtn().setVisibility(View.VISIBLE);
                    view.getManagementBtn().setVisibility(View.VISIBLE);
                    break;
                case "host":
                    view.getEmergencyBtn().setVisibility(View.VISIBLE);
                    view.getManagementBtn().setVisibility(View.VISIBLE);
                    break;
                case "member":
                    view.getQuitBtn().setVisibility(View.VISIBLE);
                    break;
            }
        }
        else
            view.getJoinBtn().setVisibility(View.VISIBLE);

        view.updateRV(newsList);
        view.getActivity().setTitle(Tools.upperCaseFirstLetter(event.getTitle()));
        view.getArguments().putString("page", event.getTitle());
        view.hideProgress();
    }

    @Override
    public void onSuccessGetNews(List<News> newsList) {
        view.updateRV(newsList);
        view.hideProgress();
    }
}
