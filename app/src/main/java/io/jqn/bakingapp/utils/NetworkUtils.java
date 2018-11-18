package io.jqn.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public final class NetworkUtils {
    public static boolean checkNetworkStatus(Context context) {
        ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert mManager != null;
        NetworkInfo activeNetwork = mManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnected();
    }
}
