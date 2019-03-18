package com.example.ics.pindaiopname;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ics.pindaiopname.api.ApiService;
import com.example.ics.pindaiopname.api.Client;
import com.example.ics.pindaiopname.model.LokasiModel;
import com.example.ics.pindaiopname.model.OpnameModel;
import com.example.ics.pindaiopname.model.UnitModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultLokasiActivity extends AppCompatActivity implements View.OnClickListener {

    EditText spUnit,spLokasi;
    Button btnSimpan;
    View drop, dropLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_lokasi);

        spLokasi = findViewById(R.id.spLokasi);
        spUnit = findViewById(R.id.spUnit);
        btnSimpan = findViewById(R.id.btnSimpan);
        drop = findViewById(R.id.dropUnit);
        dropLokasi = findViewById(R.id.dropLokasi);

        btnSimpan.setOnClickListener(this);
        drop.setOnClickListener(this);
        dropLokasi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSimpan:
                saveStok();
                break;
            case R.id.dropUnit:
                unit();
                break;
            case R.id.dropLokasi:
                lokasi(MainActivity.idUnit);
                break;
        }
    }

    private void unit() {
        ApiService service = Client.getClient().create(ApiService.class);
        Call<List<UnitModel>> call = service.getUnit();
        call.enqueue(new Callback<List<UnitModel>>() {
            @Override
            public void onResponse(Call<List<UnitModel>> call, Response<List<UnitModel>> response) {
                if(response.isSuccessful()) {
                    int jumlah = response.body().size();

                    ArrayList<UnitModel> listData = new ArrayList<>();
                    for (int i = 0; i < jumlah; i++) {
                        UnitModel unitModel = new UnitModel(
                                response.body().get(i).getUnitID(),
                                response.body().get(i).getUnitName()
                        );
                        listData.add(unitModel);
                    }
                    final String[] valueUnit = new String[listData.size()];
                    final String[] valueId = new String[listData.size()];
                    for (int i = 0; i < listData.size(); i++) {
                        valueId[i] = listData.get(i).getUnitID();
                        valueUnit[i] = listData.get(i).getUnitName();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(DefaultLokasiActivity.this);
                    builder.setTitle("Pilih Unit");
                    builder.setIcon(R.drawable.mr_dialog_material_background_dark);
                    builder.setSingleChoiceItems(valueUnit, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            spLokasi.setText("");
                            MainActivity.idUnit = valueId[position];
                            MainActivity.unitName = valueUnit[position];
                            spUnit.setText(valueUnit[position]);
                            dialog.dismiss();
                        }
                    });
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog mbuilder = builder.create();
                    mbuilder.show();

                }
            }
            @Override
            public void onFailure(Call<List<UnitModel>> call, Throwable t) {

            }
        });


    }

    private void lokasi(String idUnit) {
        ApiService service = Client.getClient().create(ApiService.class);
        Call<List<LokasiModel>> call = service.getLokasi(idUnit);
        call.enqueue(new Callback<List<LokasiModel>>() {
            @Override
            public void onResponse(Call<List<LokasiModel>> call, Response<List<LokasiModel>> response) {
                if(response.isSuccessful()) {
                    int jumlah = response.body().size();

                    ArrayList<LokasiModel> listData = new ArrayList<>();
                    for (int i = 0; i < jumlah; i++) {
                        LokasiModel lokasiModel = new LokasiModel(
                                response.body().get(i).getLokasiID(),
                                response.body().get(i).getLokasiName()
                        );
                        listData.add(lokasiModel);
                    }
                    final String[] valueLokasi = new String[listData.size()];
                    final String[] valueId = new String[listData.size()];
                    for (int i = 0; i < listData.size(); i++) {
                        valueId[i] = listData.get(i).getLokasiID();
                        valueLokasi[i] = listData.get(i).getLokasiName();
                    }
                    AlertDialog.Builder builderLokasi = new AlertDialog.Builder(DefaultLokasiActivity.this);
                    builderLokasi.setTitle("Pilih Lokasi");
                    builderLokasi.setIcon(R.drawable.mr_dialog_material_background_dark);
                    builderLokasi.setSingleChoiceItems(valueLokasi, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            MainActivity.idLokasi = valueId[position];
                            MainActivity.lokasiName = valueLokasi[position];
                            spLokasi.setText(valueLokasi[position]);
                            dialog.dismiss();
                        }
                    });
                    builderLokasi.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog mbuilderLokasi = builderLokasi.create();
                    mbuilderLokasi.show();

                }
            }
            @Override
            public void onFailure(Call<List<LokasiModel>> call, Throwable t) {

            }
        });


    }

    private void saveStok() {

    }
}
