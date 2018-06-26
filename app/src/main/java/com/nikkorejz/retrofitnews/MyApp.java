package com.nikkorejz.retrofitnews;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Если этот код работает, то его написал Никита Громик, если нет,
 * то не знаю кто его писал. (26.06.2018)
 */
public class MyApp extends Application {

    private static NewsApi sNewsApi;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sNewsApi = mRetrofit.create(NewsApi.class);
    }

    public static NewsApi getApi() {
        return sNewsApi;
    }
}
