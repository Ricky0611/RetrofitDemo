package com.example.rikki.retrofitdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyRequest {

    @GET("change/")
    Call<List<Change>> loadChanges(@Query("q") String status);
}
