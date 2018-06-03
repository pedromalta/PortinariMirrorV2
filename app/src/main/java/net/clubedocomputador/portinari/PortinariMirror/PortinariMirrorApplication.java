package net.clubedocomputador.portinari.PortinariMirror;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.singhajit.sherlock.core.Sherlock;
import com.squareup.leakcanary.LeakCanary;
import com.tspoon.traceur.Traceur;

import net.clubedocomputador.portinari.PortinariMirror.injection.component.AppComponent;
import net.clubedocomputador.portinari.PortinariMirror.injection.component.DaggerAppComponent;
import net.clubedocomputador.portinari.PortinariMirror.injection.module.AppModule;
import net.clubedocomputador.portinari.PortinariMirror.injection.module.NetworkModule;
import net.clubedocomputador.portinari.PortinariMirror.util.AppLogger;

import io.realm.Realm;

public class PortinariMirrorApplication extends MultiDexApplication {

    private AppComponent appComponent;

    public static PortinariMirrorApplication get(Context context) {
        return (PortinariMirrorApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            AppLogger.init();
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
            Sherlock.init(this);
            Traceur.enableLogging();
        }

        Realm.init(this);
    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .networkModule(new NetworkModule(this, BuildConfig.API_URL))
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    @Override protected void attachBaseContext(Context base) { super.attachBaseContext(base); MultiDex.install(this); }

    // Needed to replace the component with a test specific one
    public void setComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}
