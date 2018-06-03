package net.clubedocomputador.portinari.PortinariMirror.features.splash;

import android.os.Bundle;

import net.clubedocomputador.portinari.PortinariMirror.features.base.BaseActivity;
import net.clubedocomputador.portinari.PortinariMirror.features.dashboard.DashboardActivity;
import net.clubedocomputador.portinari.PortinariMirror.injection.component.ActivityComponent;



public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(DashboardActivity.getStartIntent(this));
        finish();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {

    }

    @Override
    protected void detachPresenter() {

    }
}