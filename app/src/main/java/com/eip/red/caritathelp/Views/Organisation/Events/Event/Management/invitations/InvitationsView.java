package com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.invitations;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by pierr on 15/01/2017.
 */

public class InvitationsView extends Fragment {

    public static InvitationsView newInstance(int eventId) {
        InvitationsView fragment = new InvitationsView();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
}
