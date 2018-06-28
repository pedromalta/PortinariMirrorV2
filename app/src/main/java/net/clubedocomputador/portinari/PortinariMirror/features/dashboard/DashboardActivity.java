package net.clubedocomputador.portinari.PortinariMirror.features.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ProgressBar;


import net.clubedocomputador.portinari.PortinariMirror.BuildConfig;
import net.clubedocomputador.portinari.PortinariMirror.R;
import net.clubedocomputador.portinari.PortinariMirror.features.base.BaseActivity;
import net.clubedocomputador.portinari.PortinariMirror.features.dashboard.widget.DashboardWebViewClient;
import net.clubedocomputador.portinari.PortinariMirror.features.faces.FaceService;
import net.clubedocomputador.portinari.PortinariMirror.injection.component.ActivityComponent;
import net.clubedocomputador.portinari.PortinariMirror.util.Util;

import javax.inject.Inject;

import butterknife.BindView;


public class DashboardActivity extends BaseActivity implements DashboardMvpView {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 69;

    @Inject
    DashboardPresenter presenter;

    @BindView(R.id.dashboard)
    WebView dashboard;

    @BindView(R.id.loading)
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                setupFaceRecognition();
            }
        } else {
            setupFaceRecognition();
        }
        dashboard.getSettings().setJavaScriptEnabled(true);
        dashboard.setWebViewClient(new DashboardWebViewClient(loading));
        dashboard.loadUrl(getUrl());


    }

    private String getUrl() {
        return BuildConfig.API_URL;
    }


    private void setupFaceRecognition() {
        Intent intent = new Intent(this, FaceService.class);
        startService(intent);
    }


    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        //mPreview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        //mPreview.release();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupFaceRecognition();
                } else {
                    Util.DialogFactory.createGenericErrorDialog(this, R.string.dialog_error_message_no_camera);
                }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        presenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        presenter.detachView();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DashboardActivity.class);
    }


}

