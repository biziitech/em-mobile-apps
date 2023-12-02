package com.bz.em.utils;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Bellal Hossain
 **/
public class EmNetworkStateCheck {

    public static boolean NETWORK_CONNECTION_STATUS = false;
    private Context context;

    public final boolean checkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();

        // Check for network connections
        if (cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else if (cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
        }
        return false;

    }

    public static boolean checkNetConnection(Activity activity) {

        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
