package net.clubedocomputador.portinari.injection.module;

import javax.inject.Singleton;

import net.clubedocomputador.portinari.data.remote.LoginApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @Singleton
    LoginApi provideLoginApi(Retrofit retrofit) {
        return retrofit.create(LoginApi.class);
    }

}
