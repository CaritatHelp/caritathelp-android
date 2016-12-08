package com.eip.red.caritathelp.Presenters.Sign.Up.Credentials;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;

/**
 * Created by pierr on 23/03/2016.
 */

public interface IOnSignUpCredentialsFinishedListener {

    void onMailError(String error);

    void onPasswordError(String error);

    void onPasswordVerificationError(String error);

    void onSuccess(User user);
}
