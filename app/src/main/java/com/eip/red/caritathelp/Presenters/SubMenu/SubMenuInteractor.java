package com.eip.red.caritathelp.Presenters.SubMenu;

import android.content.Context;
import android.widget.ImageView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Profile.MainPicture;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.R;

/**
 * Created by pierr on 20/06/2016.
 */

public class SubMenuInteractor {

    private Context context;
    private User    user;

    public SubMenuInteractor(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void removeAccount() {
/*
        JsonObject json = new JsonObject();

        json.addProperty("token", network.getToken());

        Ion.with(context)
                .load("DELETE", Network.API_LOCATION + Network.API_REQUEST_VOLUNTEER_DELETE_ACCOUNT + user.getId())
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception error, JsonObject result) {
                        if (error == null) {
                            MainActivity activity = (MainActivity) getActivity();

                            startActivity(new Intent(activity, SignInView.class));
                            activity.finish();
                        }
                        else
                            new AlertDialog.Builder(getActivity()).setMessage(error.toString()).show();
                    }
                });
*/
    }

    public void loadProfileImg(ImageView imageView) {
        Network.loadImage(context, imageView, Network.API_LOCATION_2 + user.getThumb_path(), R.drawable.profile_example);
    }

    public int getUserId() {
        return user.getId();
    }
}
