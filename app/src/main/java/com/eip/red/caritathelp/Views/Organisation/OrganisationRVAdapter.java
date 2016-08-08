package com.eip.red.caritathelp.Views.Organisation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Presenters.Organisation.OrganisationPresenter;
import com.eip.red.caritathelp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 28/03/2016.
 */

public class OrganisationRVAdapter extends RecyclerView.Adapter<OrganisationRVAdapter.DataObjectHolder> {

    private OrganisationPresenter presenter;

    private List<News>  newsList;

    public OrganisationRVAdapter(OrganisationPresenter presenter) {
        this.presenter = presenter;
        newsList = new ArrayList<>();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            name = (TextView) itemView.findViewById(R.id.my_organisations_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            News    news = newsList.get(getAdapterPosition());
//
//            if (news != null)
//                presenter.goToNewsView(news);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organisation_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
//        holder.name.setText(visibleObjects.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void update(List<News> newsList2) {
        newsList.clear();
        newsList.addAll(newsList2);
        notifyDataSetChanged();
    }

}
