package com.eip.red.caritathelp.Activities.Main;

import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 26/03/2016.
 */

public class MySearchBar {

    private CardView        searchBar;
//    private LinearLayout    searchBarContent;
    private EditText        searchText;
    private ImageButton     cancelBtn;

    public MySearchBar(MainActivity activity) {
        // Get UI elements
//        searchBar = (CardView) activity.findViewById(R.id.my_search_bar);
//        searchBarContent = (LinearLayout) activity.findViewById(R.id.search_bar_content);
//        searchText = (EditText) activity.findViewById(R.id.ed_search);
//        cancelBtn = (ImageButton) activity.findViewById(R.id.btn_clear_edit_text);

        // Init Listener
        initListener();
    }

    private void initListener() {
        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Set Gravity SearchBar to left
//                    searchBarContent.setGravity(Gravity.LEFT);
                }
                else {
                    // If searchbar is empty -> set gravity to center
//                    if (TextUtils.isEmpty(searchText.getText()))
//                        searchBarContent.setGravity(Gravity.CENTER);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear Text
                TextKeyListener.clear(searchText.getText());

                // If searchBar Edittext has not the focus -> Set Gravity SearchBar to CENTER
//                if (!searchText.isFocused())
//                    searchBarContent.setGravity(Gravity.CENTER);
            }
        });
    }

    public void show(int hint) {
        // Set SearchBar Visibility
        searchBar.setVisibility(View.VISIBLE);

        // Set SearchBarContent Gravity
//        searchBarContent.setGravity(Gravity.CENTER);

        // Set SearchText Hint
        searchText.setHint(hint);

        // Clear SearchText
        TextKeyListener.clear(searchText.getText());
    }

    public int getVisibility() {
        return (searchBar.getVisibility());
    }

    public void setVisibility(int visibility) {
        searchBar.setVisibility(visibility);
    }

    public EditText getSearchText() {
        return searchText;
    }

    public ImageButton getCancelBtn() {
        return cancelBtn;
    }
}
