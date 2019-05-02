package com.example.ics.pindaiopname.api;

import com.example.ics.pindaiopname.model.BarangModel;
import com.example.ics.pindaiopname.model.LokasiModel;
import com.example.ics.pindaiopname.model.OpnameModel;
import com.example.ics.pindaiopname.model.ResponseModel;
import com.example.ics.pindaiopname.model.UnitModel;
import com.example.ics.pindaiopname.model.uploadModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("History/ListData.php")
    Call<List<OpnameModel>> getList();

    @GET("barang/listBarang.php")
    Call<List<OpnameModel>> getListBarang();

    @GET("barang/getdata.php")
    Call<OpnameModel> getData(@Query("id") String id);

    @GET("unit/listdata.php")
    Call<List<UnitModel>> getUnit();

    @GET("lokasi/listdata.php")
    Call<List<LokasiModel>> getLokasi(@Query("unitid") String unitID);

    @FormUrlEncoded
    @POST("stok/save.php")
    Call<OpnameModel> addItem(@Field("BrgID") String BrgID, @Field("UnitID") String UnitID,
                                @Field("LokasiID") String LokasiID,
                                @Field("Qty") int QTY, @Field("userID") String UserID);
    @POST("stok/upload.php")
    Call<uploadModel> uploadOpname(@Query("userID") String userID);

    @FormUrlEncoded
    @POST("barang/save.php")
    Call<BarangModel> addBarang(@Field("BrgID") String BrgID, @Field("BrgName") String BrgName,
                              @Field("Satuan") String Satuan);

}
