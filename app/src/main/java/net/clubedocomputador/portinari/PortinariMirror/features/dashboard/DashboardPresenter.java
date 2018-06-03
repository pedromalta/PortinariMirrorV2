package net.clubedocomputador.portinari.PortinariMirror.features.dashboard;


import net.clubedocomputador.portinari.PortinariMirror.features.base.BasePresenter;
import net.clubedocomputador.portinari.PortinariMirror.injection.ConfigPersistent;

import javax.inject.Inject;

@ConfigPersistent
public class DashboardPresenter extends BasePresenter<DashboardMvpView> {

    @Inject
    public DashboardPresenter() {
    }

    @Override
    public void attachView(DashboardMvpView mvpView) {
        super.attachView(mvpView);
    }

}
