package com.if4a.jdmcarlist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.if4a.jdmcarlist.API.APIRequestData;
import com.if4a.jdmcarlist.API.RetroServer;
import com.if4a.jdmcarlist.Model.ModelResponse;
import com.if4a.jdmcarlist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama,etProdusen, etMasaProduksi, etSejarah, etGambar;
    private Button btnSimpan;
    private String nama, produsen, masaProduksi, sejarah, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etProdusen = findViewById(R.id.et_produsen);
        etMasaProduksi = findViewById(R.id.et_masa_produksi);
        etSejarah = findViewById(R.id.et_sejarah);
        etGambar = findViewById(R.id.et_gambar);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama = etNama.getText().toString();
                produsen = etProdusen.getText().toString();
                masaProduksi = etMasaProduksi.getText().toString();
                sejarah = etSejarah.getText().toString();
                gambar = etGambar.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Tidak Boleh Kosong !!!");
                }
                else if (produsen.trim().isEmpty())
                {
                    etProdusen.setError("Produsem Tidak Boleh Kosong !!!");
                }
                else if (masaProduksi.trim().isEmpty())
                {
                    etMasaProduksi.setError("Masa Produksi tidak boleh Kosong !!!");
                }
                else if(sejarah.trim().isEmpty()){
                    etSejarah.setError("Sejarah Tidak Boleh Kosong");
                }
                else if(gambar.trim().isEmpty()){
                    etGambar.setError("Gambar Tidak Boleh Kosong");
                }
                else
                {
                    tambahKuliner();
                }
            }
        });
    }

    private void tambahKuliner(){

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardCreate(nama,produsen,masaProduksi,sejarah);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode +"Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}