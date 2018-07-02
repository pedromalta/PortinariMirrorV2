package net.clubedocomputador.portinari.PortinariMirror.util;


import android.content.SharedPreferences;

import net.clubedocomputador.portinari.PortinariMirror.Constants;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * This Interceptor add all received Cookies to the app DefaultPreferences.
 * Your implementation on how to save the Cookies on the Preferences MAY VARY.
 * <p>
 * Created by tsuharesu on 4/1/15.
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    private final SharedPreferences preferences;

    public ReceivedCookiesInterceptor(SharedPreferences preferences) {
        this.preferences = preferences;

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));

            this.preferences.edit()
                    .putStringSet(Constants.Preferences.COOKIES, cookies)
                    .apply();
        }

        return originalResponse;
    }
}