package net.clubedocomputador.portinari.PortinariMirror.features.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;


import net.clubedocomputador.portinari.PortinariMirror.BuildConfig;
import net.clubedocomputador.portinari.PortinariMirror.R;
import net.clubedocomputador.portinari.PortinariMirror.data.model.FaceDetected;
import net.clubedocomputador.portinari.PortinariMirror.data.model.FaceRecognitionResult;
import net.clubedocomputador.portinari.PortinariMirror.features.base.BaseActivity;
import net.clubedocomputador.portinari.PortinariMirror.features.dashboard.widget.DashboardWebViewClient;
import net.clubedocomputador.portinari.PortinariMirror.features.face.FaceService;
import net.clubedocomputador.portinari.PortinariMirror.injection.component.ActivityComponent;
import net.clubedocomputador.portinari.PortinariMirror.util.AppLogger;
import net.clubedocomputador.portinari.PortinariMirror.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;
import org.joda.time.Instant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private List<FaceRecognitionResult> recognizeds = new ArrayList<>();
    private DateTime lastFaceRecognition = null;

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
        presenter.retrieveDashboard();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFaceDetected(FaceDetected faceDetected) {
        //30 segundos
        Instant instantBefore = new DateTime().minusSeconds(30).toInstant();
        if (lastFaceRecognition == null || lastFaceRecognition.isBefore(instantBefore)) {
            lastFaceRecognition = DateTime.now();
            presenter.faceRecognition(faceDetected.getFace());
        }

    }

    @Override
    public void updateDashboard(String html){
        dashboard.loadDataWithBaseURL(
                BuildConfig.API_URL + "dashboard/",
                html,
                "text/html",
                "utf-8",
                null);
    }

    @Override
    public void faceRecognized(List<FaceRecognitionResult> recognizeds) {
        this.recognizeds = recognizeds;
        this.lastFaceRecognition = DateTime.now();
        Integer id = null;
        if (!recognizeds.isEmpty()){
            id = recognizeds.get(0).id;
        }
        presenter.retrieveDashboardById(id);
    }


    private void setupFaceRecognition() {
        Intent intent = new Intent(this, FaceService.class);
        startService(intent);
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

    public void showError(Throwable throwable){
        lastFaceRecognition = null;
        AppLogger.e(throwable.getMessage());
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}

