package net.clubedocomputador.portinari.PortinariMirror.data.remote;

import net.clubedocomputador.portinari.PortinariMirror.data.model.FaceRecognitionResult;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


/**
 * Created by pedromalta on 10/03/18.
 */

public interface DashboardApi {

    @Multipart
    @POST("dashboard")
    Single<String> getDashboardByFace(@Part MultipartBody.Part image);

    @GET("dashboard/{userId}")
    Single<String> getDashboardById(@Path("userId") Integer userId);


    @Multipart
    @POST("face-recognition")
    Single<List<FaceRecognitionResult>> faceRecognition(@Part MultipartBody.Part image);

    @GET("dashboard")
    Single<String> getDashboardByFace();


}
