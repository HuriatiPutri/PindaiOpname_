package com.example.ics.pindaiopname;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ics.pindaiopname.api.ApiService;
import com.example.ics.pindaiopname.api.Client;
import com.example.ics.pindaiopname.database.DatabaseHelper;
import com.example.ics.pindaiopname.model.LokasiModel;
import com.example.ics.pindaiopname.model.OpnameModel;
import com.example.ics.pindaiopname.model.ResponseModel;
import com.example.ics.pindaiopname.model.UnitModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDataActivity extends AppCompatActivity implements View.OnClickListener {

    SurfaceView cameraPreview;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    EditText edtKodeBarang, edtNamaBarang, edtSatuan, edtQty, spUnit,spLokasi;
    Button btnSimpan;
    View drop, dropLokasi;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                  /*  try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        cameraPreview = findViewById(R.id.cameraPreview);
        edtKodeBarang = findViewById(R.id.edtKode);
        edtNamaBarang = findViewById(R.id.edtNamaBrg);
        edtSatuan = findViewById(R.id.edtSatuan);
        spLokasi = findViewById(R.id.spLokasi);
        spUnit = findViewById(R.id.spUnit);
        edtQty = findViewById(R.id.edtQty);
        btnSimpan = findViewById(R.id.btnSimpan);
        drop = findViewById(R.id.dropUnit);
        dropLokasi = findViewById(R.id.dropLokasi);

        btnSimpan.setOnClickListener(this);
        drop.setOnClickListener(this);
        dropLokasi.setOnClickListener(this);

        setLokasiDefault();

        if(MainActivity.idUnit == null){
            spUnit.setText("Pilih Unit");
        }else{
            spUnit.setText(MainActivity.unitName);
        }

        if(MainActivity.idLokasi == null){
            spLokasi.setText("Pilih Lokasi");
        }else{
            spLokasi.setText(MainActivity.lokasiName);
        }

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        //Add event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(GetDataActivity.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if (qrcodes.size() != 0) {
                    String kode = qrcodes.valueAt(0).displayValue;
                    getDataBarang(kode);
                }
            }

            public void getDataBarang(String kode) {
                ApiService service = Client.getClient().create(ApiService.class);
                Call<OpnameModel> call = service.getData(kode);
                call.enqueue(new Callback<OpnameModel>() {
                    @Override
                    public void onResponse(Call<OpnameModel> call, Response<OpnameModel> response) {
                        if (response.code() == 200) {
                            MediaPlayer ok = MediaPlayer.create(GetDataActivity.this, R.raw.success);
                            ok.start();
                            edtKodeBarang.setText(response.body().getBrgID());
                            edtNamaBarang.setText(response.body().getBrgName());
                            edtSatuan.setText(response.body().getSatuan());
                        } else if (response.code() == 404) {
                            MediaPlayer fault = MediaPlayer.create(GetDataActivity.this, R.raw.system_fault);
                            fault.start();
                            edtKodeBarang.setText("");
                            edtNamaBarang.setText("");
                            edtSatuan.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<OpnameModel> call, Throwable t) {

                    }
                });
            }

        });
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(GetDataActivity.this);
                    builder.setTitle(R.string.pilihunit);
                    builder.setIcon(R.drawable.mr_dialog_material_background_dark);
                    builder.setSingleChoiceItems(valueUnit, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            spLokasi.setText("");
                          //  MainActivity.idUnit = valueId[position];
                          //  MainActivity.unitName = valueUnit[position];
                            spUnit.setText(valueUnit[position]);
                            dialog.dismiss();
                        }
                    });
                    builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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
                    AlertDialog.Builder builderLokasi = new AlertDialog.Builder(GetDataActivity.this);
                    builderLokasi.setTitle(R.string.pilihlokasi);
                    builderLokasi.setIcon(R.drawable.mr_dialog_material_background_dark);
                    builderLokasi.setSingleChoiceItems(valueLokasi, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                           // MainActivity.idLokasi = valueId[position];
                           // MainActivity.lokasiName = valueLokasi[position];
                            spLokasi.setText(valueLokasi[position]);
                            dialog.dismiss();
                        }
                    });
                    builderLokasi.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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
        String brgID = edtKodeBarang.getText().toString();
        String brgNama = edtNamaBarang.getText().toString();
        String brgSatuan = edtSatuan.getText().toString();
        String brgUnit = MainActivity.idUnit;
        String brgLokasi = MainActivity.idLokasi;
        int brgQty = Integer.parseInt(edtQty.getText().toString());
        if (brgID.equals("") && brgUnit.equals("") && brgLokasi.equals("") && brgNama.equals("") && brgSatuan.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.cannotNUll, Toast.LENGTH_SHORT).show();
        } else {
            ApiService service = Client.getClient().create(ApiService.class);
            Call<OpnameModel> call = service.addItem(brgID, brgUnit, brgLokasi, brgQty);
            call.enqueue(new Callback<OpnameModel>() {
                @Override
                public void onResponse(Call<OpnameModel> call, Response<OpnameModel> response) {
                    if (response.code() == 200) {
                        Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_LONG).show();
                        finish();
                    } else if (response.code() == 400) {
                        Toast.makeText(getApplicationContext(), R.string.failed, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<OpnameModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failed dong" + t, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void setLokasiDefault(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from lokasi", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            MainActivity.idUnit = cursor.getString(1).toString();
            MainActivity.unitName = cursor.getString(2).toString() + " [Default] ";
            MainActivity.idLokasi = cursor.getString(3).toString();
            MainActivity.lokasiName = cursor.getString(4).toString() + "[Default]";
        }
    }
}