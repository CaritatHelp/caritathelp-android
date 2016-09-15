package com.eip.red.caritathelp.Views.Home.Comment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.News.comment.Comment;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Home.Comment.CommentPresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by pierr on 05/09/2016.
 */
public class CommentView extends Fragment implements ICommentView {

    private CommentPresenter    presenter;

    @BindView(R.id.edit_text_comment)   EditText comment;
    @BindView(R.id.recycler_view)       RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)      SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar)        ProgressBar progressBar;

    private Unbinder            unbinder;
    private LinearLayoutManager linearLayoutManager;
    private AlertDialog         dialog;

    public static Fragment newInstance(int newsId) {
        CommentView     fragment = new CommentView();
        Bundle args = new Bundle();

        args.putInt("page", R.string.view_name_comment);
        args.putInt("news id", newsId);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        presenter = new CommentPresenter(this, user.getToken(), getArguments().getInt("news id"));

        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.fragment_comment, container, false));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        initSwipeRefreshLayout();
        initRecyclerView();

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));

        // Init News Model
        presenter.getComments(false);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initSwipeRefreshLayout() {
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);

        // Init Refresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Get Friends Model
                presenter.getComments(true);
            }
        });
    }

    private void initRecyclerView() {
        // Init RecyclerView
        recyclerView.setAdapter(new CommentRVAdapter(presenter));

        // Init LayoutManager
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Set Options to enable toolbar display/hide
        recyclerView.setNestedScrollingEnabled(true);
//        ViewCompat.setNestedScrollingEnabled(recyclerView, true);
        recyclerView.setHasFixedSize(false);
    }

    @OnClick(R.id.btn_send)
    public void sendComment() {
        String  commentStr = comment.getText().toString();
        if (!TextUtils.isEmpty(commentStr))
            presenter.sendComment(commentStr);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void updateRecyclerViewData(List<Comment> comments) {
        ((CommentRVAdapter) recyclerView.getAdapter()).update(comments);
    }

    @Override
    public void addComment(Comment comment) {
        int index;

        this.comment.setText("");
        ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.comment.getWindowToken(), 0);
        index = ((CommentRVAdapter) recyclerView.getAdapter()).addComment(comment);
        linearLayoutManager.scrollToPosition(index);
    }
}
