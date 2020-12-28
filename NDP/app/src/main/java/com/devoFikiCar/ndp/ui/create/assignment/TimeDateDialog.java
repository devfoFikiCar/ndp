/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.create.assignment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.util.TimeStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeDateDialog extends AppCompatDialogFragment {
    private EditText editText1;
    private EditText editText2;
    private Button time1;
    private Button time2;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.datetime_dialog, null);

        builder.setView(view)
                .setTitle("Time")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TimeStorage.wrongDate()) {
                            Toast.makeText(getContext(), "Wrong date format", Toast.LENGTH_SHORT);
                        }
                    }
                });

        editText1 = (EditText) view.findViewById(R.id.etTimeDate1);
        editText2 = (EditText) view.findViewById(R.id.etTimeDate2);
        time1 = (Button) view.findViewById(R.id.btStartTime1);
        time2 = (Button) view.findViewById(R.id.btStartTime2);

        editText1.setEnabled(false);
        editText1.setText(TimeStorage.toString1());

        editText2.setEnabled(false);
        editText2.setText(TimeStorage.toString2());

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked1");
                showDateTimeDialog1(editText1);
            }
        });

        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked2");
                showDateTimeDialog2(editText2);
            }
        });

        return builder.create();
    }

    private void showDateTimeDialog1(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimeStorage.setYear1(year);
                TimeStorage.setMonth1(month);
                TimeStorage.setDay1(dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        TimeStorage.setHour1(hourOfDay);
                        TimeStorage.setMin1(minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:HH dd-MM-yyyy");
                        editText.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(getActivity(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(getActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDateTimeDialog2(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimeStorage.setYear2(year);
                TimeStorage.setMonth2(month);
                TimeStorage.setDay2(dayOfMonth);

                TimeStorage.setYear1(year);
                TimeStorage.setMonth1(month);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        TimeStorage.setHour2(hourOfDay);
                        TimeStorage.setMin2(minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:HH dd-MM-yyyy");
                        editText.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(getActivity(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(getActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
