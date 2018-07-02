package net.clubedocomputador.portinari.PortinariMirror.features.dashboard;


import net.clubedocomputador.portinari.PortinariMirror.data.DashboardService;
import net.clubedocomputador.portinari.PortinariMirror.data.model.FaceRecognitionResult;
import net.clubedocomputador.portinari.PortinariMirror.features.base.BasePresenter;
import net.clubedocomputador.portinari.PortinariMirror.injection.ConfigPersistent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class DashboardPresenter extends BasePresenter<DashboardMvpView> {

    private DashboardService service;
    private String csrfToken;

    @Inject
    public DashboardPresenter(DashboardService service) {
        this.service = service;
    }

    public void faceRecognition(byte[] face) {
        checkViewAttached();
        service.faceRecognition(face, csrfToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        recognizeds -> {
                            getView().faceRecognized(recognizeds);
                        },
                        throwable -> getView().showError(throwable));

    }

    public void retrieveDashboardByFace(byte[] face) {
        checkViewAttached();
        service.getDashboard(face, csrfToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(this::extractCSRFToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        html -> getView().updateDashboard(html),
                        throwable -> getView().showError(throwable));

    }

    private Single<String> extractCSRFToken(String html) {

        Pattern p = Pattern.compile("<input type=\"hidden\" name=\"_token\" value=\"(\\S+)\">");
        Matcher m = p.matcher(html);

        // if we find a match, get the group
        if (m.find()) {
            // get the matching group
            csrfToken = m.group(1);
        } else {
            csrfToken = null;
        }

        return Single.just(html);
    }

    public void retrieveDashboardById(Integer id) {
        checkViewAttached();
        service.getDashboardById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(this::extractCSRFToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        html -> getView().updateDashboard(html),
                        throwable -> getView().showError(throwable));
    }

    public void retrieveDashboard() {
        retrieveDashboardByFace(null);
    }



    @Override
    public void attachView(DashboardMvpView mvpView) {
        super.attachView(mvpView);
    }


}
