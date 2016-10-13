package com.eip.red.caritathelp.Views.MailBox.chatroomCreation;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Search.Search;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.mailbox.ChatroomCreationPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import mabbas007.tagsedittext.TagsEditText;

/**
 * Created by pierr on 13/10/2016.
 */

public class ChatroomCreationView extends Fragment implements ChatroomCreation.View {

    @BindView(R.id.search) TagsEditText tagsEditText;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    private AlertDialog dialog;

    private ChatroomCreationPresenter presenter;
    private ChatroomCreationRVAdapter adapter;

    public static Fragment newInstance() {
        ChatroomCreationView fragment = new ChatroomCreationView();
        Bundle args = new Bundle();

        args.putInt("page", R.string.view_name_mailbox_chatroom_creation);
        fragment.setArguments(args);

        return (fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        presenter = new ChatroomCreationPresenter(this, user);
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mailbox_chatroom_creation, container, false);
        ButterKnife.bind(this, view);

        RecyclerView recyclerView = ButterKnife.findById(view, R.id.recycler_view);
        adapter = new ChatroomCreationRVAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);

        tagsEditText.setTagsListener(new TagsEditText.TagsEditListener() {
            @Override
            public void onTagsChanged(Collection<String> collection) {
                presenter.setSearch(false);
                presenter.setTags(new ArrayList<>(collection));
            }

            @Override
            public void onEditingFinished() {
            }
        });
        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getInt("page"));

        tagsEditText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(tagsEditText, 0);
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
    public void addTag(List<String> tags) {
        tagsEditText.setTags(tags.toArray(new String[tags.size()]));
    }

    @Override
    public void updateRVData(List<Search> searchList) {
        adapter.update(searchList);
    }


    @OnTextChanged(R.id.search)
    public void onTextChangedSearch(CharSequence text) {
        if (presenter.isSearch() && !TextUtils.isEmpty(text))
            presenter.search(text.toString());
        else
            adapter.removeData();
    }

    @OnClick(R.id.btn_create)
    public void onClickCreateBtn() {
        presenter.goToChatRoomView();
    }
}
