package com.jayfrostjay.propelrrexam.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayfrostjay.propelrrexam.BuildConfig;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCallService {

    private Retrofit retrofit;
    private GsonConverterFactory gsonConverterFactory;
    private OkHttpClient.Builder okHttpClient;
    private final static String BASE_URL = BuildConfig.BASE_URL;

    public ApiCallService(){

    }

    private Object createService(@NotNull Class service){
        return retrofit.create(service);
    }

    public ApiServiceInterface apiCallServiceInstance(){
        okHttpClient = new OkHttpClient.Builder();
        gsonConverterFactory = GsonConverterFactory.create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(gsonConverterFactory)
                .build();
        return retrofit.create(ApiServiceInterface.class);
    }
}
