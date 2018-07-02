package net.clubedocomputador.portinari.PortinariMirror.features.face;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaActionSound;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import net.clubedocomputador.portinari.PortinariMirror.data.model.FaceDetected;
import net.clubedocomputador.portinari.PortinariMirror.util.AppLogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.schedulers.Schedulers;

public class FaceService extends Service {

    private CameraSource mCameraSource = null;

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.e("CREATED Service");
        FaceDetector detector = new FaceDetector.Builder(getApplicationContext())
                .setProminentFaceOnly(true) // optimize for single, relatively large face
                .setTrackingEnabled(true) // enable face tracking
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setMode(FaceDetector.FAST_MODE) // for one face this is OK
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new TrackerProcessorFactory())
                        .build());


        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            AppLogger.e("Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(getApplicationContext(), detector)
                .setRequestedPreviewSize(1024, 768)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();


        startCameraSource();
    }


    @SuppressLint("MissingPermission")
    private void startCameraSource() {
        if (mCameraSource != null) {
            try {

                mCameraSource.start();
            } catch (IOException e) {
                //Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private void takePicture() {

        mCameraSource.takePicture(
                () -> {},
                (bytes) -> {
                    mute(false);
                    processBitmap(bytes);
                });
    }

    private void processBitmap(byte[] bytes){

        ObservableCreate<byte[]> proccessBytes = new ObservableCreate<>(e -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bitmap = rotateImage(bitmap, 270);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            e.onNext(baos.toByteArray());
            e.onComplete();
        });

        proccessBytes
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(FaceDetected::new, throwable -> AppLogger.e(throwable.getMessage()));


    }

    private void mute(Boolean mute){
        AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mgr.setStreamMute(AudioManager.STREAM_SYSTEM, mute);
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TrackerProcessorFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new TrackerProcessor();
        }
    }

    private class TrackerProcessor extends Tracker<Face> {

        private int elapsedFrameCount = 0;
        private static final int frameCountForPicture = 10;

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            AppLogger.d("new face!");
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            AppLogger.d("face!");
            elapsedFrameCount++;
            if (elapsedFrameCount > frameCountForPicture) {
                elapsedFrameCount = 0;
                mute(true);
                takePicture();
            }

        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            AppLogger.d("missing face!");
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            AppLogger.d("face gone!");
        }


    }
}

