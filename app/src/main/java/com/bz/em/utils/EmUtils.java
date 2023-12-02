package com.bz.em.utils;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Bellal Hossain
 **/
public class EmUtils {
    private static ProgressDialog progressDialogs;

    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    public static void showProgress(Context context) {
        progressDialogs = new ProgressDialog(context);
        progressDialogs.setMax(100);
        //progressDialogs.setIcon(R.drawable.fevicon);
        progressDialogs.setCancelable(false);
        progressDialogs.setTitle("E-Loan");
        progressDialogs.setMessage("Please wait..");
        progressDialogs.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialogs.show();
    }

    public static void dissmisProgress() {
        if (progressDialogs != null) {
            progressDialogs.dismiss();
        }
    }
}
