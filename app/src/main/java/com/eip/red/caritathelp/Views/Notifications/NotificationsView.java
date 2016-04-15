package com.eip.red.caritathelp.Views.Notifications;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 16/11/2015.
 */

public class NotificationsView extends Fragment implements View.OnClickListener {

    private View    view;

    public static Fragment newInstance() {
        NotificationsView       fragment = new NotificationsView();
        Bundle                  args = new Bundle();

        args.putInt("page", R.string.view_name_notifications);
        fragment.setArguments(args);

        return (fragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init NewsList Model
//        presenter.getNewsList();
    }

    @Override
    public void onClick(View v) {

    }
}
