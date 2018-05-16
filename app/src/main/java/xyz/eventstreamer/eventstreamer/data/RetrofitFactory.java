package xyz.eventstreamer.eventstreamer.data;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.eventstreamer.eventstreamer.BuildConfig;
import xyz.eventstreamer.eventstreamer.data.event.EventService;
import xyz.eventstreamer.eventstreamer.data.post.PostService;
import xyz.eventstreamer.eventstreamer.data.user.UserService;

public class RetrofitFactory {

    private static final int SERVICE_CONNECTION_TIMEOUT = 10;

    private static Retrofit retrofit = null;

    private static Retrofit getRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(SERVICE_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(SERVICE_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(SERVICE_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        if (retrofit == null) {
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
                retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.SERVICE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(httpClient.build())
                        .callbackExecutor(Executors.newCachedThreadPool())
                        .build();
            } else {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.SERVICE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(httpClient.build())
                        .callbackExecutor(Executors.newCachedThreadPool())
                        .build();
            }
        }
        return retrofit;
    }

    public EventService eventService(){
        return getRetrofit().create(EventService.class);
    }

    public PostService postService(){
        return getRetrofit().create(PostService.class);
    }

    public UserService userService(){
        return getRetrofit().create(UserService.class);
    }
}
