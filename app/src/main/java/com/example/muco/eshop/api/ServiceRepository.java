package com.example.muco.eshop.api;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRepository {
    private static LoginService loginService;
    public static OkHttpClient okHttpClient = null;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
//    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static LoginService loginService() {
        if (loginService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://localhost")
                    .addConverterFactory(gsonConverterFactory)
//                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            loginService = retrofit.create(LoginService.class);
        }
        return loginService;
    }
}
