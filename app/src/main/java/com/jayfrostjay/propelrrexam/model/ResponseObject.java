package com.jayfrostjay.propelrrexam.model;

import com.google.gson.annotations.SerializedName;

public class ResponseObject {

    @SerializedName("samplestring")
    private String samplestring;

    public String getSamplestring(){
        return samplestring;
    }

    public void setSamplestring(String object) {
        this.samplestring = object;
    }
}
