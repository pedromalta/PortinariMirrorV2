package net.clubedocomputador.portinari.PortinariMirror.data.remote;

import net.clubedocomputador.portinari.PortinariMirror.data.model.LoggedIn;
import net.clubedocomputador.portinari.PortinariMirror.data.model.Login;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by pedromalta on 10/03/18.
 */

public interface LoginApi {


    @Headers("Content-type: application/json")
    @POST("login")
    Single<LoggedIn> login(@Body Login login);

    @POST("logout")
    Single<Void> logout();

}
