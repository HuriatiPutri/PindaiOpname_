package com.example.ics.pindaiopname.api;

import com.example.ics.pindaiopname.model.LokasiModel;
import com.example.ics.pindaiopname.model.OpnameModel;
import com.example.ics.pindaiopname.model.ResponseModel;
import com.example.ics.pindaiopname.model.UnitModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("History/ListData")
    Call<List<OpnameModel>> getList();

    @GET("Barang/getdata")
    Call<OpnameModel> getData(@Query("id") String id);

    @GET("unit/listdata")
    Call<List<UnitModel>> getUnit();

    @GET("lokasi/listdata")
    Call<List<LokasiModel>> getLokasi(@Query("unitID") String unitID);

    @FormUrlEncoded
    @POST("stok/save")
    Call<OpnameModel> addItem(@Field("BrgID") String BrgID, @Field("UnitID") String UnitID,
                                @Field("LokasiID") String LokasiID,
                                @Field("Qty") int QTY);



}
