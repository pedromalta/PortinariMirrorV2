package net.clubedocomputador.portinari.features.login;

import net.clubedocomputador.portinari.data.model.LoggedIn;
import net.clubedocomputador.portinari.features.base.MvpView;

/**
 * Created by pedromalta on 14/03/18.
 */

public interface LoginMvpView extends MvpView {


    void passwordEmpty();

    void passwordNotValid();

    void usernameEmpty();

    void usernameNotValid();

    void loginSuccess(LoggedIn loggedIn);

    void showLoading();

    void hideLoading();

    void showError(Throwable error);
}
