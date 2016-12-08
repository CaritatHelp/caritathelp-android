package com.eip.red.caritathelp.Views.Organisation.Management;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Organisation.Management.OrganisationEventCreation.OrganisationEventCreationView;
import com.eip.red.caritathelp.Views.Organisation.Shelters.SheltersView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 24/02/2016.
 */
public class OrganisationManagementView extends Fragment {

    private int organisationId;

    public static OrganisationManagementView newInstance(int idOrganisation) {
        OrganisationManagementView    myFragment = new OrganisationManagementView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_organisation_management);
        args.putInt("organisation id", idOrganisation);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.organisationId = getArguments().getInt("organisation id");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View    view = inflater.inflate(R.layout.fragment_organisation_management, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getInt("page"));
    }

    @OnClick(R.id.btn_create_event)
    public void onClickCreateEventBtn() {
        Tools.replaceView(this, OrganisationEventCreationView.newInstance(organisationId), Animation.FADE_IN_OUT, false);
    }

    @OnClick(R.id.btn_manage_member)
    public void onClickManageMember() {

    }

    @OnClick(R.id.btn_manage_shelters)
    public void onClickMangerSheltersBtn() {
        Tools.replaceView(this, SheltersView.newInstance(organisationId, true), Animation.FADE_IN_OUT, false);
    }

}
