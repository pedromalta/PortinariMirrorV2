package net.clubedocomputador.portinari.PortinariMirror.util;

import android.content.SharedPreferences;

import net.clubedocomputador.portinari.PortinariMirror.Constants;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    private final SharedPreferences preferences;

    public AddCookiesInterceptor(SharedPreferences preferences) {
        this.preferences = preferences;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) this.preferences.getStringSet(Constants.Preferences.COOKIES, new HashSet<>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }
}