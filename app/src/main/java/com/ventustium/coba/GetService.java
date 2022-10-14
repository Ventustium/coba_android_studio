package com.ventustium.coba;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetService {

    //masih belum paham menggunakan Retrofit dengan baik.
    //masih coba-coba. yang penting panggil API nya dulu bisa atau tidak.
    @GET("/laravel/public/api/v1/data/users")
    Call<List<Long>> getData();
}
