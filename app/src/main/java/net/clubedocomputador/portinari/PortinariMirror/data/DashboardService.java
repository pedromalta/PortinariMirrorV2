package net.clubedocomputador.portinari.PortinariMirror.data;


import javax.inject.Inject;
import javax.inject.Singleton;

import net.clubedocomputador.portinari.PortinariMirror.data.model.FaceRecognitionResult;
import net.clubedocomputador.portinari.PortinariMirror.data.remote.DashboardApi;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@Singleton
public class DashboardService {

    private DashboardApi dashboardApi;

    @Inject
    public DashboardService(DashboardApi dashboardApi) {
        this.dashboardApi = dashboardApi;
    }

    public Single<String> getDashboard(byte[] face, String csrf) {
        if (face == null || csrf == null)
            return dashboardApi.getDashboardByFace();

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), face);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", "face.jpg", reqFile);
        RequestBody csrfToken = RequestBody.create(MediaType.parse("application/octet-stream"), csrf);

        return dashboardApi.getDashboardByFace(image);
    }

    public Single<String> getDashboardById(Integer id) {
        return dashboardApi.getDashboardById(id);
    }

    public Single<List<FaceRecognitionResult>> faceRecognition(byte[] face, String csrf) {

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), face);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", "face.jpg", reqFile);
        RequestBody csrfToken = RequestBody.create(MediaType.parse("application/octet-stream"), csrf);

        return dashboardApi.faceRecognition(image);
    }

}
