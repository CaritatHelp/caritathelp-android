package com.eip.red.caritathelp.Views.SubMenu.Profile.galleryPhoto;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Picture.Picture;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.GalleryPhotoPresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 08/12/2016.
 */

public class GalleryPhotoView extends Fragment implements GalleryPhoto.View {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private GalleryPhotoPresenter presenter;
    private GalleryPhotoRvAdapter adapter;
    private AlertDialog dialog;

    public static GalleryPhotoView newInstance(int id, GalleryPhotoPresenter.GALLERY_PHOTO_OWNER type, boolean owner) {
        GalleryPhotoView fragment = new GalleryPhotoView();
        Bundle args = new Bundle();

        args.putInt("page", R.string.view_name_submenu_profile_gallery_photo);
        args.putInt("id", id);
        args.putSerializable("type", type);
        args.putBoolean("owner", owner);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User & Organisation Model
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        int id = getArguments().getInt("id");
        GalleryPhotoPresenter.GALLERY_PHOTO_OWNER type = (GalleryPhotoPresenter.GALLERY_PHOTO_OWNER) getArguments().getSerializable("type");
        boolean owner = getArguments().getBoolean("owner");

        // Init Presenter
        presenter = new GalleryPhotoPresenter(this, user, id, type, owner);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View    view = inflater.inflate(R.layout.fragment_gallery_photo, container, false);
        ButterKnife.bind(this, view);

        adapter = new GalleryPhotoRvAdapter(this, presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));

        if (presenter.isOwner())
            ButterKnife.findById(view, R.id.btn_add_photo).setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getInt("page"));
        presenter.getPhotos();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When an Image is picked
        if (requestCode == GalleryPhotoPresenter.RESULT_LOAD_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadPhoto(data, GalleryPhotoPresenter.RESULT_LOAD_IMAGE);
        else if (requestCode == GalleryPhotoPresenter.RESULT_CAPTURE_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadPhoto(data, GalleryPhotoPresenter.RESULT_CAPTURE_IMAGE);
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
        hideProgress();
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void updateRV(List<Picture> pictures) {
        adapter.update(pictures);
    }

    @Override
    public void updateRv(Picture picture, boolean remove) {
        if (remove)
            adapter.remove(picture);
        else
            adapter.add(picture);
        recyclerView.scrollToPosition(adapter.getItemCount());
    }

    @Override
    public void updateRvMainPhoto(Picture picture) {
        adapter.setMainPicture(picture);
    }

    @OnClick(R.id.btn_add_photo)
    public void onClickAddPhoto() {
        presenter.addPhoto();
    }
}
