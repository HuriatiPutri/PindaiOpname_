package com.example.ics.pindaiopname.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ics.pindaiopname.R;
import com.example.ics.pindaiopname.model.BarangModel;
import com.example.ics.pindaiopname.model.OpnameModel;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangHolder> {

    private Context context;
    private List<OpnameModel> list;

    public BarangAdapter(Context context, List<OpnameModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BarangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new BarangHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangHolder holder, int position) {
        holder.txtKode.setText(list.get(position).getBrgID());
        holder.txtSatuan.setText(list.get(position).getSatuan());
        holder.txtNama.setText(list.get(position).getBrgName());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class BarangHolder extends RecyclerView.ViewHolder{

        TextView txtNama,txtKode, txtSatuan;
        public BarangHolder(View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtKode = itemView.findViewById(R.id.txtKode);
            txtSatuan = itemView.findViewById(R.id.txtSatuan);
        }
    }
}
