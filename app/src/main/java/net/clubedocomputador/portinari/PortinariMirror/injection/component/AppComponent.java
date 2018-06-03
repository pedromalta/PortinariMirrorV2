package net.clubedocomputador.portinari.PortinariMirror.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import net.clubedocomputador.portinari.PortinariMirror.data.LoginService;
import net.clubedocomputador.portinari.PortinariMirror.data.local.InstanceHolder;
import net.clubedocomputador.portinari.PortinariMirror.data.local.PreferencesHelper;
import dagger.Component;
import net.clubedocomputador.portinari.PortinariMirror.injection.ApplicationContext;
import net.clubedocomputador.portinari.PortinariMirror.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    LoginService loginService();

    PreferencesHelper preferencesHelper();

    InstanceHolder instanceHolder();
}
