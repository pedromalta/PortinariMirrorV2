package net.clubedocomputador.portinari.features.login;


import javax.inject.Inject;

import net.clubedocomputador.portinari.data.LoginService;
import net.clubedocomputador.portinari.data.model.Login;
import net.clubedocomputador.portinari.features.base.BasePresenter;
import net.clubedocomputador.portinari.injection.ConfigPersistent;
import net.clubedocomputador.portinari.util.rx.scheduler.SchedulerUtils;

@ConfigPersistent
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final LoginService service;

    @Inject
    public LoginPresenter(LoginService service) {
        this.service = service;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void login(Login login) {
        checkViewAttached();
        getView().showLoading();
        service
                .login(login)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        loggedIn -> {
                            getView().hideLoading();
                            getView().loginSuccess(loggedIn);
                        },
                        throwable -> {
                            getView().hideLoading();
                            getView().showError(throwable);
                        });
    }
}
