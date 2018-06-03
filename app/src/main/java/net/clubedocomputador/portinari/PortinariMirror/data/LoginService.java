package net.clubedocomputador.portinari.PortinariMirror.data;


import javax.inject.Inject;
import javax.inject.Singleton;

import net.clubedocomputador.portinari.PortinariMirror.data.model.LoggedIn;
import net.clubedocomputador.portinari.PortinariMirror.data.model.Login;
import net.clubedocomputador.portinari.PortinariMirror.data.remote.LoginApi;
import io.reactivex.Single;


@Singleton
public class LoginService {

    private LoginApi loginApi;

    @Inject
    public LoginService(LoginApi loginApi) {
        this.loginApi = loginApi;
    }

    public Single<LoggedIn> login(Login login) {
        return loginApi.login(login);
    }

}
