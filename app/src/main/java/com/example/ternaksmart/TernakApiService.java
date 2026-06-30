package com.example.ternaksmart;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TernakApiService {
    @GET("ternak")
    Call<List<TernakData>> getAllTernak();

    @POST("ternak")
    Call<TernakData> createTernak(@Body TernakData ternakData);

    @PUT("ternak/{id}")
    Call<TernakData> updateTernak(@Path("id") int id, @Body TernakData ternakData);

    @DELETE("ternak/{id}")
    Call<Void> deleteTernak(@Path("id") int id);
}
