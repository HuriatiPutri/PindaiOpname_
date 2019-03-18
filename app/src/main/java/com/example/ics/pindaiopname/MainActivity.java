package com.example.ics.pindaiopname;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ics.pindaiopname.adapter.OpnameAdapter;
import com.example.ics.pindaiopname.api.ApiService;
import com.example.ics.pindaiopname.api.Client;
import com.example.ics.pindaiopname.model.OpnameModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvOpname;

    private ArrayList<OpnameModel> opnameModel = new ArrayList<>();
    private OpnameAdapter adapter;

    public static String idUnit, idLokasi, unitName, lokasiName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setSubtitle(R.string.textLine);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GetDataActivity.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new OpnameAdapter(getApplicationContext(), opnameModel);
        rvOpname = findViewById(R.id.recyclerView);
        rvOpname.setHasFixedSize(true);
        rvOpname.setLayoutManager(new LinearLayoutManager(this));
        rvOpname.setAdapter(adapter);

        listOpname();
    }

    public void listOpname() {
        ApiService service = Client.getClient().create(ApiService.class);
        Call<List<OpnameModel>> call = service.getList();
        call.enqueue(new Callback<List<OpnameModel>>() {
            @Override
            public void onResponse(Call<List<OpnameModel>> call, Response<List<OpnameModel>> response) {
                if(response.isSuccessful()){
                    int jumlah =  response.body().size();
                    for(int i=0; i<jumlah;i++){
                        OpnameModel data = new OpnameModel(
                             response.body().get(i).getTgl(),
                             response.body().get(i).getJam(),
                             response.body().get(i).getBrgName(),
                             response.body().get(i).getLokasiName(),
                             response.body().get(i).getUnitName(),
                                response.body().get(i).getSatuan(),
                                response.body().get(i).getQty()
                        );
                        opnameModel.add(data);
                    }
                    adapter = new OpnameAdapter(MainActivity.this, opnameModel);
                    rvOpname.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<OpnameModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setting) {
            startActivity(new Intent(this, DefaultLokasiActivity.class));
        } else if (id == R.id.nav_upload) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
