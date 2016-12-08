package com.eip.red.caritathelp.Views.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.Search.Search;
import com.eip.red.caritathelp.Presenters.Search.MySearchPresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierr on 19/04/2016.
 */

public class MySearchRVAdapter extends RecyclerView.Adapter<MySearchRVAdapter.DataObjectHolder> {

    private MySearchPresenter   presenter;
    private List<Search> searches;

    public MySearchRVAdapter(MySearchPresenter presenter) {
        this.presenter = presenter;
        searches = new ArrayList<>();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircularImageView   image;
        TextView            name;
        TextView            result;
        ImageButton         add;

        public DataObjectHolder(View itemView) {
            super(itemView);

            // Init UI Element
            image = (CircularImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            result = (TextView) itemView.findViewById(R.id.result);
            add = (ImageButton) itemView.findViewById(R.id.btn_add);

            // Init Listener
            itemView.setOnClickListener(this);
            image.setOnClickListener(this);
            name.setOnClickListener(this);
            add.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Search search = searches.get(getAdapterPosition());
            presenter.onClick(v.getId(), search);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_search_rv_row, parent, false);
        DataObjectHolder    holder = new DataObjectHolder(view);

        return (holder);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Search search = searches.get(position);
        String      resultType = search.getResult_type();
        String      right = search.getRights();

        // Set Name
        holder.name.setText(search.getName());

        // Set Image
        Network.loadImage(holder.image.getContext(), holder.image, Network.API_LOCATION_2 + search.getThumb_path(), R.drawable.profile_example);

        // Set RIGHTS
        System.out.println("NAME : " + search.getName());
        System.out.println("RIGHT : " + search.getRights());
        switch (resultType) {
            case Search.RESULT_TYPE_VOLUNTEER:
                if (right.equals(Search.RIGHTS_VOLUNTEER_NO_FRIEND))
                    holder.add.setVisibility(View.VISIBLE);
                else
                    holder.add.setVisibility(View.INVISIBLE);
                break;
            case Search.RESULT_TYPE_ASSOC:
                if (right == null)
                    holder.add.setVisibility(View.VISIBLE);
                else
                    holder.add.setVisibility(View.INVISIBLE);
                break;
            case Search.RESULT_TYPE_EVENT:
                if (right == null)
                    holder.add.setVisibility(View.VISIBLE);
                else
                    holder.add.setVisibility(View.INVISIBLE);
                break;
        }

        // Set Result MSG (Invitation sent...)
        String result = search.getResult();
        if (result != null) {
            // Set Msg Txt & Visibility
            holder.result.setText(result);
            holder.result.setVisibility(View.VISIBLE);

            // Set Button Visibility
            holder.add.setVisibility(View.INVISIBLE);
        }
        else {
            // Set Result & Button Visibility
            holder.result.setVisibility(View.GONE);
//            holder.add.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (searches.size());
    }

    public void update(List<Search> searches) {
        this.searches.clear();

        if (searches != null)
            this.searches.addAll(searches);

        notifyDataSetChanged();
    }
}
