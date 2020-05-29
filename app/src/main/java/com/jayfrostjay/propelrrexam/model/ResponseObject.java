package com.jayfrostjay.propelrrexam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ResponseObject {

    @SerializedName("samplestring")
    @Expose
    private String samplestring = "";

    @SerializedName("list")
    @Expose
    private List<String> list = new ArrayList<>();

    public String getSamplestring(){
        return samplestring;
    }

    public void setSamplestring(String object) {
        this.samplestring = object;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NotNull
    @Override
    public String toString(){
        String returnString = "samplestring: " + samplestring;

        if( list.size() > 0 ){
            returnString += "\n" + "list: " + list.toString();
        }

        return returnString;
    }
}
