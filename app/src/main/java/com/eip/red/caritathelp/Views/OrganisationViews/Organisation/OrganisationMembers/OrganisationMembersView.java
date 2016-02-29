package com.eip.red.caritathelp.Views.OrganisationViews.Organisation.OrganisationMembers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eip.red.caritathelp.MainActivity.MainActivity;
import com.eip.red.caritathelp.Models.Organisation;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 25/02/2016.
 */

public class OrganisationMembersView extends Fragment implements View.OnClickListener {

    private ListView    listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_organisation_members, container, false);

        // Init ListView & Listener & Adapter
        listView = (ListView)view.findViewById(R.id.orga_list_view);
        listView.setAdapter(new OrganisationMembersListViewAdapter(this));
        initListener();

        // Init Button Listener
        view.findViewById(R.id.top_bar_organisation_members_return).setOnClickListener(this);


        return (view);
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Page Change
//                TextView textView = (TextView) view.findViewById(R.id.my_organisations_title);

//                ((MainActivity) getActivity()).replaceView(OrganisationView.newInstance(textView.getText().toString()), true);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_organisation_members_return:
                ((MainActivity) getActivity()).goToPreviousPage();
                break;
        }
    }

}
