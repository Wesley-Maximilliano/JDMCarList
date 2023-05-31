package com.if4a.jdmcarlist.API;

import com.if4a.jdmcarlist.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelResponse> ardRetrieve();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("nama_mobil") String nama,
            @Field("produsen") String produsen,
            @Field("masa_produksi") String masa_produksi,
            @Field("sejarah") String sejarah
            //@Field("gambar") String gambar
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ModelResponse> ardUpdate(
            @Field("nama_mobil") String nama,
            @Field("produsen") String produsen,
            @Field("masa_produksi") String masa_produksi,
            @Field("sejarah") String sejarah
            //@Field("gambar_mobil") String gambar
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelResponse> ardDelete(
            @Field("id") String id
    );
}
