package com.example.baru;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataDosenService {
    @GET("api/progmob/dosen/{nim_progmob}")
    Call<List<DataDosen>> getDosenAll(@Path("nim_progmob") String nimProgmob);
    @FormUrlEncoded
    @POST("api/progmob/dosen/create")
    Call<Dosen> postDosen(
            @Field("nim_progmob") String nimProgmob,
            @Field("nama") String nama,
            @Field("nidn") String nidn,
            @Field("alamat") String alamat,
            @Field("email") String email,
            @Field("gelar") String gelar,
            @Field("foto") String foto);
    @FormUrlEncoded
    @POST("api/progmob/dosen/update")
    Call<Dosen> updateDosen(
            @Field("nim_progmob") String nimProgmob,
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("nidn") String nidn,
            @Field("alamat") String alamat,
            @Field("email") String email,
            @Field("gelar") String gelar,
            @Field("foto") String foto);
    @FormUrlEncoded
    @POST("api/progmob/dosen/delete")
    Call<Dosen> delDosen(@Field("nim_progmob") String nimProgmob,
                         @Field("id") String id);
}
}
