package net.clubedocomputador.portinari.PortinariMirror.features.dashboard;

import net.clubedocomputador.portinari.PortinariMirror.data.model.FaceRecognitionResult;
import net.clubedocomputador.portinari.PortinariMirror.features.base.MvpView;

import java.util.List;

/**
 * Created by pedromalta on 14/03/18.
 */

public interface DashboardMvpView extends MvpView {

    void showError(Throwable error);

    void updateDashboard(String html);

    void faceRecognized(List<FaceRecognitionResult> recognizeds);

}
