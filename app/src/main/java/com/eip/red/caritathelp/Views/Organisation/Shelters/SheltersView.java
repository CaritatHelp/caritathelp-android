package com.eip.red.caritathelp.Views.Organisation.Shelters;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Shelter.Shelter;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.SheltersPresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pierr on 03/12/2016.
 */

public class SheltersView extends Fragment implements Shelters.View {

    private SheltersPresenter presenter;

    private SheltersRvAdapter adapter;
    private AlertDialog dialog;

    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public static SheltersView newInstance(int id) {
        SheltersView fragment = new SheltersView();
        Bundle args = new Bundle();

        args.putInt("organisation id", id);
        args.putInt("page", R.string.view_name_organisation_shelters);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User & Organisation Model
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        int id = getArguments().getInt("organisation id");

        // Init Presenter
        presenter = new SheltersPresenter(this, user, id);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View    view = inflater.inflate(R.layout.fragment_organisation_shelters, container, false);
        ButterKnife.bind(this, view);

        RecyclerView recyclerView = ButterKnife.findById(view, R.id.recycler_view);
        adapter = new SheltersRvAdapter(getContext(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getString("page"));
        presenter.getShelters();
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
    public void updateRV(List<Shelter> shelters) {
        adapter.update(shelters);
        hideProgress();
    }
}
