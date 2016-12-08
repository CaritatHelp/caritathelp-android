package com.eip.red.caritathelp.Views.Organisation.Management.OrganisationEventCreation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.Network;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Management.EventCreation.OrganisationEventCreationPresenter;
import com.eip.red.caritathelp.R;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by pierr on 18/03/2016.
 */

public class OrganisationEventCreationView extends Fragment implements IOrganisationEventCreationView, View.OnClickListener{

    private OrganisationEventCreationPresenter presenter;

    private EditText        title;
    private TextView        beginDate;
    private TextView        beginTime;
    private TextView        endDate;
    private TextView        endTime;
    private EditText        location;
    private EditText        description;
    private ProgressBar     progressBar;

    private DatePickerDialog    dateBeginDialog;
    private TimePickerDialog    timeBeginDialog;
    private DatePickerDialog    dateEndDialog;
    private TimePickerDialog    timeEndDialog;
    private AlertDialog         dialog;

    public static OrganisationEventCreationView newInstance(int idOrganisation) {
        OrganisationEventCreationView    myFragment = new OrganisationEventCreationView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_submenu_my_organisations_creation);
        args.putInt("organisation id", idOrganisation);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model & Id Organisation
        User    user = ((MainActivity) getActivity()).getModelManager().getUser();
        int     organisationId = getArguments().getInt("organisation id");

        // Init Presenter
        presenter = new OrganisationEventCreationPresenter(this, user.getToken(), organisationId);

        // Init Dialog
        dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .create();

        // Init Date Picker Dialog
        initDateDialogs();
    }

    private void initDateDialogs() {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.FRANCE);
        final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();

        // Init Date Begin Dialog
        dateBeginDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                String date = "Le " + dateFormatter.format(newDate.getTime());
                beginDate.setText(date);
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateBeginDialog.setCancelable(true);
        dateBeginDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());


        // Init Time Begin Dialog
        timeBeginDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);

                String time = "à " + timeFormatter.format(newDate.getTime());
                beginTime.setText(time);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timeBeginDialog.setCancelable(true);


        // Init Date End Dialog
        dateEndDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                String date = "Le " + dateFormatter.format(newDate.getTime());
                endDate.setText(date);
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateEndDialog.setCancelable(true);
        dateEndDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Init Time End Dialog
        timeEndDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);

                String time = "à " + timeFormatter.format(newDate.getTime());
                endTime.setText(time);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timeEndDialog.setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View    view = inflater.inflate(R.layout.fragment_organisation_management_event_creation, container, false);

        // Init UI Elements
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        title = (EditText) view.findViewById(R.id.title);
        beginDate = (TextView) view.findViewById(R.id.begin_date);
        beginTime = (TextView) view.findViewById(R.id.begin_time);
        endDate = (TextView) view.findViewById(R.id.end_date);
        endTime = (TextView) view.findViewById(R.id.end_time);
        location = (EditText) view.findViewById(R.id.location);
        description = (EditText) view.findViewById(R.id.description);

        // Init Listener
        view.findViewById(R.id.btn_create).setOnClickListener(this);
        beginDate.setOnClickListener(this);
        beginTime.setOnClickListener(this);
        endDate.setOnClickListener(this);
        endTime.setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ToolBar Title
        getActivity().setTitle(getArguments().getInt("page"));
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v.getId());
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
    public void setTitleError(String error) {
        title.setError(error);
    }

    @Override
    public void setPhotoError(String error) {
    }

    @Override
    public void setBeginDateError(String error) {
        beginDate.setError(error);
    }

    @Override
    public void setEndDateError(String error) {
        endDate.setError(error);
    }

    @Override
    public void setLocationError(String error) {
        location.setError(error);
    }

    @Override
    public void setDescriptionError(String error) {
        description.setError(error);
    }

    @Override
    public void setDialogError(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    public HashMap<String, String>  getData() {
        HashMap<String, String> data = new HashMap<>();

        data.put("title", title.getText().toString());
        data.put("date begin", beginDate.getText().toString() + " " + beginTime.getText().toString());
        data.put("date end", endDate.getText().toString() + " " + endTime.getText().toString());
        data.put("location", location.getText().toString());
        data.put("description", description.getText().toString());

        return (data);
    }

    public DatePickerDialog getDateBeginDialog() {
        return dateBeginDialog;
    }

    public TimePickerDialog getTimeBeginDialog() {
        return timeBeginDialog;
    }

    public DatePickerDialog getDateEndDialog() {
        return dateEndDialog;
    }

    public TimePickerDialog getTimeEndDialog() {
        return timeEndDialog;
    }
}
