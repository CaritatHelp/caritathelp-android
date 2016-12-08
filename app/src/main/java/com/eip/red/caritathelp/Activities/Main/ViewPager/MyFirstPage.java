package com.eip.red.caritathelp.Activities.Main.ViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eip.red.caritathelp.Models.Enum.Animation;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Home.HomeView;
import com.eip.red.caritathelp.Views.OrganisationSearch.OrganisationSearchView;
import com.eip.red.caritathelp.Views.SubMenu.SubMenuView;

/**
 * Created by pierr on 04/04/2016.
 */

public class MyFirstPage extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get News List
//        newsList = ((MainActivity) getActivity()).getModelManager().getNewsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (inflater.inflate(R.layout.fragment_view_pager_first_page, container, false));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Fragment
        initFragment();
    }

    private void initFragment() {
        // Set Fragment View (Check BackStack)
        Fragment    fragment = Tools.getLastFragment(getChildFragmentManager());
        if (fragment != null)
            Tools.replaceView(this, fragment, Animation.FADE_IN_OUT, true);
        else
            Tools.replaceView(this, HomeView.newInstance(), Animation.FADE_IN_OUT, true);
    }


}
