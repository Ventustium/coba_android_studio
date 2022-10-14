package com.ventustium.coba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.Data);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://coba.petra.ac.id").
                addConverterFactory(GsonConverterFactory.create()).build();
        GetService api = retrofit.create(GetService.class);
        System.out.println("1");

        api.getData().enqueue(new Callback<List<Long>>() { //Saya tidak tau ini betul atau tidak.
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                //kalau berhasil dipanggil, memberikan respon 2
                System.out.println("2");
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                //jika ada error, akan print Error dan error nya.
                System.out.println("Error");
                System.out.println(t);
            }
        });
    }
}