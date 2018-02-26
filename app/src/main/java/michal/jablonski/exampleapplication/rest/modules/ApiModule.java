package michal.jablonski.exampleapplication.rest.modules;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import michal.jablonski.exampleapplication.rest.api.ApiInterfaceDM;
import michal.jablonski.exampleapplication.rest.api.ApiInterfaceGH;
import michal.jablonski.exampleapplication.rest.models.DataModel;
import michal.jablonski.exampleapplication.rest.observables.ObservableCreator;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private final String[] ENDPOINTS = {
            "https://api.dailymotion.com/",
            "https://api.github.com/"
    };

    @Singleton
    @Provides
    ApiInterfaceDM provideDMApi(Gson gson, OkHttpClient client) {
        return
                new Retrofit.Builder()
                        .client(client)
                        .baseUrl(ENDPOINTS[0])
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                        .create(ApiInterfaceDM.class);
    }

    @Provides
    @Singleton
    ApiInterfaceGH provideGHApi(Gson gson, OkHttpClient client) {
        return
                new Retrofit.Builder()
                        .client(client)
                        .baseUrl(ENDPOINTS[1])
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                        .create(ApiInterfaceGH.class);
    }

    @Provides
    @Singleton
    Observable<List<DataModel>> provideMergedObservables(ApiInterfaceDM apiInterfaceDM,
                                                         ApiInterfaceGH apiInterfaceGH) {

        return ObservableCreator.mergeObservables(
                ObservableCreator.createGetUsersObservable(apiInterfaceDM),
                ObservableCreator.createGetUsersObservable(apiInterfaceGH)
        );
    }
}
