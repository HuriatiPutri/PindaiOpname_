package com.example.ics.pindaiopname;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.pindaiopname.adapter.BarangAdapter;
import com.example.ics.pindaiopname.adapter.OpnameAdapter;
import com.example.ics.pindaiopname.api.ApiService;
import com.example.ics.pindaiopname.api.Client;
import com.example.ics.pindaiopname.model.BarangModel;
import com.example.ics.pindaiopname.model.OpnameModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvBarang;
    private ArrayList<OpnameModel> barangModel = new ArrayList<>();
    private BarangAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        adapter = new BarangAdapter(getApplicationContext(), barangModel);
        rvBarang = findViewById(R.id.rc_barang);
        rvBarang.setHasFixedSize(true);
        rvBarang.setLayoutManager(new LinearLayoutManager(this));
        rvBarang.setAdapter(adapter);

        listBarang();
    }

    public void listBarang() {
        ApiService service = Client.getClient().create(ApiService.class);
        Call<List<OpnameModel>> call = service.getListBarang();
        call.enqueue(new Callback<List<OpnameModel>>() {
            @Override
            public void onResponse(Call<List<OpnameModel>> call, Response<List<OpnameModel>> response) {
                if(response.code()==200){
                    int jumlah = response.body().size();
                    for(int i=0; i<jumlah;i++){
                       OpnameModel data = new OpnameModel(
                                response.body().get(i).getBrgName(),
                                response.body().get(i).getBrgID(),
                                response.body().get(i).getSatuan()
                        );
                        barangModel.add(data);
                    }
                    adapter = new BarangAdapter(BarangActivity.this, barangModel);
                    rvBarang.setAdapter(adapter);
               }else{
                    Toast.makeText(getApplicationContext(), "Something wrong"+response.code(), Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<List<OpnameModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi Internet "+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(BarangActivity.this, AddBarangActivity.class));
                break;
        }
    }
}
