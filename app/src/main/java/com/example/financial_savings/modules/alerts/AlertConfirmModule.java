package com.example.financial_savings.modules.alerts;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.financial_savings.R;

public class AlertConfirmModule {

    public static void alertDialogConfirm(Context context, int message, final Runnable runnable) {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(context);
        mydialog.setTitle(R.string.notice_dialog);
        mydialog.setMessage(message);
        mydialog.setIcon(R.drawable.android);
        mydialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                runnable.run();
            }
        });
        mydialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = mydialog.create();
        alertDialog.show();
    }
}
