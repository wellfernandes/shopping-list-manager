package com.github.wellfernandes.shoppinglistmanager.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.github.wellfernandes.shoppinglistmanager.R;

public class Alerts {

    public static void alertConfirm(Context context, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.text_title_dialog);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.text_button_dialog_ok, listener);
        builder.setNegativeButton(R.string.text_button_dialog_cancel, listener);
        builder.show();
    }
}
