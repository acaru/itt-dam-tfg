package com.ITTDAM.bookncut.Adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    private int initialYear  = -1;
    private int initialMonth = -1;
    private int initialDay = -1;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener,int year,int month,int day) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        fragment.initialYear=year;
        fragment.initialMonth=month;
        fragment.initialDay=day;
        return fragment;
    }
    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        // Initial selected value
        if (initialYear == -1)
            initialYear = year - 18;

        if (initialMonth == -1)
            initialMonth = c.get(Calendar.MONTH);

        if (initialDay == -1)
            initialDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dialog = new DatePickerDialog(getActivity(),listener,  initialYear, initialMonth, initialDay);
        // Min and max date
        c.setTime(c.getTime());
        dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c.set(Calendar.MONTH, c.getTime().getMonth()+1);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        // Create a new instance of DatePickerDialog and return it
        return dialog;
    }

}
