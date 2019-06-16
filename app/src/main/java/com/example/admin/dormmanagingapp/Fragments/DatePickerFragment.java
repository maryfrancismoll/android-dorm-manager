package com.example.admin.dormmanagingapp.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    //public DatePicker datePicker;
    public String selectedDate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
        new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                        "-" + (view.getMonth()+1) +
                        "-" + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();

                // datePicker = view;
                convertDateToString(view);
            }
        };

    private void convertDateToString(DatePicker datePicker){
        this.selectedDate = "" + datePicker.getYear();

        if (datePicker.getMonth() + 1 < 10){
            this.selectedDate += "0";
        }

        this.selectedDate += datePicker.getMonth() + "-" + datePicker.getDayOfMonth();
    }
}
