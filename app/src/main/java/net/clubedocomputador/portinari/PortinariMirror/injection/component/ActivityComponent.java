package net.clubedocomputador.portinari.PortinariMirror.injection.component;

import net.clubedocomputador.portinari.PortinariMirror.features.dashboard.DashboardActivity;
import net.clubedocomputador.portinari.PortinariMirror.features.faces.FaceActivity;
import net.clubedocomputador.portinari.PortinariMirror.features.splash.SplashActivity;
import dagger.Subcomponent;

import net.clubedocomputador.portinari.PortinariMirror.injection.PerActivity;
import net.clubedocomputador.portinari.PortinariMirror.injection.module.ActivityModule;


@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(DashboardActivity dashboardActivity);

    void inject(FaceActivity faceActivity);

}