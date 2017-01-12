package com.eip.red.caritathelp.Views.Organisation.Members;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Organisation.Member;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Members.OrganisationMembersPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by pierr on 25/02/2016.
 */

public class OrganisationMembersListViewAdapter extends BaseAdapter {

    private OrganisationMembersView fragment;
    private OrganisationMembersPresenter presenter;
    private List<Member>            visibleObjects;
    private List<Member>            allObjects;

    public OrganisationMembersListViewAdapter(OrganisationMembersView fragment, OrganisationMembersPresenter presenter) {
        this.fragment = fragment;
        this.presenter = presenter;
        visibleObjects = new ArrayList<>();
        allObjects = new ArrayList<>();
    }

    private class ViewHolder {
        CircularImageView image;
        TextView memberName;
        TextView rights;
        TextView upgradeBtn;
        ImageView kickBtn;
    }

    @Override
    public int getCount() {
        return (visibleObjects.size());
    }

    @Override
    public Object getItem(int position) {
        return (visibleObjects.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = fragment.getActivity().getLayoutInflater().inflate(R.layout.fragment_organisation_members_list_row, null);
            holder.image = (CircularImageView) view.findViewById(R.id.organisation_members_list_row_image);
            holder.memberName = (TextView) view.findViewById(R.id.name);
            holder.rights = (TextView) view.findViewById(R.id.rights);
            holder.upgradeBtn = (TextView) view.findViewById(R.id.btn_upgrade);
            holder.kickBtn = (ImageView) view.findViewById(R.id.btn_kick);
            view.setTag(holder);
        }
        else
            holder = (ViewHolder) view.getTag();

        // Get Member model
        final Member      member = visibleObjects.get(position);

        // Set Members Picture
        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + member.getThumb_path(), R.drawable.profile_example);

        // Set Members Name
        String      name = Tools.upperCaseFirstLetter(member.getFirstname()) + " " + Tools.upperCaseFirstLetter(member.getLastname());
        holder.memberName.setText(name);

        if (presenter.isOwner()) {
            if (!member.getRights().equals(Organisation.ORGANISATION_OWNER)) {
                holder.upgradeBtn.setVisibility(View.VISIBLE);
                holder.kickBtn.setVisibility(View.VISIBLE);

                holder.upgradeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.upgrade(member.getId(), member.getRights(), position);
                    }
                });

                holder.kickBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(view.getContext())
                                .setCancelable(true)
                                .setMessage(String.format("Voulez-vous renvoyer %s ?", member.getFirstname() + " " + member.getLastname()))
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        presenter.kick(member.getId(), position);
                                    }
                                })
                                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .show();
                    }
                });
            }
            else {
                holder.upgradeBtn.setVisibility(View.GONE);
                holder.kickBtn.setVisibility(View.GONE);
            }
        }
        else {
            holder.upgradeBtn.setVisibility(View.GONE);
            holder.kickBtn.setVisibility(View.GONE);
        }

        switch (member.getRights()) {
            case Organisation.ORGANISATION_OWNER:
                holder.rights.setText("Propriétaire");
                break;
            case Organisation.ORGANISATION_ADMIN:
                holder.rights.setText("Administrateur");
                break;
            case Organisation.ORGANISATION_MEMBER:
                holder.rights.setText("Membre");
                break;
        }

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        visibleObjects.clear();
        if (charText.length() == 0)
            visibleObjects.addAll(allObjects);
        else {
            for (Member member : allObjects) {
                String name = member.getFirstname() + " " + member.getLastname();
                if (name.toLowerCase(Locale.getDefault()).contains(charText))
                    visibleObjects.add(member);
            }
        }
        notifyDataSetChanged();
    }

    public void update(List<Member> members) {
        allObjects.clear();
        visibleObjects.clear();

        allObjects.addAll(members);
        visibleObjects.addAll(members);

        notifyDataSetChanged();
    }

    public void upgrade(int position) {
        Member member = visibleObjects.get(position);

        if (member.getRights().equals(Organisation.ORGANISATION_ADMIN))
            member.setRights(Organisation.ORGANISATION_MEMBER);
        else if (member.getRights().equals(Organisation.ORGANISATION_MEMBER))
            member.setRights(Organisation.ORGANISATION_ADMIN);
        notifyDataSetChanged();
    }

    public void kick(int positon) {
        visibleObjects.remove(positon);
        notifyDataSetChanged();
    }

}
