package com.eip.red.caritathelp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.eip.red.caritathelp.Models.Enum.Animation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pierr on 12/03/2016.
 */

public class Tools {

    static public File createImageFile() throws IOException {
        // Create an image file name
        String  timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        String  imageFileName = "IMG_" + timeStamp + "_";
        File    storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Caritathelp");
//        File    storageDir = Environment.getExternalStoragePublicDirectory("Caritathelp");

//        System.out.println("PASSERRRRRRRRRRRRRRRRR11111111111111111111111");

        if (!storageDir.exists()) {
            System.out.println("PASSERRRRRRRRRRRRRRRRR22222222222222222222222");
            if (!storageDir.mkdirs()) {
                System.out.println("PASSERRRRRRRRRRRRRRRRR");
                Log.d("Pics Files directory", "failed to create directory");
                return null;
            }
        }

        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    static public Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }

    static public File getOutputMediaFile() {
//        File myFilesDir = new File(Environment.getExternalStorageDirectory(), "Caritathelp");

        File myFilesDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Caritathelp");
//        File myFilesDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/caritathelp/files");

        // Create the storage directory if it does not exist
        if (!myFilesDir.exists()) {
            if (!myFilesDir.mkdirs()){
                Log.d("Pics Files directory", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        return new File(myFilesDir.getPath(), "IMG_"+ timeStamp + ".jpg");
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

    static public void showKeyboard(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager)  context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    static public void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
