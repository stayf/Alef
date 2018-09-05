package com.stayfprod.alef.data.remote;

import com.stayfprod.alef.App;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final ApiServer API;
    private static final Retrofit RETROFIT;

    static {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(6, TimeUnit.SECONDS)
                .connectTimeout(6, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .baseUrl(App.API_ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create());

        API = (RETROFIT = builder.build()).create(ApiServer.class);
    }

    public static ApiServer getApi() {
        return API;
    }
}
