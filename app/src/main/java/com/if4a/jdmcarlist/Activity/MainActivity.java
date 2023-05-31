package com.if4a.jdmcarlist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.if4a.jdmcarlist.API.APIRequestData;
import com.if4a.jdmcarlist.API.RetroServer;
import com.if4a.jdmcarlist.Adapter.AdapterJDMCar;
import com.if4a.jdmcarlist.Model.ModelJDMCar;
import com.if4a.jdmcarlist.Model.ModelResponse;
import com.if4a.jdmcarlist.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvJdm;
    private FloatingActionButton fabTambah;
    private ProgressBar pbJdm;
    private RecyclerView.Adapter adJdm;
    private RecyclerView.LayoutManager lmJdm;
    private List<ModelJDMCar> listJdm = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvJdm = findViewById(R.id.rv_jdm);
        fabTambah = findViewById(R.id.fab_tambah);
        pbJdm = findViewById(R.id.pb_jdm);

        lmJdm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvJdm.setLayoutManager(lmJdm);


        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveJdm();
    }

    public void retrieveJdm(){
        pbJdm.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listJdm = response.body().getData();

                adJdm = new AdapterJDMCar(MainActivity.this, listJdm);
                rvJdm.setAdapter(adJdm);
                adJdm.notifyDataSetChanged();

                pbJdm.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Gagal Menghubungi Server",Toast.LENGTH_SHORT).show();
                pbJdm.setVisibility(View.GONE);
            }
        });
    }
}