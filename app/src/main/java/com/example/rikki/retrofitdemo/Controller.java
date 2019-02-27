package com.example.rikki.retrofitdemo;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<List<Change>> {

    static final String BASE_URL = "https://git.eclipse.org/r/";
    private Context context;

    public Controller(Context context) {
        this.context = context;
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MyRequest request = retrofit.create(MyRequest.class);

        Call<List<Change>> call = request.loadChanges("status:open");
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Change>> call, Response<List<Change>> response) {
        if(response.isSuccessful()){
            List<Change> changesList = response.body();
            for(Change change : changesList){
                Toast.makeText(context, change.getTask(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            try {
                Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(context, "Error Response IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<List<Change>> call, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }
}
