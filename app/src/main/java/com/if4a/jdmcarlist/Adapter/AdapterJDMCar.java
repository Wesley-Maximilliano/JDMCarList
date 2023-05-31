package com.if4a.jdmcarlist.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if4a.jdmcarlist.API.APIRequestData;
import com.if4a.jdmcarlist.API.RetroServer;
import com.if4a.jdmcarlist.Activity.MainActivity;
import com.if4a.jdmcarlist.Activity.UbahActivity;
import com.if4a.jdmcarlist.Model.ModelJDMCar;
import com.if4a.jdmcarlist.Model.ModelResponse;
import com.if4a.jdmcarlist.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterJDMCar extends RecyclerView.Adapter<AdapterJDMCar.VHJdm>{
    private Context ctx;
    private List<ModelJDMCar> listJDM;

    public AdapterJDMCar(Context ctx, List<ModelJDMCar> listJDM) {
        this.ctx = ctx;
        this.listJDM = listJDM;
    }

    @NonNull
    @Override
    public VHJdm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View VarView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_jdm, parent,false);
        return new VHJdm(VarView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHJdm holder, int position) {
        ModelJDMCar MK = listJDM.get(position);

        holder.tvId.setText(MK.getId());
        holder.tvNama.setText(MK.getNama_mobil());
        holder.tvProdusen.setText(MK.getProdusen());
        holder.tvMasaProduksi.setText(MK.getMasa_produksi());
        holder.tvSejarah.setText(MK.getSejarah());
        holder.tvGambar.setText(MK.getGambar());
    }

    @Override
    public int getItemCount() {
        return listJDM.size();
    }

    public class VHJdm extends RecyclerView.ViewHolder{
        TextView tvId,tvNama,tvProdusen,tvMasaProduksi,tvSejarah,tvGambar;

        public VHJdm(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvProdusen = itemView.findViewById(R.id.tv_produsen);
            tvMasaProduksi = itemView.findViewById(R.id.tv_masa_produksi);
            tvSejarah = itemView.findViewById(R.id.tv_sejarah);
            tvGambar = itemView.findViewById(R.id.tv_gambar);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Operasi apa yang akan dilakukan?");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hapusJdm(tvId.getText().toString());
                            dialog.dismiss();
                        }
                    });

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent pindah = new Intent(ctx, UbahActivity.class);
                            pindah.putExtra("xId", tvId.getText().toString());
                            pindah.putExtra("xNama", tvNama.getText().toString());
                            pindah.putExtra("xProdusen", tvProdusen.getText().toString());
                            pindah.putExtra("xMasaProduksi", tvMasaProduksi.getText().toString());
                            pindah.putExtra("xSejarah", tvSejarah.getText().toString());
                            pindah.putExtra("xGambar", tvGambar.getText().toString());
                            ctx.startActivity(pindah);
                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }

        private void hapusJdm(String idJdm){
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = ARD.ardDelete(idJdm);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : " + kode +", Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity)ctx).retrieveJdm();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
