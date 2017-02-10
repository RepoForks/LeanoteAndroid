package com.apkdv.leanote.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.apkdv.leanote.Leamonax;
import com.apkdv.leanote.R;

public class NetworkUtils {

    private static NetworkInfo getActiveNetworkInfo(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return null;
        }
        // note that this may return null if no network is currently active
        return cm.getActiveNetworkInfo();
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static void checkNetwork() throws NetworkUnavailableException {
        if (!isNetworkAvailable(Leamonax.getContext())) {
            throw new NetworkUnavailableException();
        }
    }

    public static class NetworkUnavailableException extends IllegalStateException {
        public NetworkUnavailableException() {
            super(Leamonax.getContext().getResources().getString(R.string.network_is_unavailable));
        }
    }
}
