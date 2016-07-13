package com.eip.red.caritathelp.Views.SubMenu.Profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.ProfilePresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pierr on 11/05/2016.
 */

public class ProfileView extends Fragment implements IProfileView, View.OnClickListener {

    private ProfilePresenter    presenter;

    private CircularImageView               profileImg;
    private TextView                        name;
    private HashMap<String, ImageButton>    friendshipBtn;
    private ImageButton                     messageBtn;
    private ProgressBar                     progressBar;
    private AlertDialog                     dialog;

    public static ProfileView newInstance(int id) {
        ProfileView    myFragment = new ProfileView();

        Bundle args = new Bundle();

        args.putInt("page", R.string.view_name_submenu_profile);
        args.putInt("id", id);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     id = getArguments().getInt("id");

        // Init Presenter
        presenter = new ProfilePresenter(this, user, id);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submenu_profile, container, false);

        // Init UI Element
        profileImg = (CircularImageView) view.findViewById(R.id.image);
        name = (TextView) view.findViewById(R.id.name);
        friendshipBtn = new HashMap<>();
        friendshipBtn.put(User.FRIENDSHIP_FRIEND, (ImageButton) view.findViewById(R.id.btn_friendship_friend));
        friendshipBtn.put(User.FRIENDSHIP_NONE, (ImageButton) view.findViewById(R.id.btn_friendship_none));
        friendshipBtn.put(User.FRIENDSHIP_INVITATION_SENT, (ImageButton) view.findViewById(R.id.btn_friendship_invitation_sent));
        friendshipBtn.put(User.FRIENDSHIP_INVITATIONS_RECEIVED_CONFIRM, (ImageButton) view.findViewById(R.id.btn_friendship_confirm));
        friendshipBtn.put(User.FRIENDSHIP_INVITATIONS_RECEIVED_REMOVE, (ImageButton) view.findViewById(R.id.btn_friendship_remove));
        messageBtn = (ImageButton) view.findViewById(R.id.btn_send_message);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Init Listener
        profileImg.setOnClickListener(this);
        for (ImageButton imageButton : friendshipBtn.values()) {
            imageButton.setOnClickListener(this);
        }
        messageBtn.setOnClickListener(this);
        view.findViewById(R.id.btn_friends).setOnClickListener(this);
        view.findViewById(R.id.btn_organisations).setOnClickListener(this);
        view.findViewById(R.id.btn_events).setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Get profile Model
        presenter.getProfile(profileImg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When an Image is picked
        if (requestCode == ProfilePresenter.RESULT_LOAD_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadProfileImg(profileImg, data);
        else if (requestCode == ProfilePresenter.RESULT_CAPTURE_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadProfileImg(profileImg, data);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    public CircularImageView getProfileImg() {
        return profileImg;
    }

    public TextView getName() {
        return name;
    }

    public ImageButton getFriendshipBtn(String key) {
        return friendshipBtn.get(key);
    }

    public ImageButton getMessageBtn() {
        return messageBtn;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
