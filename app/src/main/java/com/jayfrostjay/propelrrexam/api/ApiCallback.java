package com.jayfrostjay.propelrrexam.api;

public interface ApiCallback {
    void onSuccess(String data);
    void onFailure(String data);
}
