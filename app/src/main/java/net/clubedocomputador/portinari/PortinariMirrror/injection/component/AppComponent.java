package net.clubedocomputador.portinari.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import net.clubedocomputador.portinari.data.LoginService;
import net.clubedocomputador.portinari.data.local.InstanceHolder;
import net.clubedocomputador.portinari.data.local.PreferencesHelper;
import net.clubedocomputador.portinari.util.location.LocationTracker;
import dagger.Component;
import net.clubedocomputador.portinari.injection.ApplicationContext;
import net.clubedocomputador.portinari.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    LocationTracker locationTracker();

    LoginService loginService();

    PreferencesHelper preferencesHelper();

    InstanceHolder instanceHolder();
}
