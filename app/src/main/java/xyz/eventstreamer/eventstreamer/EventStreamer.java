package xyz.eventstreamer.eventstreamer;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import xyz.eventstreamer.eventstreamer.data.RetrofitFactory;
import xyz.eventstreamer.eventstreamer.util.SharedPreferenceUtil;

public class EventStreamer extends Application {

    private static EventStreamer instance;
    private RetrofitFactory retrofitFactory;
    private SharedPreferenceUtil sharedPreferenceUtil;

    public static EventStreamer getInstance() {
        return instance;
    }

    private Socket socket;{
        try {
            socket = IO.socket(BuildConfig.SOCKET_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        retrofitFactory = new RetrofitFactory();
        sharedPreferenceUtil = new SharedPreferenceUtil(this);
    }

    public RetrofitFactory getRetrofit() {
        if (retrofitFactory != null) {
            return retrofitFactory;
        } else {
            throw new RuntimeException("Retrofit instance must be initialized");
        }
    }

    public SharedPreferenceUtil getSharedPreferenceUtil() {
        if (sharedPreferenceUtil != null) {
            return sharedPreferenceUtil;
        } else {
            throw new RuntimeException("SharedPreferenceUtil instance must be initialized");
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
