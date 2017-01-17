package com.eip.red.caritathelp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.eip.red.caritathelp.Models.Enum.Animation;

import java.util.List;

/**
 * Created by pierr on 12/03/2016.
 */

public class Tools {

    static public Location getLastKnownLocation(LocationManager locationManager) {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);

            if (l == null)
                continue;

            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy())
                bestLocation = l;
        }

        return bestLocation;
    }

    static public void replaceView(Fragment currentFragment ,Fragment newFragment, int animation, boolean parent) {
        FragmentManager     fm;
        FragmentTransaction ft;

        // Init FM & FT
        if (parent)
            fm = currentFragment.getChildFragmentManager();
        else
            fm = currentFragment.getParentFragment().getChildFragmentManager();

        ft = fm.beginTransaction();

        // Set Animation
        switch (animation) {
            case Animation.SLIDE_LEFT_RIGHT:
//                ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out);
                break;
            case Animation.SLIDE_UP_DOWN:
//                ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
                break;
            case Animation.FLIP_LEFT_RIGHT:
//                ft.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.fade_in, R.animator.fade_out);//,0 R.animator.card_flip_left_in, R.animator.card_flip_left_out);
                break;
            case Animation.FADE_IN_OUT:
                ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                break;
        }

        // Replace Fragment (Check Tag)
        String tag = newFragment.getTag();

        if (tag == null) {
            ft.replace(R.id.fragment_container, newFragment, Integer.toString(fm.getBackStackEntryCount()));

            // Save old fragment in the stack
            ft.addToBackStack(newFragment.getTag());
        }
        else
            ft.replace(R.id.fragment_container, newFragment, tag);

        // Commit changes
        ft.commit();
    }

    static public Fragment getLastFragment(FragmentManager fragmentManager) {
        int count = fragmentManager.getBackStackEntryCount();

        if (count > 0)
            return (fragmentManager.findFragmentByTag(Integer.toString(count - 1)));

        return (null);
    }


    static public String upperCaseFirstLetter(String value) {
        StringBuilder newValue = new StringBuilder(value.toLowerCase());

        newValue.setCharAt(0, Character.toUpperCase(value.charAt(0)));

        return (newValue.toString());
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    static public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, WindowManager.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
