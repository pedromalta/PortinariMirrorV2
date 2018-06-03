package net.clubedocomputador.portinari.features.splash;

import android.os.Bundle;

import javax.inject.Inject;

import net.clubedocomputador.portinari.R;
import net.clubedocomputador.portinari.data.local.PreferencesHelper;
import net.clubedocomputador.portinari.features.base.BaseActivity;
import net.clubedocomputador.portinari.features.login.LoginActivity;
import net.clubedocomputador.portinari.injection.component.ActivityComponent;

import static net.clubedocomputador.portinari.Constants.Preferences.LOGIN_ID;


public class SplashActivity extends BaseActivity {

    @Inject
    PreferencesHelper preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null  == preferences.get(LOGIN_ID)) {
            startActivity(LoginActivity.getStartIntent(this));
        } else {
            startActivity(LoginActivity.getStartIntent(this));
            //startActivity(NextActivity.getStartIntent(this));
        }

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