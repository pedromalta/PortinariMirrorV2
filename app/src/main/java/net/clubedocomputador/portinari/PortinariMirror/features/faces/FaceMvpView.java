package net.clubedocomputador.portinari.PortinariMirror.features.faces;

import net.clubedocomputador.portinari.PortinariMirror.data.model.LoggedIn;
import net.clubedocomputador.portinari.PortinariMirror.features.base.MvpView;

/**
 * Created by pedromalta on 14/03/18.
 */

public interface FaceMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void recognized(LoggedIn loggedIn);

    void showError(Throwable error);
}
