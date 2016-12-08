package com.eip.red.caritathelp.Views.SubMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.SubMenuPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by pierr on 19/01/2016.
 */

public class SubMenuView extends Fragment implements View.OnClickListener {

    private SubMenuPresenter    presenter;

    public static Fragment newInstance() {
        SubMenuView     fragment = new SubMenuView();
        Bundle          args = new Bundle();

        args.putInt("page", R.string.view_name_submenu);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User & Network model
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();

        // Init Presenter
        presenter = new SubMenuPresenter(this, user);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submenu, container, false);

        // Init Listener
        view.findViewById(R.id.submenu_my_profile).setOnClickListener(this);
        view.findViewById(R.id.submenu_my_organisations).setOnClickListener(this);
        view.findViewById(R.id.submenu_my_events).setOnClickListener(this);
        view.findViewById(R.id.submenu_friends).setOnClickListener(this);
        view.findViewById(R.id.submenu_invitations).setOnClickListener(this);
        view.findViewById(R.id.submenu_account_settings).setOnClickListener(this);
        view.findViewById(R.id.submenu_logout).setOnClickListener(this);
        view.findViewById(R.id.submenu_delete_account).setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init Profile Image
        presenter.initProfileImg((CircularImageView) view.findViewById(R.id.image));
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }


}
