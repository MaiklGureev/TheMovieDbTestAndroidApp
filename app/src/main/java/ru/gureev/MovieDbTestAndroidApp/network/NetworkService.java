package ru.gureev.MovieDbTestAndroidApp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.gureev.MovieDbTestAndroidApp.AppConfig;
import rx.schedulers.Schedulers;


public class NetworkService {



    private static volatile NetworkService instance;
    private Retrofit retrofit;

    private NetworkService() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.API_BASE_URL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static NetworkService getInstance() {
        NetworkService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (NetworkService.class) {
            if (instance == null) {
                instance = new NetworkService();
            }
            return instance;
        }
    }

    public JSONPlaceHolderApi getJsonPlaceHolderApi() {
        return retrofit.create(JSONPlaceHolderApi.class);
    }


}
