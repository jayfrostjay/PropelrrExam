package com.jayfrostjay.propelrrexam.manager;

import android.util.Log;

import com.jayfrostjay.propelrrexam.api.ApiCallService;
import com.jayfrostjay.propelrrexam.api.ApiCallback;
import com.jayfrostjay.propelrrexam.api.ApiServiceInterface;
import com.jayfrostjay.propelrrexam.model.ResponseObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {

    private ApiServiceInterface apiCallService;

    public ApiManager(){
        apiCallService = ApiCallService.apiCallServiceInstance();
    }

    public void executeApiCall(ApiCallback callback){
        Call<ResponseObject> call = apiCallService.mockyResponse();
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                Log.e("TESTING", String.valueOf(response.isSuccessful()));
                ResponseObject resource = response.body();
                callback.onSuccess(resource.getSamplestring().toString());
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Log.e("TESTING ERROR", String.valueOf(t.getCause()));
                callback.onFailure("Api Call Failed");
            }
        });
    }

}
