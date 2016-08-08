package com.eip.red.caritathelp.Views.Organisation.Events.Event;

import android.app.AlertDialog;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.OrganisationEventPresenter;
import com.eip.red.caritathelp.R;

import java.util.List;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventView extends Fragment implements IOrganisationEventView, View.OnClickListener {

    private OrganisationEventPresenter presenter;

    private RecyclerView    recyclerView;
    private ImageButton     joinBtn;
    private ImageButton     quitBtn;
    private ImageButton     managementBtn;
    private ProgressBar     progressBar;
    private AlertDialog     dialog;

    public static OrganisationEventView newInstance(int eventId, String title) {
        OrganisationEventView    myFragment = new OrganisationEventView();

        Bundle args = new Bundle();
        args.putString("page", title);
        args.putInt("event id", eventId);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Network Model & Id Organisation
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     eventId = getArguments().getInt("event id");

        // Init Presenter
        presenter = new OrganisationEventPresenter(this, user.getToken(), eventId);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_organisation_event, container, false);

        // Init UI Element
        joinBtn = (ImageButton) view.findViewById(R.id.btn_join);
        quitBtn = (ImageButton) view.findViewById(R.id.btn_quit);
        managementBtn = (ImageButton) view.findViewById(R.id.btn_management);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Init Image Filter (Darken the image)
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        LightingColorFilter lcf = new LightingColorFilter(0xFF888888, 0x00222222);
        imageView.setColorFilter(lcf);

        // Init RecyclerView
        initRecyclerView(view);

        // Init Listener
        joinBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
        view.findViewById(R.id.btn_guests).setOnClickListener(this);
        view.findViewById(R.id.btn_informations).setOnClickListener(this);
        managementBtn.setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Event Model
        presenter.getEvent();

        // Init News Model
//        presenter.getNews();
    }

    private void initRecyclerView(View view) {
        // Init RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new OrganisationEventRVAdapter());

        // Init LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set Options to enable toolbar display/hide
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        // Init Divider (between items)
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
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
    public void setDialogError(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void updateRecyclerView(List<News> news) {
//        ((OrganisationEventRVAdapter) recyclerView.getAdapter()).update(news);
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }

    public ImageButton getJoinBtn() {
        return joinBtn;
    }

    public ImageButton getQuitBtn() {
        return quitBtn;
    }

    public ImageButton getManagementBtn() {
        return managementBtn;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
