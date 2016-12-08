package com.eip.red.caritathelp.Views.SubMenu.Profile.galleryPhoto;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Picture.Picture;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.GalleryPhotoPresenter;
import com.eip.red.caritathelp.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pierr on 08/12/2016.
 */

public class GalleryPhotoRvAdapter extends RecyclerView.Adapter<GalleryPhotoRvAdapter.ItemObjectHolder> {

    private GalleryPhotoView view;
    private GalleryPhotoPresenter presenter;
    private List<Picture> pictures;

    public GalleryPhotoRvAdapter(GalleryPhotoView view, final GalleryPhotoPresenter presenter) {
        this.view = view;
        this.presenter = presenter;
        pictures = new ArrayList<>();
    }

    public class ItemObjectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo) ImageView photo;

        public ItemObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.photo)
        public void onClickPhoto() {
            final Picture picture = pictures.get(getAdapterPosition());

            if (picture != null) {
                if (presenter.isOwner()) {
                    CharSequence[] items = {"Utiliser en tant que photo de profil", "Supprimer"};
                    new AlertDialog.Builder(view.getContext())
                            .setCancelable(true)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0)
                                        presenter.upgradePhoto(picture);
                                    else
                                        presenter.deletePhoto(picture);
                                }
                            })
                            .show();
                }
            }
        }
    }

    @Override
    public ItemObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery_photo_rv_row, parent, false);
        return new ItemObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemObjectHolder holder, int position) {
        Picture picture = pictures.get(position);

        if (picture != null) {
            Ion.with(holder.photo)
                    .error(R.drawable.profile_example)
                    .load(Network.API_LOCATION_2 + picture.getPicturePath().getUrl());
        }
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public void update(List<Picture> pictures) {
        this.pictures.clear();
        this.pictures.addAll(pictures);
        notifyDataSetChanged();
    }

    public void add(Picture picture) {
        this.pictures.add(picture);
        notifyItemInserted(pictures.size() - 1);
    }

    public void remove(int position) {
        this.pictures.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(Picture picture) {
        int position = this.pictures.indexOf(picture);
        this.pictures.remove(position);
        notifyItemRemoved(position);
    }

}
