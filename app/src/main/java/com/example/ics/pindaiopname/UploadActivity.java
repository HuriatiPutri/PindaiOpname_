package com.example.ics.pindaiopname;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ics.pindaiopname.api.ApiService;
import com.example.ics.pindaiopname.api.Client;
import com.example.ics.pindaiopname.model.OpnameModel;
import com.example.ics.pindaiopname.model.uploadModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    ImageButton btnUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        btnUpload = findViewById(R.id.btnUpload);

        btnUpload.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UploadActivity.this);
                alert.setMessage("Proses Upload Data?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                upload();
                            }
                        }).setNegativeButton("No", null);
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
    }

    private void upload() {

        ApiService service = Client.getClient().create(ApiService.class);
        Call<uploadModel> call = service.uploadOpname(MainActivity.userID);
        call.enqueue(new Callback<uploadModel>() {
            @Override
            public void onResponse(Call<uploadModel> call, Response<uploadModel> response) {
                if(response.code()==200){
                    Toast.makeText(getApplicationContext(), "Upload Berhasil", Toast.LENGTH_SHORT).show();
                }else if(response.code()==400){
                    Toast.makeText(getApplicationContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<uploadModel> call, Throwable t) {
               // Toast.makeText(getApplicationContext(),"Failed "  + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
