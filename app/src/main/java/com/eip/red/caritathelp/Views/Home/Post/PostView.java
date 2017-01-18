package com.eip.red.caritathelp.Views.Home.Post;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Home.Post.PostPresenter;
import com.eip.red.caritathelp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by pierr on 07/09/2016.
 */

public class PostView extends Fragment implements IPostView {

    private PostPresenter   presenter;

    private Unbinder        unbinder;
    private AlertDialog     dialog;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.image_user)          ImageView   userImg;
    @BindView(R.id.text_view_name)      TextView    userName;
    @BindView(R.id.edit_text_post_news) EditText    post;
    @BindView(R.id.progress_bar)        ProgressBar progressBar;

    public static Fragment newInstance(String groupType, int groupId, String groupName, String rights) {
        PostView    fragment = new PostView();
        Bundle args = new Bundle();

        args.putInt("page", R.string.view_name_post);
        args.putString("group type", groupType);
        args.putInt("group id", groupId);
        args.putString("group name", groupName);
        args.putString("rights", rights);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        Bundle  args = getArguments();
        presenter = new PostPresenter(this, user, args.getString("group type"), args.getInt("group id"), args.getString("group name"), args.getString("rights"));
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.fragment_post, container, false));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

         nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
             @Override
             public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                 post.setScrollX(scrollX);
                 post.setScrollY(scrollY);
             }
         });

        post.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nestedScrollView.requestFocusFromTouch();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });

        getActivity().setTitle(getArguments().getInt("page"));
        presenter.initUserProfile(userImg, userName);
    }

    @OnClick(R.id.btn_post)
    public void onClickPostBtn() {
        presenter.postNews(post.getText().toString());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

}
