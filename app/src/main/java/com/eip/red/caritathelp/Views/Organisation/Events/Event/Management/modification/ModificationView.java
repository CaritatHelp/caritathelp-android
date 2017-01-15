package com.eip.red.caritathelp.Views.Organisation.Events.Event.Management.modification;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.eip.red.caritathelp.Activities.Main.MainActivity;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Presenters.Organisation.Events.Event.Management.ModificationPresenter;
import com.eip.red.caritathelp.Presenters.SubMenu.MyOrganisations.OrganisationCreation.OrganisationCreationPresenter;
import com.eip.red.caritathelp.Presenters.SubMenu.Profile.ProfilePresenter;
import com.eip.red.caritathelp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by pierr on 14/04/2016.
 */

public class ModificationView extends Fragment implements Modification, View.OnClickListener {

    private ModificationPresenter presenter;

    private EditText        title;
    private TextView        beginDate;
    private TextView        beginTime;
    private TextView        endDate;
    private TextView        endTime;
    private EditText        location;
    private EditText        description;
    private ProgressBar     progressBar;

    private CircularImageView thumbnail;
    private DatePickerDialog    dateBeginDialog;
    private TimePickerDialog    timeBeginDialog;
    private DatePickerDialog    dateEndDialog;
    private TimePickerDialog    timeEndDialog;
    private AlertDialog         dialog;

    public static ModificationView newInstance(int eventId) {
        ModificationView myFragment = new ModificationView();

        Bundle args = new Bundle();
        args.putInt("page", R.string.view_name_organisation_event_management);
        args.putInt("event id", eventId);
        myFragment.setArguments(args);

        return (myFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get User Model & Id Organisation
        User user = ((MainActivity) getActivity()).getModelManager().getUser();
        int eventId = getArguments().getInt("event id");

        // Init Presenter
        presenter = new ModificationPresenter(this, user, eventId);

        // Init Dialog
        dialog = new AlertDialog.Builder(getContext())
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
        View view = inflater.inflate(R.layout.fragment_organisation_event_management_modification, container, false);

        // Init UI Element
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        thumbnail = (CircularImageView) view.findViewById(R.id.thumbnail);
        title = (EditText) view.findViewById(R.id.title);
        beginDate = (TextView) view.findViewById(R.id.begin_date);
        beginTime = (TextView) view.findViewById(R.id.begin_time);
        endDate = (TextView) view.findViewById(R.id.end_date);
        endTime = (TextView) view.findViewById(R.id.end_time);
        location = (EditText) view.findViewById(R.id.location);
        description = (EditText) view.findViewById(R.id.description);

        // Init Listener
        beginDate.setOnClickListener(this);
        beginTime.setOnClickListener(this);
        endDate.setOnClickListener(this);
        endTime.setOnClickListener(this);
        view.findViewById(R.id.btn_photo).setOnClickListener(this);
        view.findViewById(R.id.btn_save).setOnClickListener(this);

        return (view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getArguments().getInt("page"));
        presenter.getEvent();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ProfilePresenter.RESULT_LOAD_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadEventImg(data, OrganisationCreationPresenter.RESULT_LOAD_IMAGE);
        else if (requestCode == ProfilePresenter.RESULT_CAPTURE_IMAGE && resultCode == MainActivity.RESULT_OK && data != null)
            presenter.uploadEventImg(data, OrganisationCreationPresenter.RESULT_CAPTURE_IMAGE);
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
        progressBar.setVisibility(View.INVISIBLE);
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
    public void setDialog(String title, String msg) {
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();

        // Set Title
        String  titleStr = title.getText().toString();
        if (TextUtils.isEmpty(titleStr))
            titleStr = title.getHint().toString();
        data.put("title", titleStr);

        // Set Date Begin
        String  dateBStr = beginDate.getText().toString();
        String  timeBStr = beginTime.getText().toString();
        if (TextUtils.isEmpty(dateBStr))
            dateBStr = beginDate.getHint().toString();
        if (TextUtils.isEmpty(timeBStr))
            timeBStr = beginTime.getHint().toString();
        data.put("date begin", dateBStr + " " + timeBStr);

        // Set Date End
        String  dateEStr = endDate.getText().toString();
        String  timeEStr = endTime.getText().toString();
        if (TextUtils.isEmpty(dateEStr))
            dateEStr = endDate.getHint().toString();
        if (TextUtils.isEmpty(timeEStr))
            timeEStr = endTime.getHint().toString();
        data.put("date end", dateEStr + " " + timeEStr);

        // Set Location
        String  locationStr = location.getText().toString();
        if (TextUtils.isEmpty(locationStr))
            locationStr = location.getHint().toString();
        data.put("location", locationStr);

        // Set Description
        String  descriptionStr = description.getText().toString();
        if (TextUtils.isEmpty(descriptionStr))
            descriptionStr = description.getHint().toString();
        data.put("description", descriptionStr);

        return (data);
    }

    public CircularImageView getThumbnail() {
        return thumbnail;
    }

    public EditText getTitle() {
        return title;
    }

    public TextView getBeginDate() {
        return beginDate;
    }

    public TextView getBeginTime() {
        return beginTime;
    }

    public TextView getEndDate() {
        return endDate;
    }

    public TextView getEndTime() {
        return endTime;
    }

    public EditText getLocation() {
        return location;
    }

    public EditText getDescription() {
        return description;
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

    public ProgressBar getProgressBar() {
        return progressBar;
    }

}
