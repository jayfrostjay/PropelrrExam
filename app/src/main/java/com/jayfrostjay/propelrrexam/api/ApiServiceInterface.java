package com.jayfrostjay.propelrrexam.api;
import com.jayfrostjay.propelrrexam.model.ResponseObject;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServiceInterface {
    @GET(".")
    Call<ResponseObject> mockyResponse();
}
