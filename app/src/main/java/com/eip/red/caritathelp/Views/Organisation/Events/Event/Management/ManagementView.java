package com.eip.red.caritathelp.Views.Organisation.Events.Event.Management;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.modification.ModificationView;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 15/01/2017.
 */

public class ManagementView extends Fragment {

    private int eventId;

    public static ManagementView newInstance(int eventId) {
        ManagementView    myFragment = new ManagementView();

        Bundle args = new Bundle();
        args.putString("page", "Gestion de l'événement");
        args.putInt("event id", eventId);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View    view = inflater.inflate(R.layout.fragment_organisation_event_management, container, false);
        ButterKnife.bind(this, view);

        eventId = getArguments().getInt("event id");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getString("page"));
    }

    @OnClick(R.id.btn_modify_event)
    public void onClickModifyEvent() {
        Tools.replaceView(this, ModificationView.newInstance(eventId), Animation.FADE_IN_OUT, false);
    }

    @OnClick(R.id.btn_manage_invitations)
    public void onClickManageInvitations() {

    }

    @OnClick(R.id.btn_delete_event)
    public void onClickDeleteEvent() {
        new AlertDialog.Builder(getContext())
                .setMessage("Voulez-vous supprimer cet événement?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        User user = ((MainActivity) getActivity()).getModelManager().getUser();

                        Ion.with(getContext())
                                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_ORGANISATION_EVENT + eventId)
                                .setHeader("access-token", user.getToken())
                                .setHeader("client", user.getClient())
                                .setHeader("uid", user.getUid())
                                .as(new TypeToken<JsonObject>(){})
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception error, JsonObject result) {
                                        if (error == null) {
                                            new AlertDialog.Builder(getContext())
                                                    .setMessage("L'événement a bien été supprimé.")
                                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                        @Override
                                                        public void onCancel(DialogInterface dialogInterface) {
                                                            dialogInterface.dismiss();
                                                            ((MainActivity)getActivity()).backToFirstPage();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
