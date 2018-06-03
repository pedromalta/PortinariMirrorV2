package net.clubedocomputador.portinari.injection.component;

import net.clubedocomputador.portinari.features.map.MapActivity;
import net.clubedocomputador.portinari.features.faces.FaceActivity;
import net.clubedocomputador.portinari.features.login.LoginActivity;
import net.clubedocomputador.portinari.features.splash.SplashActivity;
import dagger.Subcomponent;
import net.clubedocomputador.portinari.injection.PerActivity;
import net.clubedocomputador.portinari.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(MapActivity mapActivity);

    void inject(FaceActivity faceActivity);

}