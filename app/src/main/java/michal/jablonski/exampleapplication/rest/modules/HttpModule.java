package michal.jablonski.exampleapplication.rest.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import static java.util.concurrent.TimeUnit.SECONDS;
import static michal.jablonski.exampleapplication.BuildConfig.DEBUG;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

@Module
public class HttpModule {

    private static final long TIMEOUT = 10;
    private static final TimeUnit TIMEOUT_UNIT = SECONDS;

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        Interceptor loggingInterceptor = getLoggingInterceptor(DEBUG);
        Interceptor apiInterceptor = getApiInterceptor();

        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TIMEOUT_UNIT)
                .writeTimeout(TIMEOUT, TIMEOUT_UNIT)
                .readTimeout(TIMEOUT, TIMEOUT_UNIT)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(apiInterceptor)
                .build();
    }

    @Singleton
    @Provides
    Gson providesGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    private HttpLoggingInterceptor getLoggingInterceptor(boolean enableLogs) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (enableLogs)
            interceptor.setLevel(BODY);
        else
            interceptor.setLevel(NONE);

        return interceptor;
    }

    private Interceptor getApiInterceptor() {
        return chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        };
    }
}
