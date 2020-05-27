package com.jayfrostjay.propelrrexam.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayfrostjay.propelrrexam.BuildConfig;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCallService {

    private Retrofit retrofit;
    private GsonConverterFactory gsonConverterFactory;
    private OkHttpClient okHttpClient;
    private final static String BASE_URL = BuildConfig.BASE_URL;

    public ApiCallService(){
        okHttpClient = new OkHttpClient();
        gsonConverterFactory = GsonConverterFactory.create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    public Object createService(@NotNull Class service){
        return retrofit.create(service);
    }

    public static ApiServiceInterface apiCallServiceInstance(){
        ApiCallService instance = new ApiCallService();
        return (ApiServiceInterface) instance.createService(ApiServiceInterface.class);
    }
}
