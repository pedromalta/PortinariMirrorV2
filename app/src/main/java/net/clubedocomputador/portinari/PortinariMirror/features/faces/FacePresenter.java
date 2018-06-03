package net.clubedocomputador.portinari.PortinariMirror.features.faces;



import javax.inject.Inject;

import net.clubedocomputador.portinari.PortinariMirror.data.LoginService;
import net.clubedocomputador.portinari.PortinariMirror.data.model.Login;
import net.clubedocomputador.portinari.PortinariMirror.features.base.BasePresenter;
import net.clubedocomputador.portinari.PortinariMirror.injection.ConfigPersistent;
import net.clubedocomputador.portinari.PortinariMirror.util.rx.scheduler.SchedulerUtils;

@ConfigPersistent
public class FacePresenter extends BasePresenter<FaceMvpView> {

    private final LoginService service;

    @Inject
    public FacePresenter(LoginService service) {
        this.service = service;
    }

    @Override
    public void attachView(FaceMvpView mvpView) {
        super.attachView(mvpView);
    }


    public void recognise(Login login) {
        checkViewAttached();
        getView().showLoading();
        //TODO
        service
                .login(login)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        loggedIn -> {
                            getView().recognized(loggedIn);
                        },
                        throwable -> {
                            getView().showError(throwable);
                        });
    }
}
