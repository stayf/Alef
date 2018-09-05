package com.stayfprod.alef.data.remote;

import com.stayfprod.alef.data.entity.RemoteImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServer {

    @GET("list.php")
    Call<List<RemoteImage>> getImages();
}
