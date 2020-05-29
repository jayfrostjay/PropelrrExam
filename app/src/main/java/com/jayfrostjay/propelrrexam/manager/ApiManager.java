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
        ApiCallService service = new ApiCallService();
        apiCallService = service.apiCallServiceInstance();
    }

    public void executeApiCall(ApiCallback callback){
        apiCallService.mockyResponse().enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                ResponseObject resource = response.body();
                if( resource != null ){
                    callback.onSuccess(resource.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                callback.onFailure("Api Call Failed");
            }
        });
    }

}
