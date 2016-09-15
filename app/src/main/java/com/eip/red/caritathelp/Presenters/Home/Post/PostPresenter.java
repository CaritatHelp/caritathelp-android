package com.eip.red.caritathelp.Presenters.Home.Post;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Views.Home.Post.PostView;

/**
 * Created by pierr on 07/09/2016.
 */

public class PostPresenter implements IPostPresenter, IOnPostFinishedListener {

    private PostView        view;
    private PostInteractor  interactor;

    public PostPresenter(PostView view, User user, String groupType, int groupId, String groupName, String rights) {
        this.view = view;
        this.interactor = new PostInteractor(view.getContext(), user, groupType, groupId, groupName, rights);
    }

    @Override
    public void initUserProfile(ImageView imageView, TextView name) {
        User    user = interactor.getUser();

        Network.loadImage(view.getContext(), imageView, Network.API_LOCATION + user.getThumb_path(), R.drawable.profile_example);
        name.setText(new StringBuilder(user.getFirstname()).append(" ").append(user.getLastname()));
    }

    @Override
    public void postNews(final String content) {
        if (!TextUtils.isEmpty(content)) {
            String rights = interactor.getRights();
            if (rights != null && (rights.equals(Organisation.ORGANISATION_ADMIN) || rights.equals(Organisation.ORGANISATION_OWNER)))
                showDialogBox(this, content);
            else
                interactor.postNews(this, content, false);
        }

    }

    private void showDialogBox(final IOnPostFinishedListener listener, final String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
        String[]    names = new String[2];
        final int[] idSelected = {0};

        names[0] = interactor.getUser().getFirstname() + " " + interactor.getUser().getLastname();
        names[1] = interactor.getGroupName();

        builder.setTitle("Publier en tant que");
        builder.setCancelable(true);

        builder.setSingleChoiceItems(names, idSelected[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idSelected[0] = which;
            }
        });

        builder.setPositiveButton("Publier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                view.showProgress();
                if (idSelected[0] == 0)
                    interactor.postNews(listener, content, false);
                else
                    interactor.postNews(listener, content, true);
            }
        });

        builder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDialog(String title, String msg) {
        view.hideProgress();
        view.setDialog(title, msg);
    }

    @Override
    public void onSuccessPostNews() {
        view.hideProgress();
        view.getActivity().onBackPressed();
    }
}
