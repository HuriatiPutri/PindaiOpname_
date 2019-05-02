package com.example.ics.pindaiopname.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ics.pindaiopname.R;
import com.example.ics.pindaiopname.model.OpnameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class OpnameAdapter extends RecyclerView.Adapter<OpnameAdapter.OpnameHolder> {

    private Context context;
    private List<OpnameModel> list;

    public OpnameAdapter(Context context, List<OpnameModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OpnameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opname, parent, false);
        return new OpnameHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull OpnameHolder holder, int position) {
        holder.txtTgl.setText("[" + list.get(position).getTgl() + "]");
        holder.txtJam.setText(list.get(position).getJam());
        holder.txtNama.setText(list.get(position).getBrgName());
        holder.txtSatuan.setText(list.get(position).getSatuan());
        holder.txtLokasi.setText(list.get(position).getLokasiName());
        holder.txtUnit.setText(list.get(position).getUnitName() + ",");
        holder.txtQty.setText(String.valueOf(list.get(position).getQty()));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class OpnameHolder extends RecyclerView.ViewHolder{

        TextView txtTgl,txtJam, txtNama, txtSatuan, txtLokasi, txtQty, txtUnit;
        CardView cardView;
        public OpnameHolder(View itemView) {
            super(itemView);
            txtTgl = itemView.findViewById(R.id.txtTgl);
            txtJam = itemView.findViewById(R.id.txtJam);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtSatuan = itemView.findViewById(R.id.txtSatuan);
            txtLokasi = itemView.findViewById(R.id.txtLokasi);
            txtUnit = itemView.findViewById(R.id.txtUnit);
            txtQty = itemView.findViewById(R.id.txtQty);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
