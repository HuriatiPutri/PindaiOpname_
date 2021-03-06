package com.example.ics.pindaiopname;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.pindaiopname.api.ApiService;
import com.example.ics.pindaiopname.api.Client;
import com.example.ics.pindaiopname.database.DatabaseContract;
import com.example.ics.pindaiopname.database.DatabaseHelper;
import com.example.ics.pindaiopname.model.LokasiModel;
import com.example.ics.pindaiopname.model.OpnameModel;
import com.example.ics.pindaiopname.model.UnitModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ics.pindaiopname.database.DatabaseContract.LokasiColumns.CONTENT_URI;

public class DefaultLokasiActivity extends AppCompatActivity implements View.OnClickListener {

   private String idUnit, namaUnit, idLokasi, namaLokasi;
   private EditText spUnit,spLokasi;
   private Button btnSimpan;
   private View drop, dropLokasi;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_lokasi);

        spLokasi = findViewById(R.id.spLokasi);
        spUnit = findViewById(R.id.spUnit);
        drop = findViewById(R.id.dropUnit);
        dropLokasi = findViewById(R.id.dropLokasi);

        drop.setOnClickListener(this);
        dropLokasi.setOnClickListener(this);
        data();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dropUnit:
                unit();
                break;
            case R.id.dropLokasi:
                lokasi(idUnit);
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
                    builder.setTitle(R.string.pilihunit);
                    builder.setSingleChoiceItems(valueUnit, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            spLokasi.setText("");
                            idUnit = valueId[position];
                            namaUnit = valueUnit[position];
                            spUnit.setText(valueUnit[position]);

                            saveUnit();
                            dialog.dismiss();
                        }
                    });
                    builder.setNeutralButton(R.string.batal, new DialogInterface.OnClickListener() {
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
                    builderLokasi.setTitle(R.string.pilihlokasi);
                    builderLokasi.setSingleChoiceItems(valueLokasi, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            idLokasi = valueId[position];
                            namaLokasi = valueLokasi[position];
                            spLokasi.setText(valueLokasi[position]);

                            saveLokasi();
                            dialog.dismiss();
                        }
                    });
                    builderLokasi.setNeutralButton(R.string.batal, new DialogInterface.OnClickListener() {
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

    private void saveUnit() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from lokasi", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            SQLiteDatabase db_ = dbHelper.getWritableDatabase();
            db_.execSQL("update lokasi set IdUnit='"+idUnit+"'," +
                    "namaUnit='"+namaUnit+"'");
            Toast.makeText(getApplicationContext(), R.string.unit_diperbarui, Toast.LENGTH_LONG).show();
        }else{
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.LokasiColumns.ID_UNIT, idUnit);
            values.put(DatabaseContract.LokasiColumns.NAMA_UNIT, namaUnit);
            getContentResolver().insert(CONTENT_URI, values);
            setResult(101);
            Toast.makeText(this, R.string.unit_disimpan, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLokasi() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from lokasi", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            SQLiteDatabase db_ = dbHelper.getWritableDatabase();
            db_.execSQL("update lokasi set idLokasi='"+idLokasi+"'," +
                    "namaLokasi='"+namaLokasi+"'");
            Toast.makeText(getApplicationContext(), R.string.lokasi_diperbarui, Toast.LENGTH_LONG).show();
        }else{
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.LokasiColumns.ID_LOKASI, idLokasi);
            values.put(DatabaseContract.LokasiColumns.NAMA_LOKASI, namaLokasi);
            getContentResolver().insert(CONTENT_URI, values);
            setResult(101);
            Toast.makeText(this, R.string.lokasi_disimpan, Toast.LENGTH_SHORT).show();
        }
    }

    public void data(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from lokasi", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            idUnit = cursor.getString(1);
            idLokasi = cursor.getString(3);
            spUnit.setText(cursor.getString(2).toString());
            spLokasi.setText(cursor.getString(4).toString());
        }
    }


}
