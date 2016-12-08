package com.eip.red.caritathelp.Activities.Main.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 14/03/2016.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 4;

    private List<Fragment>  fragments;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

        // Init Fragment List
        fragments = new ArrayList<>();
        fragments.add(new MyFirstPage());
        fragments.add(new MySecondPage());
        fragments.add(new MyThirdPage());
        fragments.add(new MyFourthPage());
    }

    @Override
    public Fragment getItem(int position) {
        return (fragments.get(position));
    }

    @Override
    public int getCount() {
        return (NUM_PAGES);
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

    public Fragment getFragment(int id) {
        return (fragments.get(id));
    }

}
