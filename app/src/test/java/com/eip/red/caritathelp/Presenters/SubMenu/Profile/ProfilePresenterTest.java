package com.eip.red.caritathelp.Presenters.SubMenu.Profile;

import android.widget.ImageView;

import com.eip.red.caritathelp.Models.Friends.Friend;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Views.SubMenu.Profile.ProfileView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by pierr on 23/06/2016.
 */
public class ProfilePresenterTest {

//    private ProfilePresenter    profilePresenter;
//    private User                user;
    private List<Friend>        friends;

//    @Mock ProfileView       view;
//    @Mock User              user;
    /*@Mock*/ ProfilePresenter presenter;

    @Before
    public void setUp() throws Exception {
        // Init Mock
        MockitoAnnotations.initMocks(this);
//
//        ProfileView view = ProfileView.newInstance(user.getId());
//
//        profilePresenter = new ProfilePresenter(ourMock);// Mockito.mock(ProfilePresenter.class);
//        user = Mockito.mock(User.class);

//        presenter = new ProfilePresenter(view, user, 1);
//
//        Friend  friend = Mockito.mock(Friend.class);
//
//        friends = new ArrayList<>();
//        friends.add(friend);
//        friends.add(friend);

        // define return value for method getUniqueId()
//        when(profilePresenter.getNews()).thenReturn("PASSER");

    }

    @Test
    public void testVerify()  {
        ProfilePresenter    test = Mockito.mock(ProfilePresenter.class);
        User                user = Mockito.mock(User.class);

//        doAnswer(new Answer<Void>() {
//            public Void answer(InvocationOnMock invocation) {
//                Object[] args = invocation.getArguments();
//                System.out.println("called with arguments: " + Arrays.toString(args));
//                return null;
//            }
//        }).when(test).onSuccessGetProfile(user, null);

//        test.getProfile();

        test.getProfile();

//        test.initProfileImg(Mockito.mock(ImageView.class));

        verify(test).onSuccessGetProfile(user, null);
//        verify(test).onSuccessUploadProfileImg();

//        verify(test).onFailureInitProfileImg();
    }

}