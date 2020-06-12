package com.example.financial_savings.modules.dates;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.financial_savings.R;
import com.example.financial_savings.sessions.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateChooseCustomModule {
    private static Session session;
    public static Dialog dialog;

    public static void handlingChooseCustom(Context context, Runnable runnable, boolean isCondition) {
        session = new Session(context);
        dialog = new Dialog(context);
        dialog.setTitle(context.getResources().getString(R.string.choosetime));
        dialog.setContentView(R.layout.budgets_choose_time_custom);
        EditText editTextDateStart = dialog.findViewById(R.id.editTextDateStart_budget_add);
        EditText editTextDateEnd = dialog.findViewById(R.id.editTextDateEnd_budget_add);
        Button buttonChooseTime = dialog.findViewById(R.id.buttonChooseTime);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        eventChooseDate(editTextDateStart, editTextDateEnd, context, isCondition);
        eventChooseTime(buttonChooseTime, editTextDateStart, editTextDateEnd, context, runnable, isCondition);
    }

    private static void eventChooseTime(final Button buttonChooseTime, final EditText editTextDateStart, final EditText editTextDateEnd,
                                        final Context context, final Runnable runnable, final boolean isCondition) {
        buttonChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String dateStart = editTextDateStart.getText().toString();
                    String dateEnd = editTextDateEnd.getText().toString();
                    if(!dateStart.isEmpty() && !dateEnd.isEmpty()) {
                        @SuppressLint("SimpleDateFormat") Date dateS = new SimpleDateFormat("dd/MM/yyyy").parse(dateStart);
                        @SuppressLint("SimpleDateFormat") Date dateE = new SimpleDateFormat("dd/MM/yyyy").parse(dateEnd);
                        if(isCondition) {
                            if(DateBetweenModule.daysBetween(dateE, dateS) > 6) {
                                handlingSession(dateStart, dateEnd, runnable);
                            }
                            else Toast.makeText(context, R.string.condition_time, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(dateE.after(dateS)) {
                                handlingSession(dateStart, dateEnd, runnable);
                            }
                            else Toast.makeText(context, R.string.condition_times, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else Toast.makeText(context, R.string.empty_info, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void handlingSession(String dateStart, String dateEnd, Runnable runnable) {
        session.clearNameTime();
        session.clearTimeStart();
        session.clearTimeEnd();
        session.setTimeStart(dateStart);
        session.setTimeEnd(dateEnd);
        runnable.run();
    }

    private static void eventChooseDate(final EditText editTextDateStart, final EditText editTextDateEnd,
                                        final Context context, boolean isCondition) {
        if(isCondition) {
            editTextDateStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlingChooseDate(editTextDateStart, context, 0);
                }
            });

            editTextDateEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlingChooseDate(editTextDateEnd, context, 1);
                }
            });
        }
        else {
            editTextDateStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlingChooseDate(editTextDateStart, context, -1);
                }
            });

            editTextDateEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlingChooseDate(editTextDateEnd, context, -1);
                }
            });
        }
    }

    private static void handlingChooseDate(final EditText editText, final Context context, final int code) {
        Calendar c = Calendar.getInstance();
        int nYear = c.get(Calendar.YEAR);
        int nMonth = c.get(Calendar.MONTH);
        int nDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                editText.setText(date);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context, callback, nYear, nMonth, nDay);
        if(code == 1)
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        else if(code == 0)
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

}
