package com.eip.red.caritathelp.Views.Organisation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.News.News;
import com.eip.red.caritathelp.Models.Organisation.Organisation;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.MyWidgets.DividerItemDecoration;
import com.eip.red.caritathelp.Presenters.Organisation.OrganisationPresenter;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pierr on 18/02/2016.
 */

public class OrganisationView extends Fragment implements IOrganisationView, View.OnClickListener {

    private OrganisationPresenter           presenter;

    private CircularImageView               logo;
    private HashMap<String, ImageButton>    membershipBtn;
    private OrganisationRVAdapter           adapter;
    private Unbinder                        unbinder;
    private AlertDialog                     dialog;

    @BindView(R.id.btn_management) ImageButton managementBtn;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar) ProgressBar         progressBar;

    public static OrganisationView newInstance(int id, String name) {
        OrganisationView    myFragment = new OrganisationView();

        Bundle args = new Bundle();

        args.putString("page", name);
        args.putInt("id", id);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User & Organisation Model
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     id = getArguments().getInt("id");

        // Init Presenter
        presenter = new OrganisationPresenter(this, user, id);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_organisation, container, false);

        // Init UI Element
        logo = (CircularImageView) view.findViewById(R.id.logo);
        membershipBtn = new HashMap<>();
        membershipBtn.put(Organisation.ORGANISATION_OWNER, (ImageButton) view.findViewById(R.id.btn_membership_owner));
        membershipBtn.put(Organisation.ORGANISATION_ADMIN, (ImageButton) view.findViewById(R.id.btn_membership_admin));
        membershipBtn.put(Organisation.ORGANISATION_MEMBER, (ImageButton) view.findViewById(R.id.btn_membership_member));
        membershipBtn.put(Organisation.ORGANISATION_NONE, (ImageButton) view.findViewById(R.id.btn_membership_none));
        membershipBtn.put(Organisation.ORGANISATION_INVITED_CONFIRM, (ImageButton) view.findViewById(R.id.btn_membership_confirm));
        membershipBtn.put(Organisation.ORGANISATION_INVITED_REMOVE, (ImageButton) view.findViewById(R.id.btn_membership_remove));
        membershipBtn.put(Organisation.ORGANISATION_WAITING, (ImageButton) view.findViewById(R.id.btn_membership_waiting));

        // Init Listener
        for (ImageButton imageButton : membershipBtn.values()) {
            imageButton.setOnClickListener(this);
        }
        view.findViewById(R.id.btn_follow).setOnClickListener(this);
        view.findViewById(R.id.btn_post).setOnClickListener(this);
        view.findViewById(R.id.btn_members).setOnClickListener(this);
        view.findViewById(R.id.btn_management).setOnClickListener(this);
        view.findViewById(R.id.btn_events).setOnClickListener(this);
        view.findViewById(R.id.btn_informations).setOnClickListener(this);

        unbinder = ButterKnife.bind(this, view);
        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(Tools.upperCaseFirstLetter(getArguments().getString("page")));
        initRecyclerView(view);
        initSwipeRefreshLayout();
        presenter.getData();
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.icons);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getNews(true);
            }
        });
    }

    private void initRecyclerView(View view) {
        // Init RecyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new OrganisationRVAdapter(presenter);
        recyclerView.setAdapter(adapter);

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
    public void onClick(View v) {
        presenter.onClick(v.getId());
    }


    @Override
    public void setLogoPosition(String right) {
        if (right != null && right.equals("owner")) {
            // Set Logo Position
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) logo.getLayoutParams();

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            logo.setLayoutParams(layoutParams);

            // Set Management Btn Visibility
            managementBtn.setVisibility(View.VISIBLE);
        }
        else {
            // Set Logo Position
            logo.bringToFront();
            logo.invalidate();

            // Set Management Btn Visibility
            managementBtn.setVisibility(View.INVISIBLE);
        }
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
    public void updateRV(List<News> newsList) {
        adapter.update(newsList);
    }

    @Override
    public void addNews(News news) {
        adapter.addNews(news);
    }

    public CircularImageView getLogo() {
        return logo;
    }

    public ImageButton getMembershipBtn(String key) {
        return membershipBtn.get(key);
    }

}
