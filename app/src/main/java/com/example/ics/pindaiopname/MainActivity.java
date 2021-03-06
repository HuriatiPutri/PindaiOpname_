package com.example.ics.pindaiopname;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ics.pindaiopname.adapter.OpnameAdapter;
import com.example.ics.pindaiopname.api.ApiService;
import com.example.ics.pindaiopname.api.Client;
import com.example.ics.pindaiopname.model.OpnameModel;
import com.example.ics.pindaiopname.model.uploadModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GoogleSignInClient googleSignInClient;
    public static String userID;
    RecyclerView rvOpname;

    TextView username, email;
    ImageView photo;

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

        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.namauser);
        email = header.findViewById(R.id.emailuser);
        photo = header.findViewById(R.id.imageView);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if(acct != null){
            userID = acct.getEmail();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            username.setText(personName);
            email.setText(personEmail);
            Glide.with(this).load(personPhoto).into(photo);
        }

        adapter = new OpnameAdapter(getApplicationContext(), opnameModel);
        rvOpname = findViewById(R.id.recyclerView);
        rvOpname.setHasFixedSize(true);
        rvOpname.setLayoutManager(new LinearLayoutManager(this));
        rvOpname.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        rvOpname.setLayoutManager(mLayoutManager);

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
                                response.body().get(i).getQty(),
                                response.body().get(i).getUserID()
                        );
                        opnameModel.add(data);
                    }
                    adapter = new OpnameAdapter(MainActivity.this, opnameModel);
                    rvOpname.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<OpnameModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi Internet ", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(MainActivity.this, DefaultLokasiActivity.class));
        } else if (id == R.id.nav_upload) {
            startActivity(new Intent(MainActivity.this, UploadActivity.class));
        } else if (id == R.id.nav_logout) {
            signOut();
        }else if (id == R.id.nav_barang) {
            startActivity(new Intent(MainActivity.this, BarangActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Successfully Sign Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SignInActivity.class));
                        finish();
                    }
                });
    }
}
