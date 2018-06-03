package net.clubedocomputador.portinari.PortinariMirror.features.dashboard;

import net.clubedocomputador.portinari.PortinariMirror.features.base.MvpView;

/**
 * Created by pedromalta on 14/03/18.
 */

public interface DashboardMvpView extends MvpView {

    void showError(Throwable error);
}
