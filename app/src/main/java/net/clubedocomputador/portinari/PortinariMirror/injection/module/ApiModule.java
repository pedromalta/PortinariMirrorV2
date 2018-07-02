package net.clubedocomputador.portinari.PortinariMirror.injection.module;

import javax.inject.Singleton;

import net.clubedocomputador.portinari.PortinariMirror.data.remote.DashboardApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @Singleton
    DashboardApi provideLoginApi(Retrofit retrofit) {
        return retrofit.create(DashboardApi.class);
    }

}
